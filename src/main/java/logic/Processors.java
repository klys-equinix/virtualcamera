package logic;

import data.Rectangle3D;

import javax.vecmath.Point3d;
import java.util.function.Function;

public class Processors {
    public static double TRANSLATION_STEP = 50.0;
    public static double FOCAL_STEP = 10.0;
    public static double ROTATION_STEP = 0.1;

    public static Function<Rectangle3D, Rectangle3D> translateLeft() {
        return rect -> {
            rect.getA().setX(rect.getA().getX() + TRANSLATION_STEP);
            rect.getB().setX(rect.getB().getX() + TRANSLATION_STEP);
            rect.getC().setX(rect.getC().getX() + TRANSLATION_STEP);
            rect.getD().setX(rect.getD().getX() + TRANSLATION_STEP);
            return rect;
        };
    }

    public static Function<Rectangle3D, Rectangle3D> translateRight() {
        return rect -> {
            rect.getA().setX(rect.getA().getX() - TRANSLATION_STEP);
            rect.getB().setX(rect.getB().getX() - TRANSLATION_STEP);
            rect.getC().setX(rect.getC().getX() - TRANSLATION_STEP);
            rect.getD().setX(rect.getD().getX() - TRANSLATION_STEP);
            return rect;
        };
    }

    public static Function<Rectangle3D, Rectangle3D> translateBack() {
        return rect -> {
            rect.getA().setY(rect.getA().getY() + TRANSLATION_STEP);
            rect.getB().setY(rect.getB().getY() + TRANSLATION_STEP);
            rect.getC().setY(rect.getC().getY() + TRANSLATION_STEP);
            rect.getD().setY(rect.getD().getY() + TRANSLATION_STEP);
            return rect;
        };
    }

    public static Function<Rectangle3D, Rectangle3D> translateForward() {
        return rect -> {
            rect.getA().setY(rect.getA().getY() - TRANSLATION_STEP);
            rect.getB().setY(rect.getB().getY() - TRANSLATION_STEP);
            rect.getC().setY(rect.getC().getY() - TRANSLATION_STEP);
            rect.getD().setY(rect.getD().getY() - TRANSLATION_STEP);
            return rect;
        };
    }

    public static Function<Rectangle3D, Rectangle3D> translateBottom() {
        return rect -> {
            rect.getA().setZ(rect.getA().getZ() + TRANSLATION_STEP);
            rect.getB().setZ(rect.getB().getZ() + TRANSLATION_STEP);
            rect.getC().setZ(rect.getC().getZ() + TRANSLATION_STEP);
            rect.getD().setZ(rect.getD().getZ() + TRANSLATION_STEP);
            return rect;
        };
    }

    public static Function<Rectangle3D, Rectangle3D> translateUp() {
        return rect -> {
            rect.getA().setZ(rect.getA().getZ() - TRANSLATION_STEP);
            rect.getB().setZ(rect.getB().getZ() - TRANSLATION_STEP);
            rect.getC().setZ(rect.getC().getZ() - TRANSLATION_STEP);
            rect.getD().setZ(rect.getD().getZ() - TRANSLATION_STEP);
            return rect;
        };
    }

    public static Function<Rectangle3D, Rectangle3D> turnUp() {
        return rectangle3D -> {
            rectangle3D.setA(rotatePointUp(rectangle3D.getA()));
            rectangle3D.setB(rotatePointUp(rectangle3D.getB()));
            rectangle3D.setC(rotatePointUp(rectangle3D.getC()));
            rectangle3D.setD(rotatePointUp(rectangle3D.getD()));
            return rectangle3D;
        };
    }

    private static Point3d rotatePointUp(Point3d point3d) {
        var ay = point3d.getY();
        var az = point3d.getZ();
        point3d.setY((ay * Math.cos((-1) * ROTATION_STEP))
                - (az * Math.sin((-1) * ROTATION_STEP)));
        point3d.setZ((ay * Math.sin((-1) * ROTATION_STEP))
                + (az * Math.cos((-1) * ROTATION_STEP)));
        return point3d;
    }

    public static Function<Rectangle3D, Rectangle3D> turnDown() {
        return rectangle3D -> {
            rectangle3D.setA(rotatePointDown(rectangle3D.getA()));
            rectangle3D.setB(rotatePointDown(rectangle3D.getB()));
            rectangle3D.setC(rotatePointDown(rectangle3D.getC()));
            rectangle3D.setD(rotatePointDown(rectangle3D.getD()));
            return rectangle3D;
        };
    }

    private static Point3d rotatePointDown(Point3d point3d) {
        var ay = point3d.getY();
        var az = point3d.getZ();
        point3d.setY((ay * Math.cos(ROTATION_STEP))
                - (az * Math.sin(ROTATION_STEP)));
        point3d.setZ((ay * Math.sin(ROTATION_STEP))
                + (az * Math.cos(ROTATION_STEP)));
        return point3d;
    }


    public static Function<Rectangle3D, Rectangle3D> turnLeft() {
        return rectangle3D -> {
            rectangle3D.setA(rotatePointLeft(rectangle3D.getA()));
            rectangle3D.setB(rotatePointLeft(rectangle3D.getB()));
            rectangle3D.setC(rotatePointLeft(rectangle3D.getC()));
            rectangle3D.setD(rotatePointLeft(rectangle3D.getD()));
            return rectangle3D;
        };
    }

    private static Point3d rotatePointLeft(Point3d point3d) {
        var ax = point3d.getX();
        var ay = point3d.getY();
        point3d.setX((ax * Math.cos((-1) * ROTATION_STEP))
                - (ay * Math.sin((-1) * ROTATION_STEP)));
        point3d.setY((ax * Math.sin((-1) * ROTATION_STEP))
                + (ay * Math.cos((-1) * ROTATION_STEP)));
        return point3d;
    }

    public static Function<Rectangle3D, Rectangle3D> turnRight() {
        return rectangle3D -> {
            rectangle3D.setA(rotatePointRight(rectangle3D.getA()));
            rectangle3D.setB(rotatePointRight(rectangle3D.getB()));
            rectangle3D.setC(rotatePointRight(rectangle3D.getC()));
            rectangle3D.setD(rotatePointRight(rectangle3D.getD()));
            return rectangle3D;
        };
    }

    private static Point3d rotatePointRight(Point3d point3d) {
        var ax = point3d.getX();
        var ay = point3d.getY();
        point3d.setX((ax * Math.cos(ROTATION_STEP))
                - (ay * Math.sin(ROTATION_STEP)));
        point3d.setY((ax * Math.sin(ROTATION_STEP))
                + (ay * Math.cos(ROTATION_STEP)));
        return point3d;
    }
}
