package data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.vecmath.Point2d;

@AllArgsConstructor
@Getter
@Setter
public class Line2D {
    private Point2d start;
    private Point2d end;

    public static Line2D fromCoords(double xS, double yS, double xE, double yE) {
        return new Line2D(new Point2d(xS, yS), new Point2d(xE, yE));
    }
}
