package OrbitalSim2D.bodies;

public class BlackHole extends Body {
    public static double SOL = 1.989e30;

    public BlackHole(long mass, double x, double y) {
        super(mass, x, y);
        this.radius = schwarzschild();
    }

    public BlackHole(long mass, double x, double y, double initX, double initY) {
        super(mass, x, y, initX, initY);
        this.radius = schwarzschild();
    }

    private double schwarzschild() {
        return ((2*G*this.mass) / (C*C));
    }

    public void merge(Body smallPlanet) {
        this.mass += smallPlanet.mass;
        this.vel = new double[] {this.vel[0] + smallPlanet.vel[0], this.vel[1] + smallPlanet.vel[1]};
    }

    
}
