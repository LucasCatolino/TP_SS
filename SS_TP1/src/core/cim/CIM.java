package core.cim;

import core.Particle;
import core.Point;
import core.ParticlesFileCreator;
import core.VirtualParticle;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static java.lang.Thread.sleep;

public class CIM {
    private final Space space;
    final private int N;
    final private double rc;
    final static int M = 10;
    final static double RC = 0.001;
    //final private String[] fileName; //se usa para getNextParticle sepa que archivos lee
    private Map<Integer, List<Integer>> neighbors;

    public CIM(int l, int m, int n, double rc) {
        N = n;
        //this.fileName = "fileName";
        space = new Space(l, m);
        this.rc = rc;
        neighbors = new HashMap<>();
    }






    public List<Integer> identifyNeighbours(Particle myp, List<Particle> plist){
        List<Integer> toRet = new ArrayList<>();
        for(Particle p : plist){
            if(myp.distance(p) <= myp.getCritic() && myp.getID() != p.getID()){
                toRet.add(p.getID());

            }
        }
        return toRet;
    }

    public List<Integer> identifyVirtualNeighbours(Particle myp, List<VirtualParticle> plist){
        List<Integer> toRet = new ArrayList<>();
        for(VirtualParticle p : plist){
            double aux;
            if( p.distance(myp)<= myp.getCritic() && myp.getID() != p.getId()){
                toRet.add(p.getId());
                neighbors.putIfAbsent(p.getId(), new ArrayList<>());
                neighbors.get(p.getId()).add(myp.getID(), p.getId());
            }
        }
        return toRet;
    }


    public void solveAlgorithm(){
        //guardo todas las particulas en space
        //recorrer space celda a celda buscando los vecinos
        for (int i = 0; i < space.size(); i++) {

            List<Particle> cellParticles = space.getParticles(i);
            List<Particle> cellNeighboursParticles = space.getCellNeighbours(i);
            List<VirtualParticle> virtualNeighbours = space.getVirtualCellNeighbours(i);
            if(cellParticles != null) {//puede que no tenga ninguna particula
                for (Particle p : cellParticles) {
                    neighbors.putIfAbsent(p.getID(),new ArrayList<>());
                    neighbors.get(p.getID()).addAll(p.getID(), identifyNeighbours(p, cellNeighboursParticles));
                    neighbors.get(p.getID()).addAll( identifyVirtualNeighbours(p,virtualNeighbours));

                }
            }
        }

    }

    public void printResults(){
        System.out.println("Printing neighbours!");
        StringBuilder strb = new StringBuilder();
        List<Integer> ns = new ArrayList<>(neighbors.keySet());
        ns.sort(Integer::compareTo);

        for(Integer p : ns){
            strb.append(p).append(" ");
            for(Integer id : neighbors.get(p)){
                strb.append(id).append(" ");
            }
            strb.append("\n");
        }
        try (PrintWriter out = new PrintWriter("output.txt")) {
            out.println(strb);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException, InterruptedException {
        InputStream dinamicstream = CIM.class.getClassLoader().getResourceAsStream("dinamic" + ".txt");
        Scanner ds = new Scanner(dinamicstream);
        InputStream staticstream = CIM.class.getClassLoader().getResourceAsStream("static"  + ".txt");
        Scanner is = new Scanner(staticstream);

        Double tokenr, tokenrc, tokenx,tokeny;
        int n = Integer.parseInt(is.next()); //N
        int l = Integer.parseInt(is.next()); //L
        CIM cim = new CIM(l,M,n,0.001);
        int id = 0;
        ds.next(); //t0

        int i = 0;
        while(is.hasNext() && ds.hasNext() && i < n) {
            i++;
            tokenr= Double.parseDouble(is.next());
            tokenrc = Double.parseDouble(is.next());
            tokenx = Double.parseDouble(ds.next());
            tokeny = Double.parseDouble(ds.next());
            cim.space.add(new Particle(new Point(tokenx,tokeny) ,tokenr, id, tokenrc));
            System.out.println(id + ": " + tokenr + " " + tokenrc + " " + tokenx + " " + tokeny);
            id++;
        }
        ds.close();
        is.close();

        long start = System.currentTimeMillis();
        cim.solveAlgorithm();
        long finish = System.currentTimeMillis();
        long duration = finish - start;
        System.out.println("Runtime: " + duration + "ms");
        cim.printResults();

    }

}
