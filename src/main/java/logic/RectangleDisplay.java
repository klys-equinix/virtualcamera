package logic;

import data.Line2D;
import data.Rectangle2D;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;
import java.util.function.Function;

import static data.Data.X_DIMENSION;
import static data.Data.Y_DIMENSION;

@Setter
public class RectangleDisplay extends JPanel {
    private List<Rectangle2D> rectangles;

    public RectangleDisplay() {
        setPreferredSize(new Dimension(X_DIMENSION, Y_DIMENSION));
    }


    private Function<Graphics2D, Consumer<Rectangle2D>> drawPolygon() {
        return g -> rect
                -> {
            g.setColor(rect.getColor());
            g.fillPolygon(rect.getXPoints(), rect.getYPoints(), 4);
        };
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        var g2 = (Graphics2D) g;
        if (rectangles != null)
            rectangles.forEach(drawPolygon().apply(g2));
    }
}
