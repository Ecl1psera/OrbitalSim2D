package Bodies;

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
    
    @Override
    public void merge(Body objBody) {
        double[] momThis = {this.mass * this.vel[0], this.mass * this.vel[1]};
        double[] momObj = {objBody.mass * objBody.vel[0], objBody.mass * objBody.vel[1]};
        this.mass += objBody.mass;
        this.vel = new double[] {(momThis[0] + momObj[0])/this.mass, (momThis[1] + momObj[1])/this.mass};
    }

    
}
