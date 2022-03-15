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
		Particle particle= new Particle(point, radius);

		assertEquals(0.37, particle.getRadius(), 0.001);
		assertEquals(0.5, particle.getCenter().getX(), 0.01);
		assertEquals(0.5, particle.getCenter().getY(), 0.01);
	}

}
