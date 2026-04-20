package Bodies;

public class Star extends Body{
    public double density = 1408;

    public Star(double mass, double x, double y) {
        super(mass, x, y);
        this.radius = findRadius();
    }

    public Star(double mass, double x, double y, double density) {
        super(mass, x, y);
        this.density = density;
        this.radius = findRadius();
    }

    public Star(double mass, double x, double y, double initX, double initY) {
        super(mass, x, y, initX, initY);
        this.radius = findRadius();
    }

    public Star(double mass, double x, double y, double initX, double initY, double density) {
        super(mass, x, y, initX, initY);
        this.density = density;
        this.radius = findRadius();
    }
}
