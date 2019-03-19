package logic;

import data.Line2D;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import static data.Data.X_DIMENSION;
import static data.Data.Y_DIMENSION;

@Setter
public class Display extends JPanel {
    private List<Line2D> lines;

    public Display() {
        setPreferredSize(new Dimension(X_DIMENSION, Y_DIMENSION));
    }


    private Function<Graphics2D, Consumer<Line2D>> drawLine() {
        return g -> line
                -> g.drawLine((int) line.getStart().getX(), (int) line.getStart().getY(), (int) line.getEnd().getX(), (int) line.getEnd().getY());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        var g2 = (Graphics2D) g;
        if(lines != null)
            lines.forEach(drawLine().apply(g2));
    }
}
