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

@Getter
@Setter
public class RectangleScene {
    public final static double Xc = 0;
    public final static double Yc = 0;
    private double Zc = 300;

    public static double TRANSLATION_STEP = 50.0;
    public static double FOCAL_STEP = 10.0;
    public static double ROTATION_STEP = 0.1;

    public final static Vector3d ZERO_POINT = new Vector3d(X_DIMENSION / 2, Y_DIMENSION / 2, 0);

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
        paint();
    }

    public void zoomOUT() {
        this.setZc(this.getZc() - FOCAL_STEP);
        paint();
    }

    public void translateLeft() {
        rectangles.parallelStream().forEach(rect -> {
            rect.getA().setX(rect.getA().getX() + TRANSLATION_STEP);
            rect.getB().setX(rect.getB().getX() + TRANSLATION_STEP);
            rect.getC().setX(rect.getC().getX() + TRANSLATION_STEP);
            rect.getD().setX(rect.getD().getX() + TRANSLATION_STEP);
        });
        paint();
    }

    public void translateRight() {
        rectangles.parallelStream().forEach(rect -> {
            rect.getA().setX(rect.getA().getX() - TRANSLATION_STEP);
            rect.getB().setX(rect.getB().getX() - TRANSLATION_STEP);
            rect.getC().setX(rect.getC().getX() - TRANSLATION_STEP);
            rect.getD().setX(rect.getD().getX() - TRANSLATION_STEP);
        });
        paint();
    }

    public void translateBack() {
        rectangles.parallelStream().forEach(rect -> {
            rect.getA().setY(rect.getA().getY() + TRANSLATION_STEP);
            rect.getB().setY(rect.getB().getY() + TRANSLATION_STEP);
            rect.getC().setY(rect.getC().getY() + TRANSLATION_STEP);
            rect.getD().setY(rect.getD().getY() + TRANSLATION_STEP);
        });
        paint();
    }

    public void translateForward() {
        rectangles.parallelStream().forEach(rect -> {
            rect.getA().setY(rect.getA().getY() - TRANSLATION_STEP);
            rect.getB().setY(rect.getB().getY() - TRANSLATION_STEP);
            rect.getC().setY(rect.getC().getY() - TRANSLATION_STEP);
            rect.getD().setY(rect.getD().getY() - TRANSLATION_STEP);
        });
        paint();
    }

    public void translateBottom() {
        rectangles.parallelStream().forEach(rect -> {
            rect.getA().setZ(rect.getA().getZ() + TRANSLATION_STEP);
            rect.getB().setZ(rect.getB().getZ() + TRANSLATION_STEP);
            rect.getC().setZ(rect.getC().getZ() + TRANSLATION_STEP);
            rect.getD().setZ(rect.getD().getZ() + TRANSLATION_STEP);
        });
        paint();
    }

    public void translateUp() {
        rectangles.parallelStream().forEach(rect -> {
            rect.getA().setZ(rect.getA().getZ() - TRANSLATION_STEP);
            rect.getB().setZ(rect.getB().getZ() - TRANSLATION_STEP);
            rect.getC().setZ(rect.getC().getZ() - TRANSLATION_STEP);
            rect.getD().setZ(rect.getD().getZ() - TRANSLATION_STEP);
        });
        paint();
    }

    public void turnUp() {
        rectangles.parallelStream().forEach(rectangle3D -> {
            rectangle3D.setA(rotatePointUp(rectangle3D.getA()));
            rectangle3D.setB(rotatePointUp(rectangle3D.getB()));
            rectangle3D.setC(rotatePointUp(rectangle3D.getC()));
            rectangle3D.setD(rotatePointUp(rectangle3D.getD()));
        });
        paint();
    }

    private Point3d rotatePointUp(Point3d point3d) {
        var ay = point3d.getY();
        var az = point3d.getZ();
        point3d.setY((ay * Math.cos((-1) * ROTATION_STEP))
                - (az * Math.sin((-1) * ROTATION_STEP)));
        point3d.setZ((ay * Math.sin((-1) * ROTATION_STEP))
                + (az * Math.cos((-1) * ROTATION_STEP)));
        return point3d;
    }

    public void turnDown() {
        rectangles.parallelStream().forEach(rectangle3D -> {
            rectangle3D.setA(rotatePointDown(rectangle3D.getA()));
            rectangle3D.setB(rotatePointDown(rectangle3D.getB()));
            rectangle3D.setC(rotatePointDown(rectangle3D.getC()));
            rectangle3D.setD(rotatePointDown(rectangle3D.getD()));
        });
        paint();
    }

    private Point3d rotatePointDown(Point3d point3d) {
        var ay = point3d.getY();
        var az = point3d.getZ();
        point3d.setY((ay * Math.cos(ROTATION_STEP))
                - (az * Math.sin(ROTATION_STEP)));
        point3d.setZ((ay * Math.sin(ROTATION_STEP))
                + (az * Math.cos(ROTATION_STEP)));
        return point3d;
    }


    public void turnLeft() {
        rectangles.parallelStream().forEach(rectangle3D -> {
            rectangle3D.setA(rotatePointLeft(rectangle3D.getA()));
            rectangle3D.setB(rotatePointLeft(rectangle3D.getB()));
            rectangle3D.setC(rotatePointLeft(rectangle3D.getC()));
            rectangle3D.setD(rotatePointLeft(rectangle3D.getD()));
        });
        paint();
    }

    private Point3d rotatePointLeft(Point3d point3d) {
        var ax = point3d.getX();
        var ay = point3d.getY();
        point3d.setX((ax * Math.cos((-1) * ROTATION_STEP))
                - (ay * Math.sin((-1) * ROTATION_STEP)));
        point3d.setY((ax * Math.sin((-1) * ROTATION_STEP))
                + (ay * Math.cos((-1) * ROTATION_STEP)));
        return point3d;
    }

    public void turnRight() {
        rectangles.parallelStream().forEach(rectangle3D -> {
            rectangle3D.setA(rotatePointRight(rectangle3D.getA()));
            rectangle3D.setB(rotatePointRight(rectangle3D.getB()));
            rectangle3D.setC(rotatePointRight(rectangle3D.getC()));
            rectangle3D.setD(rotatePointRight(rectangle3D.getD()));
        });

        paint();
    }

    private Point3d rotatePointRight(Point3d point3d) {
        var ax = point3d.getX();
        var ay = point3d.getY();
        point3d.setX((ax * Math.cos(ROTATION_STEP))
                - (ay * Math.sin(ROTATION_STEP)));
        point3d.setY((ax * Math.sin(ROTATION_STEP))
                + (ay * Math.cos(ROTATION_STEP)));
        return point3d;
    }

    public void paint() {
        ArrayList<Rectangle3D> toMap = new ArrayList<>(rectangles);
        var startPoint = new Point3d(Xc, Zc, Yc);
        display.setRectangles(toMap.parallelStream()
                .sorted(Comparator.comparing(rect -> ((Rectangle3D)rect).getMidDistance(startPoint)).reversed())
                .map(project())
                .filter(Rectangle2D::isNotNull)
                .collect(Collectors.toList()));
        display.repaint();
    }

    public void loadData(double[][] data) {
        var lines = new ArrayList<Line3D>();
        for (double[] datum : data) {
            var newLine = Line3D.fromCoords(datum[0], datum[1], datum[2], datum[3], datum[4], datum[5]);
            lines.add(newLine);
            if (lines.size() == 4) {
                rectangles.add(Rectangle3D.builder()
                        .a(lines.get(0).getStart())
                        .b(lines.get(1).getStart())
                        .c(lines.get(2).getStart())
                        .d(lines.get(3).getStart())
                        .color(colors[ThreadLocalRandom.current().nextInt(0, colors.length)])
                        .build());
                lines.clear();
            }
        }
        rectangles = rectangles.stream().flatMap(splitRectangle().apply(64)).collect(Collectors.toList());
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
                rectangle2D.setA(projected.getStart());
                rectangle2D.setB(projected.getEnd());
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
            double Y = start.getY() - end.getY();
            double y = Zc - start.getY();
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
