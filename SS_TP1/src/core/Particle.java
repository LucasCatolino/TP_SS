package core;

public class Particle {

    private Point center;
    private double radius;
    private int id;
    private double criticR;

    public Particle(Point pnt, Double radius, int id, Double rc) {
        this.center = pnt;
        this.radius = radius;
        this.id= id;
        this.criticR= rc;
        
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

	public double getCritic() {
		return this.criticR;
	}
	
	public int getID() {
		return this.id;
	}
}
