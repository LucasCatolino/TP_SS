package core.cim;

import core.Particle;
import core.Point;
import core.ParticlesFileCreator;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class CIM {
    private final Space space;
    final private int N;
    final private double rc;
    //final private String[] fileName; //se usa para getNextParticle sepa que archivos lee
    private Map<Particle, List<Particle>> neighbors;

    public CIM(int l, int m, int n, double rc) {
        N = n;
        //this.fileName = "fileName";
        space = new Space(l, m);
        this.rc = rc;
        neighbors = new HashMap<>();
    }




    //lee los archivos y va devolviendo de a una particulas
    private Particle getNextParticle(){
        return new Particle(new Point(1,1) ,1, 1, 1);
    }


    public void solveAlgorithm(){
        //guardo todas las particulas en space
        //recorrer space celda a celda buscando los vecinos
        for (int i = 0; i < space.size()-2; i++) {

            List<Particle> cellParticles = space.getParticles(i);
            List<Particle> cellNeighboursParticles = space.getCellNeighbours(i);

            if(cellParticles != null) {//puede que no tenga ninguna particula
                for (Particle p : cellParticles) {
                    neighbors.put(p, space.identifyNeighbours(p, cellNeighboursParticles));
                }
            }
        }

    }

    public void printResults(){
        System.out.println("Printing neighbours!");
        for(Particle p : neighbors.keySet()){
            StringBuilder strb = new StringBuilder();
            strb.append(p.getID()).append(" ");
            for(Particle pa : neighbors.get(p)){
                strb.append(pa.getID()).append(" ");
            }
            System.out.println(strb);
        }
    }

    public static void main(String[] args) throws IOException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException, InterruptedException {
        //TODO: esto deberia irse a la mierda y deberiamos recibir los nombres de los archivos., no crearlos acá.
        String L = ParticlesFileCreator.createFiles(args); //se crean los archivos
        Scanner inputScanner;
        InputStream staticstream = CIM.class.getClassLoader().getResourceAsStream("static" + L + ".txt");
        inputScanner = new Scanner(staticstream);

        Double tokenr, tokenrc, tokenx,tokeny;
        int l = Integer.parseInt(inputScanner.next()); //L
        int n = Integer.parseInt(inputScanner.next()); //N
        CIM cim = new CIM(l,5,n,1);
        int id = 0;
        InputStream dinamicstream = CIM.class.getClassLoader().getResourceAsStream("dinamic" + L + ".txt");
        Scanner ds = new Scanner(dinamicstream);
        ds.next();


        while(inputScanner.hasNext() && ds.hasNext()) {
            tokenr= Double.parseDouble(inputScanner.next());
            tokenrc = Double.parseDouble(inputScanner.next());
            tokenx = Double.parseDouble(ds.next());
            tokeny = Double.parseDouble(ds.next());
            cim.space.add(new Particle(new Point(tokenx,tokeny) ,tokenr, id, tokenrc));
            System.out.println(id + ": " + tokenr + " " + tokenrc + " " + tokenx + " " + tokeny);
            id++;
        }
        ds.close();
        inputScanner.close();

        cim.solveAlgorithm();
        cim.printResults();



    }

}
