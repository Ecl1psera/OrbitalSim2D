package orbitalSim;

import orbitalSim.Bodies.Body;
import orbitalSim.Bodies.Star;
import orbitalSim.Bodies.BlackHole;
import orbitalSim.Bodies.Planet;

import orbitalSim.Interface.GlWindow;



public class Main {
    public static double MAXSTEPS = 1e100;
    public static double STEPTIME = 1000; // How long a step lasts for in seconds
    public static void main(String[] args) {
        Body earth = new Planet(5.97e24, 0, 149000000000D, 29780, 0);
        Body mars = new Planet(6.39e23, 0, 228000000000D, 24000, 0, 3933);
        //Body moon = new Planet(7.34e22, 0, planet1.radius+384400000, 1e3, 0);
        Body sol = new Star(BlackHole.SOL, 0, 0);
        //Body star2 = new Star(BlackHole.SOL, 0, star1.radius*50, 100000, 0);
        //Body person = new Body(80, 0, 400000+planet1.radius, 7670, 0);
        Body[] bodies = {sol, earth, mars};
        mainLoop(bodies);
    }
    
    public static void mainLoop(Body[] bodies) {
        GlWindow window = new GlWindow(bodies);
        boolean interrupted = window.pause;
        boolean running = true;

       
        while(running) {
            interrupted = window.pause;
            if (!interrupted){
                for (Body b : bodies) {
                    b.updatePos(STEPTIME);
                }

                for (Body b : bodies) {
                    b.updateAccel(bodies);
                }

                for (Body b : bodies) {
                    b.updateVel(STEPTIME);
                }
            }

            running = window.paint();

            try {
                Thread.sleep(0,1);
            } catch (InterruptedException e){
                System.out.println("interupted");
            }
            
        }
    }

    public static void createBody() {

    }
}
