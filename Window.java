package OrbitalSim2D;

import javax.swing.JFrame;

import OrbitalSim2D.bodies.Body;

public class Window {

    public static JFrame createWindow(Body[] bodies) {
        JFrame frame = new JFrame("Orbital Sim");

        Visual panel = new Visual(bodies);

        frame.add(panel);
        frame.setSize(1000, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        return frame;
    }
}