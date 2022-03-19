import CIM.CIM;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import CIM.*;
import javax.swing.plaf.synth.SynthRootPaneUI;


public class main {
	/*
	public static void main(String[] args){
		InputStream dynamicStream = CIM.class.getClassLoader().getResourceAsStream("dinamic" + ".txt");
		assert dynamicStream != null;
		InputStream staticStream = CIM.class.getClassLoader().getResourceAsStream("static" + ".txt");
		assert staticStream != null;

		Scanner dynamicScanner = new Scanner(dynamicStream);
		Scanner staticScanner = new Scanner(staticStream);
		int numOfParticles = Integer.parseInt(staticScanner.next()); //N
		int spaceSize = Integer.parseInt(staticScanner.next()); //L

		double tokenR, tokenRc, tokenX, tokenY;

		dynamicScanner.next(); //t0

		int i = 0, id=0;
		List<Particle> allParticle = new ArrayList<>();
		while (staticScanner.hasNext() && dynamicScanner.hasNext() && i < numOfParticles) {
			i++;
			tokenR = Double.parseDouble(staticScanner.next());
			tokenRc = Double.parseDouble(staticScanner.next());
			tokenX = Double.parseDouble(dynamicScanner.next());
			tokenY = Double.parseDouble(dynamicScanner.next());
			Particle newParticle = new Particle(new Point(tokenX, tokenY), tokenR, id, tokenRc);
			allParticle.add(newParticle);
			id++;
		}
		dynamicScanner.close();
		staticScanner.close();

		/*
		COMIENZO DEL ALGORITMO
		 */
	/*
		long startTime = System.nanoTime();

		CIM cim = new CIM(spaceSize, 20, true);
		for (Particle p : allParticle) {
			cim.add(p);
		}
		cim.solveAlgorithm();

		long endTime = System.nanoTime();
		/*
		FIN DEL ALGORITMO
		 */
	/*
		System.out.println("Finished.   "+(endTime-startTime)/1e6 +" ms");
		System.out.println();

		cim.printResults("resources/output.txt");

		//solo chequea las interaciones en el espacio finito
		boolean flag = cim.interactionsCheck(allParticle); ;
		System.out.println("check :"+ (flag? "OK" : "error"));
	}
	*/

	//prueba de CIM sin archivos
	public static void main(String[] args){
		int l=15; //space size
		int m=3;   //num of cells
		int n=10; //num of particles

		//crea los archivos
		List<Particle> particleList = new ArrayList<>();
		for (int i = 0; i < n ; i++) {
			double x = (Math.random()*(l-1));
			double y = (Math.random()*(l-1));
			double r = 1;
			double rc = 3;
			Point p = new Point(x,y);
			particleList.add(new Particle(p, r, i, rc));
		}

		//tomo el time incial
		long startTime = System.nanoTime();

		//creo la clase del algoritmo
		CIM cim = new CIM(l, m , true);

		//cargo todas la particulas
		for (Particle p : particleList) {
			cim.add(p);
		}
		cim.solveAlgorithm();

		long endTime = System.nanoTime();
		System.out.println("termino el algoritmo, timepo:"+ (endTime-startTime)/1e6 + " ms");

		//chequeo si esta bien
		long startTime2 = System.nanoTime();
		boolean flag = cim.interactionsCheck(particleList);
		long endTime2 = System.nanoTime();
		if(flag) {System.out.println("OK");};

		System.out.println("timpo de fueza bruta: "+ (endTime2-startTime2)/1e6 + " ms");
	}


}
