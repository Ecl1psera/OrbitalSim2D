package OrbitalSim2D.bodies;

public class Planet extends Body{
    public double density = 5513; //kg/m^3

    public Planet(double mass, double x, double y) {
        super(mass, x, y);
        this.radius = findRadius();
    }

    public Planet(double mass, double x, double y, double density) {
        super(mass, x, y);
        this.density = density;
        this.radius = findRadius();
    }

    public Planet(double mass, double x, double y, double initX, double initY) {
        super(mass, x, y, initX, initY);
        this.radius = findRadius();
    }

    public Planet(double mass, double x, double y, double initX, double initY, double density) {
        super(mass, x, y, initX, initY);
        this.density = density;
        this.radius = findRadius();
    }

    public double findRadius() {
        double vol = this.mass / this.density;
        return Math.cbrt((3 * vol) / (4 * Math.PI));
    }

    public void merge(Body smallPlanet) {
        this.mass += smallPlanet.mass;
        this.radius = findRadius();
        this.vel = new double[] {this.vel[0] + smallPlanet.vel[0], this.vel[1] + smallPlanet.vel[1]};
        this.pos = new double[] {(this.pos[0] + smallPlanet.pos[0])/2, (this.pos[1] + smallPlanet.pos[1])/2};
    }
}
