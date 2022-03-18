package core;

public class VirtualParticle {
    private Point center;
    private int id;
    public Point getCenter() {
        return center;
    }

    public int getId() {
        return id;
    }

    public VirtualParticle(Point pnt, int id) {
        this.center = pnt;
        this.id = id;
    }

    public double distance(Particle p){
        double dist= center.distance(p.getCenter()) - 0.25 - p.getRadius();
        //if particles are overlapped, distance betwen borders is zero
        return (dist > 0) ? dist : 0;
    }

}
