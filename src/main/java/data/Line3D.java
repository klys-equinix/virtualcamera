package data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.vecmath.Point3d;

@AllArgsConstructor
@Getter
@Setter
public class Line3D {
    private Point3d start;
    private Point3d end;

    public static Line3D fromCoords(double xS, double yS, double zS, double xE, double yE, double zE) {
        return new Line3D(new Point3d(xS, yS, zS), new Point3d(xE, yE, zE));
    }
}
