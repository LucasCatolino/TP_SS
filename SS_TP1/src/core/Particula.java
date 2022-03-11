package core;

public class Particula {

    private Point pnt;
    private double radius;

    public Particula(double radius, double interaction, int L) {
        this.pnt = randomInit(L);
        this.radius = radius;
    }

	private Point randomInit(int limit) {
		double x= Math.random()*limit;
		double y= Math.random()*limit;
		return new Point(x, y);
	}

	public double getX() {
		return pnt.getX();
	}
	
	public double getY() {
		return pnt.getY();
	}
}
