package data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.vecmath.Point2d;
import java.awt.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Rectangle2D {
    private Point2d a;
    private Point2d b;
    private Point2d c;
    private Point2d d;
    private Color color;

    public int[] getXPoints() {
        return new int[]{(int)a.getX(), (int)b.getX(), (int)c.getX(), (int)d.getX()};
    }

    public int[] getYPoints() {
        return new int[]{(int)a.getY(), (int)b.getY(), (int)c.getY(), (int)d.getY()};
    }

    public Boolean isNotNull() {
        return a != null && b != null && c != null && d != null;
    }
}
