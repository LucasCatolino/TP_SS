package core;

public class Particle {

    private Point center;
    private double radius;

    public Particle(Point pnt, double radius) {
        this.center = pnt;
        this.radius = radius;
    }

    public Point getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }

    public double distance(Particle p){
        //TODO: que pasa si estan superpuestas, devueleve negativos
        return center.distance(p.getCenter()) - radius - p.radius;
    }

    public void move(double x, double y){
        center.move(x, y);
    }

    @Override
    public String toString() {
        return center.toString();
    }
}
