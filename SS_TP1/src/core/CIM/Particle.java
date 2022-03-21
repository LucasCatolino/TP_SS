package core.CIM;


import java.util.Objects;

public class Particle {

    private final Point center;
    private final double radius;
    private final int id;
    private final double criticR;

    public Particle(Point pnt, double radius, int id, double rc) {
        this.center = pnt;
        this.radius = radius;
        this.id= id;
        this.criticR= rc;

    }
    /*
    getters
     */
    public Point getCenter() {
        return center;
    }

    public int getID() {
        return this.id;
    }

    /*
    funciones utiles
     */
    public boolean interactWith(Particle p){
        return distance(p) <= criticR;
    }

    public double distance(Particle p){
        //if particles are overlapped, distance betwen borders is zero
        return center.distance(p.getCenter()) - radius - p.radius;
    }

    public Particle getVirtual(double x, double y){
        double newX = getCenter().x - x;
        double newY = getCenter().y - y;
        return new Particle(new Point(newX, newY), radius, id, criticR);
    }

    /*
    no se usan
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Particle)) return false;
        Particle particle = (Particle) o;
        return id == particle.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return center.toString();
    }

}
