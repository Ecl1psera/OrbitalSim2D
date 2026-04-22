package orbitalSim.Interface;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import orbitalSim.Bodies.Body;

public class Window {

    public static JFrame createWindow(Body[] bodies) {
        JFrame frame = new JFrame("Orbital Sim");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        Visual panel = new Visual(bodies);

        frame.add(panel);
        frame.setSize(screenSize.width, screenSize.height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setVisible(true);

        return frame;
    }
}