package test;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Test;
import core.Particle;
import core.Point;


public class Tests {

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test01CrearParticula() {
		Point point= new Point(0.5, 0.5);
		double radius= 0.37;
		Particle particle= new Particle(point, radius, 1, 1);

		assertEquals(0.37, particle.getRadius(), 0.001);
		assertEquals(0.5, particle.getCenter().getX(), 0.01);
		assertEquals(0.5, particle.getCenter().getY(), 0.01);
	}
	
	@Test
	public void test02DistanciaEntreParticulas() {
		Point point1= new Point(1, 1);
		double radius1= 0.5;
		Particle particle1= new Particle(point1, radius1, 1, 1);
		
		Point point2= new Point(3, 1);
		double radius2= 0.5;
		Particle particle2= new Particle(point2, radius2, 2, 1);

		assertEquals(1.0, particle1.distance(particle2), 0.01);
		assertEquals(1.0, particle2.distance(particle1), 0.01);
	}
	
	@Test
	public void test03DistanciaEntreParticulasSuperpuestasEsNula() {
		Point point1= new Point(1, 1);
		double radius1= 0.5;
		Particle particle1= new Particle(point1, radius1, 1, 1);
		
		Point point2= new Point(1, 1);
		double radius2= 0.7;
		Particle particle2= new Particle(point2, radius2, 2, 1);

		assertEquals(0.0, particle1.distance(particle2), 0.01);
		assertEquals(0.0, particle2.distance(particle1), 0.01);
	}
	
	@Test
	public void test04DistanciaEntreParticulasSuperpuestasEsNula() {
		Point point1= new Point(1, 1);
		double radius1= 0.5;
		Particle particle1= new Particle(point1, radius1, 1, 1);
		
		Point point2= new Point(2, 1);
		double radius2= 0.7;
		Particle particle2= new Particle(point2, radius2, 2, 1);

		assertEquals(0.0, particle1.distance(particle2), 0.01);
		assertEquals(0.0, particle2.distance(particle1), 0.01);
	}


}
