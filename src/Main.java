import javax.swing.JFrame;

import Bodies.*;
import Interface.*;

public class Main {
    public static double MAXSTEPS = 1e100;
    public static double STEPTIME = 10; // How long a step lasts for in seconds
    public static void main() {
        Body planet1 = new Planet(5.97e24, 0, 0);
        Body moon = new Planet(7.34e22, 0, planet1.radius+384400000, 1e3, 0);
        Body star1 = new Star(BlackHole.SOL, 0, 0);
        Body star2 = new Star(BlackHole.SOL, 0, star1.radius*100, 0, 0);
        //Body person = new Body(80, 0, 400000+planet1.radius, 7670, 0);
        Body[] bodies = {star1, star2};
        mainLoop(bodies);
    }
    
    public static void mainLoop(Body[] bodies) {
        JFrame frame = Window.createWindow(bodies);
        Visual panel = (Visual) frame.getContentPane().getComponent(0);
        boolean interrupted = panel.pause;

        while(true) {
            interrupted = panel.pause;
            if (!interrupted){
                for (Body b : bodies) {
                    b.updateAccel(bodies);
                }

                for (Body b : bodies) {
                    b.updateVel(STEPTIME);
                }

                for (Body b : bodies) {
                    b.updatePos(STEPTIME);
                }

            }
            panel.repaint();

            System.out.println(bodies[0].pos[1] -  bodies[1].pos[1]);

            try {
                Thread.sleep(0, 1);
            } catch (InterruptedException e){
                System.out.println("interupted");
            }
            
        }
    }
    public static void updateObjects(Body[] bodies) {
        for (int i = 0; i < bodies.length; i++) {
            for (int j = i + 1; j < bodies.length; j++) {
                Body a = bodies[i];
                Body b = bodies[j];

                double dx = a.pos[0] - b.pos[0];
                double dy = a.pos[1] - b.pos[1];
                double distSq = dx*dx + dy*dy;

                double r = a.radius + b.radius;

                if (distSq <= r*r) {
                    a.isColliding(b);
                    b.isColliding(a);
                }
            }
        }
    }
}
