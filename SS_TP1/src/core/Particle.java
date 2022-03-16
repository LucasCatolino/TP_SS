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
    	double dist= center.distance(p.getCenter()) - radius - p.radius;
    	//if particles are overlapped, distance betwen borders is zero
        return (dist > 0) ? dist : 0;
    }

    public void move(double x, double y){
        center.move(x, y);
    }

    @Override
    public String toString() {
        return center.toString();
    }
}
