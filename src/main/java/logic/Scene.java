package logic;

import data.Line2D;
import data.Line3D;
import lombok.Getter;
import lombok.Setter;

import javax.vecmath.Point2d;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import static data.Data.X_DIMENSION;
import static data.Data.Y_DIMENSION;

@Getter
@Setter
public class Scene {
    public final static double Xc = 0;
    public final static double Yc = 0;
    private double Zc = 300;

    public static double TRANSLATION_STEP = 50.0;
    public static double FOCAL_STEP = 10.0;
    public static double ROTATION_STEP = 0.1;

    public final static Vector3d ZERO_POINT = new Vector3d(X_DIMENSION / 2, Y_DIMENSION / 2, 0);

    private Display display;

    private static List<Line3D> lines = new ArrayList<>();

    public void zoomIN() {
        this.setZc(this.getZc() + FOCAL_STEP);
        paint();
    }

    public void zoomOUT() {
        this.setZc(this.getZc() - FOCAL_STEP);
        paint();
    }

    public void translateLeft() {
        lines.forEach(line -> {
            line.getStart().setX(line.getStart().getX() + TRANSLATION_STEP);
            line.getEnd().setX(line.getEnd().getX() + TRANSLATION_STEP);
        });
        paint();
    }

    public void translateRight() {
        lines.forEach(line -> {
            line.getStart().setX(line.getStart().getX() - TRANSLATION_STEP);
            line.getEnd().setX(line.getEnd().getX() - TRANSLATION_STEP);
        });
        paint();
    }

    public void translateBack() {
        lines.forEach(line -> {
            line.getStart().setY(line.getStart().getY() + TRANSLATION_STEP);
            line.getEnd().setY(line.getEnd().getY() + TRANSLATION_STEP);
        });
        paint();
    }

    public void translateForward() {
        lines.forEach(line -> {
            line.getStart().setY(line.getStart().getY() - TRANSLATION_STEP);
            line.getEnd().setY(line.getEnd().getY() - TRANSLATION_STEP);
        });
        paint();
    }

    public void translateBottom() {
        lines.forEach(line -> {
            line.getStart().setZ(line.getStart().getZ() + TRANSLATION_STEP);
            line.getEnd().setZ(line.getEnd().getZ() + TRANSLATION_STEP);
        });
        paint();
    }

    public void translateUp() {
        lines.forEach(line -> {
            line.getStart().setZ(line.getStart().getZ() - TRANSLATION_STEP);
            line.getEnd().setZ(line.getEnd().getZ() - TRANSLATION_STEP);
        });
        paint();
    }

    public void turnUp() {
        lines.forEach(line -> {
            var ay = line.getStart().getY();
            var az = line.getStart().getZ();
            var by = line.getEnd().getY();
            var bz = line.getEnd().getZ();

            line.getStart().setY((ay * java.lang.Math.cos((-1) * ROTATION_STEP))
                    - (az * java.lang.Math.sin((-1) * ROTATION_STEP)));
            line.getStart().setZ((ay * java.lang.Math.sin((-1) * ROTATION_STEP))
                    + (az * java.lang.Math.cos((-1) * ROTATION_STEP)));

            line.getEnd().setY((by * java.lang.Math.cos((-1) * ROTATION_STEP))
                    - (bz * java.lang.Math.sin((-1) * ROTATION_STEP)));
            line.getEnd().setZ((by * java.lang.Math.sin((-1) * ROTATION_STEP))
                    + (bz * java.lang.Math.cos((-1) * ROTATION_STEP)));
        });

        paint();
    }

    public void turnDown() {
        lines.forEach(line -> {
            var ay = line.getStart().getY();
            var az = line.getStart().getZ();
            var by = line.getEnd().getY();
            var bz = line.getEnd().getZ();

            line.getStart().setY((ay * java.lang.Math.cos(ROTATION_STEP))
                    - (az * java.lang.Math.sin(ROTATION_STEP)));
            line.getStart().setZ((ay * java.lang.Math.sin(ROTATION_STEP))
                    + (az * java.lang.Math.cos(ROTATION_STEP)));

            line.getEnd().setY((by * java.lang.Math.cos(ROTATION_STEP))
                    - (bz * java.lang.Math.sin(ROTATION_STEP)));
            line.getEnd().setZ((by * java.lang.Math.sin(ROTATION_STEP))
                    + (bz * java.lang.Math.cos(ROTATION_STEP)));
        });

        paint();
    }


    public void turnLeft() {
        lines.forEach(line -> {
            var ax = line.getStart().getX();
            var ay = line.getStart().getY();
            var bx = line.getEnd().getX();
            var by = line.getEnd().getY();

            line.getStart().setX((ax * java.lang.Math.cos((-1) * ROTATION_STEP))
                    - (ay * java.lang.Math.sin((-1) * ROTATION_STEP)));
            line.getStart().setY((ax * java.lang.Math.sin((-1) * ROTATION_STEP))
                    + (ay * java.lang.Math.cos((-1) * ROTATION_STEP)));

            line.getEnd().setX((bx * java.lang.Math.cos((-1) * ROTATION_STEP))
                    - (by * java.lang.Math.sin((-1) * ROTATION_STEP)));
            line.getEnd().setY((bx * java.lang.Math.sin((-1) * ROTATION_STEP))
                    + (by * java.lang.Math.cos((-1) * ROTATION_STEP)));
        });

        paint();
    }

    public void turnRight() {
        lines.forEach(line -> {
            var ax = line.getStart().getX();
            var ay = line.getStart().getY();
            var bx = line.getEnd().getX();
            var by = line.getEnd().getY();

            line.getStart().setX((ax * java.lang.Math.cos(ROTATION_STEP))
                    - (ay * java.lang.Math.sin(ROTATION_STEP)));
            line.getStart().setY((ax * java.lang.Math.sin(ROTATION_STEP))
                    + (ay * java.lang.Math.cos(ROTATION_STEP)));

            line.getEnd().setX((bx * java.lang.Math.cos(ROTATION_STEP))
                    - (by * java.lang.Math.sin(ROTATION_STEP)));
            line.getEnd().setY((bx * java.lang.Math.sin(ROTATION_STEP))
                    + (by * java.lang.Math.cos(ROTATION_STEP)));
        });

        paint();
    }

    public void leanLeft(){
        lines.forEach(line -> {
            var ax = line.getStart().getX();
            var az = line.getStart().getY();
            var bx = line.getEnd().getX();
            var bz = line.getEnd().getY();

            line.getStart().setX(( ax * java.lang.Math.cos((-1)*ROTATION_STEP) )
                    + ( az * java.lang.Math.sin((-1)*ROTATION_STEP) ));
            line.getStart().setY(( (-1) * ax * java.lang.Math.sin((-1)*ROTATION_STEP) )
                    + ( az * java.lang.Math.cos((-1)*ROTATION_STEP) ));

            line.getEnd().setX(( bx * java.lang.Math.cos((-1)*ROTATION_STEP) )
                    + ( bz * java.lang.Math.sin((-1)*ROTATION_STEP) ));
            line.getEnd().setY(( (-1) * bx * java.lang.Math.sin((-1)*ROTATION_STEP) )
                    + ( bz * java.lang.Math.cos((-1)*ROTATION_STEP) ));
        });

        paint();
    }
    public void leanRight(){
        lines.forEach(line -> {
            var ax = line.getStart().getX();
            var az = line.getStart().getY();
            var bx = line.getEnd().getX();
            var bz = line.getEnd().getY();

            line.getStart().setX(( ax * java.lang.Math.cos(ROTATION_STEP) )
                    + ( az * java.lang.Math.sin(ROTATION_STEP) ));
            line.getStart().setY(( (-1) * ax * java.lang.Math.sin(ROTATION_STEP) )
                    + ( az * java.lang.Math.cos(ROTATION_STEP) ));

            line.getEnd().setX(( bx * java.lang.Math.cos(ROTATION_STEP) )
                    + ( bz * java.lang.Math.sin(ROTATION_STEP) ));
            line.getEnd().setY(( (-1) * bx * java.lang.Math.sin(ROTATION_STEP) )
                    + ( bz * java.lang.Math.cos(ROTATION_STEP) ));
        });

        paint();
    }

    public void axisRotateLeft(){
        lines.forEach(line -> {
            var ax = line.getStart().getX();
            var az = line.getStart().getZ();
            var bx = line.getEnd().getX();
            var bz = line.getEnd().getZ();

            line.getStart().setX(( ax * java.lang.Math.cos((-1)*ROTATION_STEP) )
                    + ( az * java.lang.Math.sin((-1)*ROTATION_STEP) ));
            line.getStart().setZ(( (-1) * ax * java.lang.Math.sin((-1)*ROTATION_STEP) )
                    + ( az * java.lang.Math.cos((-1)*ROTATION_STEP) ));

            line.getEnd().setX(( bx * java.lang.Math.cos((-1)*ROTATION_STEP) )
                    + ( bz * java.lang.Math.sin((-1)*ROTATION_STEP) ));
            line.getEnd().setZ(( (-1) * bx * java.lang.Math.sin((-1)*ROTATION_STEP) )
                    + ( bz * java.lang.Math.cos((-1)*ROTATION_STEP) ));
        });

        paint();
    }
    public void axisRotateRight(){
        lines.forEach(line -> {
            var ax = line.getStart().getX();
            var az = line.getStart().getZ();
            var bx = line.getEnd().getX();
            var bz = line.getEnd().getZ();

            line.getStart().setX(( ax * java.lang.Math.cos(ROTATION_STEP) )
                    + ( az * java.lang.Math.sin(ROTATION_STEP) ));
            line.getStart().setZ(( (-1) * ax * java.lang.Math.sin(ROTATION_STEP) )
                    + ( az * java.lang.Math.cos(ROTATION_STEP) ));

            line.getEnd().setX(( bx * java.lang.Math.cos(ROTATION_STEP) )
                    + ( bz * java.lang.Math.sin(ROTATION_STEP) ));
            line.getEnd().setZ(( (-1) * bx * java.lang.Math.sin(ROTATION_STEP) )
                    + ( bz * java.lang.Math.cos(ROTATION_STEP) ));
        });

        paint();
    }

    public void paint() {
        ArrayList<Line3D> toMap = new ArrayList(lines);
        display.setLines(toMap.stream()
                .map(project())
                .filter(Objects::nonNull)
                .collect(Collectors.toList()));
        display.repaint();
    }

    public void loadData(double[][] data) {
        for (double[] datum : data) {
            Line3D newLine = Line3D.fromCoords(datum[0], datum[1], datum[2], datum[3], datum[4], datum[5]);
            lines.add(newLine);
        }
    }

    private Function<Line3D, Line2D> project() {
        return line3D -> {
            var start = line3D.getStart();
            var end = line3D.getEnd();
            Line2D projected = null;
            if(isPointInSight(start) && isPointInSight(end))
                projected = new Line2D(projectPoint(start), projectPoint(end));
            else if(isPointInSight(start))
                projected = projectPartly(start, end);
            else if(isPointInSight(end))
                projected = projectPartly(end, start);
            return projected;
        };
    }

    private boolean isPointInSight(Point3d point) {
        return point.getY() >= Zc;
    }

    private Line2D projectPartly(Point3d start, Point3d end) {
        if(start.getY() == Zc) {
            Point2d result1 = new Point2d(
                    start.getX(),
                    start.getZ()
            );
            Point2d result2 = new Point2d(result1.getX(), result1.getY());
            return new Line2D(result1, result2);
        } else {
            double Y = start.getY() - end.getY();
            double y = Zc - start.getY();
            double x, z, k;
            k = y / Y;
            x = start.getX() + ((end.getX() - start.getX()) * k);
            z = start.getZ() + ((end.getZ() - start.getZ()) * k);
            return new Line2D(projectPoint(start), new Point2d((int)(ZERO_POINT.getX() + x), (int)(ZERO_POINT.getY() - z)));
        }
    }

    private Point2d projectPoint(Point3d point3d) {

//        var xPrime = (Zc * ((point3d.getX()) / (point3d.getY() + Zc))) + ZERO_POINT.getX();
//        var yPrime = (Zc * ((point3d.getZ()) / (point3d.getY() + Zc))) + ZERO_POINT.getY();

        var focal = Zc / point3d.getY();
        var xPrime = focal * point3d.getX() + ZERO_POINT.getX();
        var yPrime = ZERO_POINT.getY() - focal * point3d.getZ();

        return new Point2d(xPrime, yPrime);
    }
}
