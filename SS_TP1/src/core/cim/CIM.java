package core.cim;

import core.Particle;
import core.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CIM {
    private final Space space;
    final private int N;
    final private double rc;
    final private String[] fileName; //se usa para getNextParticle sepa que archivos lee
    private Map<Particle, List<Particle>> neighbors;

    public CIM(int l, int m, int n, String[] fileName, double rc) {
        N = n;
        this.fileName = fileName;
        space = new Space(l, m);
        this.rc = rc;
    }

    //lee los archivos y va devolviendo de a una particulas
    private Particle getNextParticle(){
        return new Particle(new Point(1,1) ,1, 1, 1);
    }


    public void solveAlgorithm(){
        //guardo todas las particulas en space
        for (int i = 0; i < N; i++) {
            space.add(getNextParticle());
        }

        //recorrer space celda a celda buscando los vecinos
        for (int i = 0; i <space.size(); i++) {
            //busco todas las particulas de la celda i
            List<Particle> Particles = space.getParticles(i);
            if(Particles == null){break;}//puede que no tenga ninguna particula

            for (Particle p : Particles) {
                //busco la particula i y la agregó al mapa de vecionos
                neighbors.put(p, new ArrayList<>());


                //con estos metodos consigo las partículas  vecinas de
                //las 8 celadas vecinas de la celda i
                //TODO: arreglar esta feo
                List<Particle> p1 = space.LeftCellParticles(i);
                List<Particle> p2 = space.rightCellParticles(i);
                List<Particle> p3 = space.bottomCellParticles(i);
                List<Particle> p4 = space.topCellParticles(i);
                List<Particle> p5 = space.bottomLeftCellParticles(i);
                List<Particle> p6 = space.bottomRightCellParticles(i);
                List<Particle> p7 = space.topLeftParticles(i);
                List<Particle> p8 = space.topRightCellParticles(i);

                //queda interar p por todas las listas y ver en cuales
                //tiene distancia menor a rc
                //las que tiene distancia menor a rc se juardan em Map()
            }

        }




    }

}
