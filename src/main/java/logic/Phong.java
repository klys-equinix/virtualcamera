package logic;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import static logic.RectangleScene.ZERO_POINT;

public class Phong {

    static double ip = 1.0;
    static double ia = 0.1;
    static double ka = 0.45;
    static double kd = 0.6;
    static double ks = 0.4;
    static double m = 10;

    public static double phong(Vector3d v, Vector3d l, Vector3d n) {
        v.normalize();
        l.normalize();
        n.normalize();

        Vector3d h = getH(new Point3d(v), new Point3d(l));
        double ndl = crop(n.dot(l));
        double ndh = crop(n.dot(h));

        return crop(kd * ndl * ip + ks * Math.pow(ndh, m) * ip) * ip;
    }

    static Vector3d getH(Point3d p, Point3d q) {
        Vector3d r = mul(q, p.distance(new Point3d(ZERO_POINT)) / q.distance(new Point3d(ZERO_POINT)));
        q.sub(r);
        r.add(
                mul(q, 0.5)
        );
        return r;
    }

    static Vector3d mul(Point3d p, double multiplier) {
        return mul(new Vector3d(p), multiplier);
    }

    static Vector3d mul(Vector3d v, double multiplier) {
        v.x = v.x * multiplier;
        v.y = v.y * multiplier;
        v.z = v.z * multiplier;
        return v;
    }

    static double crop(double n) {
        return n < 0.0 ? 0.0 : n > 1.0 ? 1.0 : n;
    }
}
