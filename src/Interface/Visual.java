package Interface;

import javax.swing.*;

import Bodies.Body;

import java.awt.*;
import java.awt.event.*;

public class Visual extends JPanel {

    private Body[] bodies;
    private double scale = 1e6;
    private double camX = 0;
    private double camY = 0;
    private double camSpeed = -10*scale;
    private boolean camLock = true;

    public boolean pause = true;

    public Visual(Body[] bodies) {
        this.bodies = bodies;
        setBackground(Color.BLACK);

        setFocusable(true);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    scale *= 0.8; // zoom in
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    scale *= 1.25; // zoom out
                }
                if (e.getKeyCode() == KeyEvent.VK_A) {
                    camX += camSpeed;
                }
                if (e.getKeyCode() == KeyEvent.VK_D) {
                    camX -= camSpeed;
                }
                if (e.getKeyCode() == KeyEvent.VK_W) {
                    camY += camSpeed;
                }
                if (e.getKeyCode() == KeyEvent.VK_S) {
                    camY -= camSpeed;
                }
                if (e.getKeyCode() == KeyEvent.VK_E) {
                    camLock = !camLock;
                }
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    pause = !pause;
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.WHITE);

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        if(camLock) {
            Body focus = bodies[0];
            camX = focus.pos[0];
            camY = focus.pos[1];
        }

        for (Body i : bodies) {
            double relX = i.pos[0] - camX;
            double relY = i.pos[1] - camY;

            int x = centerX + (int)(relX / scale);
            int y = centerY + (int)(relY / scale);

            int r = Math.max(3, (int)(i.radius / scale));

            g.fillOval(x - r, y - r, r * 2, r * 2);
        }
        g.drawString(String.valueOf(pause), 10, 10);

    }

}