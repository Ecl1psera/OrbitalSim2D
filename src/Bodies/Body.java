package Bodies;

public class Body {
    protected static double G = 6.6743e-11; //m3 kg-1 s-2
    protected static int C = 299792458; // M/S
    public double mass; // KG
    public double[] pos = new double[2]; // M
    public double[] vel = new double[2]; // M/S
    public double[] accel = new double[2]; // M/S^2
    public double[] prevAccel = new double[2]; // M/S^2; to calcualte velocity through verlet
    public double radius; // M
    public double[] force = new double[2]; // N
    public double density = 5513; // KG/M^3


    public Body(double mass, double x, double y) {
        this.mass = mass;
        this.pos = new double[] {x,y};
        this.vel = new double[] {0,0};
    }

    public Body(double mass, double x, double y, double initX, double initY) {
        this.mass = mass;
        this.pos = new double[] {x,y};
        this.vel = new double[] {initX, initY};
    }

    public boolean isColliding(Body other) {
        double dx = this.pos[0] - other.pos[0];
        double dy = this.pos[1] - other.pos[1];
        double r = this.radius + other.radius;
        return dx*dx + dy*dy <= r*r;
    }

    public void update(Body[] bodies, double stepTime) {
        updateAccel(bodies);
        updateVel(stepTime);
        updatePos(stepTime);
    }

    public double[] calcForce(Body[] bodies) {
        double[] forces = new double[2];

        for (Body i : bodies) {
            if (i == this) continue;

            double dx = i.pos[0] - this.pos[0];
            double dy = i.pos[1] - this.pos[1];

            double distSq = dx*dx + dy*dy;
            if (distSq == 0) continue;

            double dist = Math.sqrt(distSq);
            double force = G * (this.mass * i.mass) / distSq;

            forces[0] += force * (dx / dist);
            forces[1] += force * (dy / dist);
        }

        return forces;
    }
    
    public void updateAccel(Body[] bodies) {
        force = calcForce(bodies);
        prevAccel = accel;
        accel[0] = force[0]/this.mass;
        accel[1] = force[1]/this.mass;
    }

    public void updateVel(double stepTime) {
        vel[0] += 0.5*(accel[0] + prevAccel[0]) * stepTime;
        vel[1] += 0.5*(accel[1] + prevAccel[1]) * stepTime;
    }

    public void updatePos(double stepTime) {
        pos[0] = pos[0] + (vel[0] * stepTime) + 0.5*(accel[0]*(stepTime*stepTime));
        pos[1] = pos[1] + (vel[1] * stepTime) + 0.5*(accel[1]*(stepTime*stepTime));
    }

    public double findRadius() {
        double vol = this.mass / this.density;
        return Math.cbrt((3 * vol) / (4 * Math.PI));
    }

    public void merge(Body objBody) {
        double[] momThis = {this.mass * this.vel[0], this.mass * this.vel[1]};
        double[] momObj = {objBody.mass * objBody.vel[0], objBody.mass * objBody.vel[1]};
        this.mass += objBody.mass;
        this.radius = findRadius();
        this.vel = new double[] {(momThis[0] + momObj[0])/this.mass, (momThis[1] + momObj[1])/this.mass};
        this.pos = new double[] {(this.pos[0] + objBody.pos[0])/2, (this.pos[1] + objBody.pos[1])/2};
    }
}


