package data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import javax.vecmath.Point2d;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class Rectangle3D {
    private Point3d a;
    private Point3d b;
    private Point3d c;
    private Point3d d;
    private Color color;

    public double getMidDistance(Point3d startPoint) {
        return startPoint.distance(getMidPoint());
    }

    public Point3d getMidPoint() {
        var mid = new Point3d();
        mid.add(a);
        mid.add(b);
        mid.add(c);
        mid.add(d);
        mid.scale(0.25);
        return mid;
    }
}
