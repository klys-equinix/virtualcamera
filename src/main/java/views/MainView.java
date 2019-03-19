package views;

import data.Data;
import logic.RectangleDisplay;
import logic.RectangleScene;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.event.KeyListener;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import javax.swing.GroupLayout;
import javax.swing.JFrame;

public class MainView extends JFrame  implements KeyListener {
    private static final long serialVersionUID = -9152548955124095714L;

    private RectangleScene scene;

    public MainView() {
        setSize(new Dimension(1280, 720));
        setPreferredSize(new Dimension(1280, 720));
        setBackground(Color.LIGHT_GRAY);

        scene = new RectangleScene();

        scene.loadData(Data.data);

        var display = new RectangleDisplay();
        display.setBorder(new TitledBorder(null, "View", TitledBorder.LEADING,
                TitledBorder.TOP, null, null));
        display.setBackground(Color.WHITE);

        GroupLayout groupLayout = new GroupLayout(getContentPane());
        groupLayout.setHorizontalGroup(
                groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(display, GroupLayout.DEFAULT_SIZE, 1280, Short.MAX_VALUE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGap(3))
        );
        groupLayout.setVerticalGroup(
                groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(display, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 720, Short.MAX_VALUE))
                                .addContainerGap())
        );

        getContentPane().setLayout(groupLayout);

        scene.setDisplay(display);
        scene.paint();
        addKeyListener(this);

    }

    @Override
    public void keyPressed(KeyEvent arg0) {
        int code = arg0.getKeyCode();
        switch (code) {

            case KeyEvent.VK_UP:
                scene.translateForward();
                break;
            case KeyEvent.VK_DOWN:
                scene.translateBack();
                break;
            case KeyEvent.VK_LEFT:
                scene.translateLeft();
                break;
            case KeyEvent.VK_RIGHT:
                scene.translateRight();
                break;
            case KeyEvent.VK_COMMA:
                scene.translateUp();
                break;
            case KeyEvent.VK_PERIOD:
                scene.translateBottom();
                break;
            case KeyEvent.VK_A:
                scene.turnLeft();
                break;
            case KeyEvent.VK_D:
                scene.turnRight();
                break;
            case KeyEvent.VK_W:
                scene.turnUp();
                break;
            case KeyEvent.VK_S:
                scene.turnDown();
                break;
            case KeyEvent.VK_PAGE_UP:
                scene.zoomIN();
                break;
            case KeyEvent.VK_PAGE_DOWN:
                scene.zoomOUT();
                break;

        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        ;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        ;
    }
}