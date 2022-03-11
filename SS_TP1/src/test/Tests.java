package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;

import core.Particula;

public class Tests {

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test01CrearParticula() {
		Particula particula= new Particula(0.3700, 1.0000, 100);
		System.out.println("x: " + particula.getX() + " y:" + particula.getY());
		assertNotNull(particula);
	}

}
