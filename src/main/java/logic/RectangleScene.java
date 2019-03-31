package logic;

import data.Line2D;
import data.Line3D;
import data.Rectangle2D;
import data.Rectangle3D;
import lombok.Getter;
import lombok.Setter;

import javax.vecmath.Point2d;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static data.Data.X_DIMENSION;
import static data.Data.Y_DIMENSION;
import static logic.Phong.crop;
import static logic.Phong.ia;
import static logic.Phong.ka;
import static logic.Phong.phong;

@Getter
@Setter
public class RectangleScene {
    public final static double Xc = 500;
    public final static double Yc = 500;
    private double Zc = 300;

    public static double TRANSLATION_STEP = 50.0;
    public static double FOCAL_STEP = 10.0;
    public static double ROTATION_STEP = 0.1;

    public final static Vector3d ZERO_POINT = new Vector3d(X_DIMENSION / 2, Y_DIMENSION / 2, 0);
    private final static Point3d light = new Point3d(-1, 1, -1);


    private static Color[] colors = {
            Color.CYAN,
            Color.GREEN,
            Color.MAGENTA,
            Color.ORANGE,
            Color.PINK,
            Color.BLUE,
            Color.BLACK,
            Color.ORANGE,
            Color.LIGHT_GRAY
    };

    private RectangleDisplay display;

    private static List<Rectangle3D> rectangles = new ArrayList<>();

    public void zoomIN() {
        this.setZc(this.getZc() + FOCAL_STEP);
        paint(Function.identity());
    }

    public void zoomOUT() {
        this.setZc(this.getZc() - FOCAL_STEP);
        paint(Function.identity());
    }

    public void paint(Function<Rectangle3D, Rectangle3D> process) {
        display.setRectangles(rectangles.parallelStream()
                .sorted(Comparator.comparing(rect -> ((Rectangle3D)rect).getMidDistance(new Point3d(Xc, Zc, Yc))).reversed())
                .map(process.andThen(shade()).andThen(project()))
                .filter(Rectangle2D::isNotNull)
                .collect(Collectors.toList()));
        display.repaint();
    }

    private Function<Rectangle3D, Rectangle2D> project() {
        return rectangle3D -> {
            var rectangle2D = new Rectangle2D();

            if (isPointInSight(rectangle3D.getA()) && isPointInSight(rectangle3D.getB())) {
                rectangle2D.setA(projectPoint(rectangle3D.getA()));
                rectangle2D.setB(projectPoint(rectangle3D.getB()));
            } else if (isPointInSight(rectangle3D.getA())) {
                var projected = projectPartly(rectangle3D.getA(), rectangle3D.getB());
                rectangle2D.setA(projected.getStart());
                rectangle2D.setB(projected.getEnd());
            } else if (isPointInSight(rectangle3D.getB())) {
                var projected = projectPartly(rectangle3D.getB(), rectangle3D.getA());
                rectangle2D.setA(projected.getEnd());
                rectangle2D.setB(projected.getStart());
            }

            if (isPointInSight(rectangle3D.getC())) {
                rectangle2D.setC(projectPoint(rectangle3D.getC()));
            } else if (isPointInSight(rectangle3D.getB())) {
                var projected = projectPartly(rectangle3D.getB(), rectangle3D.getC());
                rectangle2D.setB(projected.getStart());
                rectangle2D.setC(projected.getEnd());
            }

            if (isPointInSight(rectangle3D.getD())) {
                rectangle2D.setD(projectPoint(rectangle3D.getD()));
            } else if (isPointInSight(rectangle3D.getC())) {
                var projected = projectPartly(rectangle3D.getC(), rectangle3D.getD());
                rectangle2D.setC(projected.getStart());
                rectangle2D.setD(projected.getEnd());
            }

            rectangle2D.setColor(rectangle3D.getColor());
            return rectangle2D;
        };
    }

    private Function<Rectangle3D, Rectangle3D> shade() {
        return rectangle3D -> {
            var radius = (X_DIMENSION/2);
            var v = new Vector3d(Xc, Zc, Yc);
            v.sub(rectangle3D.getMidPoint());
            var l = new Vector3d(light.x*radius+radius, light.y*radius+radius, light.z*radius);
            l.sub(rectangle3D.getMidPoint());
            var n = new Point3d(rectangle3D.getMidPoint().x-(radius), rectangle3D.getMidPoint().z-(radius), rectangle3D.getMidPoint().y);

            var p = phong(v, l, new Vector3d(n));

            p += ia*ka;

            var c = (float) crop(p);
            rectangle3D.setColor(new Color(c, c, c));

            return rectangle3D;
        };
    }

    private boolean isPointInSight(Point3d point) {
        return point.getY() >= Zc;
    }

    private Line2D projectPartly(Point3d start, Point3d end) {
        if (start.getY() == Zc) {
            var result1 = new Point2d(
                    start.getX(),
                    start.getZ()
            );
            var result2 = new Point2d(result1.getX(), result1.getY());
            return new Line2D(result1, result2);
        } else {
            var Y = start.getY() - end.getY();
            var y = Zc - start.getY();
            double x, z, k;
            k = y / Y;
            x = start.getX() + ((end.getX() - start.getX()) * k);
            z = start.getZ() + ((end.getZ() - start.getZ()) * k);
            return new Line2D(projectPoint(start), new Point2d((int) (ZERO_POINT.getX() + x), (int) (ZERO_POINT.getY() - z)));
        }
    }

    private Point2d projectPoint(Point3d point3d) {
        var focal = Zc / point3d.getY();
        var xPrime = focal * point3d.getX() + ZERO_POINT.getX();
        var yPrime = ZERO_POINT.getY() - focal * point3d.getZ();
        return new Point2d(xPrime, yPrime);
    }

    public void loadData(double[][] data) {
        var lines = new ArrayList<Line3D>();
        var counter = 0;
        for (double[] datum : data) {
            var newLine = Line3D.fromCoords(datum[0], datum[1], datum[2], datum[3], datum[4], datum[5]);
            lines.add(newLine);
            if (lines.size() == 4) {
                rectangles.add(Rectangle3D.builder()
                        .a(lines.get(0).getStart())
                        .b(lines.get(1).getStart())
                        .c(lines.get(2).getStart())
                        .d(lines.get(3).getStart())
                        .color(colors[counter++%7])
                        .build());
                lines.clear();
            }
        }
        rectangles = rectangles.parallelStream().flatMap(splitRectangle().apply(64)).collect(Collectors.toList());
    }

    private Function<Integer, Function<Rectangle3D, Stream<Rectangle3D>>> splitRectangle() {
        return parts -> rectangle2D -> IntStream.range(0, parts).mapToObj(part -> {
            var partMultiplier = part / parts;
            var nextPartMultiplier = (part + 1)/ parts;
            return Rectangle3D.builder()
                    .a(
                            new Point3d(
                                    rectangle2D.getA().x + partMultiplier * ((rectangle2D.getB().x - rectangle2D.getA().x)),
                                    rectangle2D.getA().y + partMultiplier * ((rectangle2D.getB().y - rectangle2D.getA().y)),
                                    rectangle2D.getA().z + partMultiplier * ((rectangle2D.getB().z - rectangle2D.getA().z))
                            )
                    )
                    .b(
                            new Point3d(
                                    rectangle2D.getA().x + nextPartMultiplier * ((rectangle2D.getB().x - rectangle2D.getA().x)),
                                    rectangle2D.getA().y + nextPartMultiplier * ((rectangle2D.getB().y - rectangle2D.getA().y)),
                                    rectangle2D.getA().z + nextPartMultiplier * ((rectangle2D.getB().z - rectangle2D.getA().z))
                            )
                    )
                    .c(
                            new Point3d(
                                    rectangle2D.getD().x + nextPartMultiplier * ((rectangle2D.getC().x - rectangle2D.getD().x)),
                                    rectangle2D.getD().y + nextPartMultiplier * ((rectangle2D.getC().y - rectangle2D.getD().y)),
                                    rectangle2D.getD().z + nextPartMultiplier * ((rectangle2D.getC().z - rectangle2D.getD().z))
                            )
                    )
                    .d(
                            new Point3d(
                                    rectangle2D.getD().x + partMultiplier * ((rectangle2D.getC().x - rectangle2D.getD().x)),
                                    rectangle2D.getD().y + partMultiplier * ((rectangle2D.getC().y - rectangle2D.getD().y)),
                                    rectangle2D.getD().z + partMultiplier * ((rectangle2D.getC().z - rectangle2D.getD().z))
                            )
                    )
                    .color(rectangle2D.getColor())
                    .build();
        });
    }
}
