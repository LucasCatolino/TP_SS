package core.cim;

import core.Particle;
import core.Point;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class space {

    private final List<Particle>[] mySpace;
    private final int L;
    private final int M;
    private final int numProjCell;

    public space(int l, int m) {
        numProjCell = l/m;
        mySpace = (List<Particle>[]) new List[(numProjCell*numProjCell)];
        //mySpace = new ArrayList<>(l*l);
        L = l;
        M = m;

    }

    //agrega una particula al espacio
    public void add(Particle p){
        int index = getCell(p);
        if(mySpace[index] == null){
            mySpace[index]= new ArrayList<>();
        }
        mySpace[index].add(p);
    }

    //se utilizan en add
    //dada una partícula devuelve la celda a la que pertenece
    private int getCell(Particle p){
        double x = p.getCenter().getX();
        double y = p.getCenter().getY();

        double xProjections = getProjections(x);
        double yProjections = getProjections(y);

        return (int) (xProjections +( numProjCell * (yProjections)));
    }

    //se utiliza en add
    private double getProjections(double num){
        double aux = num - num %M;
        return (aux/M);
    }

    //devulve una lista con todas la particulas
    //que pertenecen a la celda cell
    public List<Particle> getParticles(int cell){
        return mySpace[cell];
    }

    public List<Particle> previousCell(int cell){
        if(cell-1 )
        return mySpace[cell];
    }

    //intenta imprimir una especie de gráfico de space
    public void print(){
        for (int y = numProjCell-1; y >= 0; y--) {
            System.out.print(y+" |");
            for (int x = 0; x<numProjCell; x++) {
                System.out.print(mySpace[(x+(numProjCell*y))]);
                System.out.print(" | ");
            }
            System.out.println();
        }
    }

    @Override
    public String toString() {
        return Arrays.toString(mySpace);
    }


    /*
    public static void main(String[] args){
        int l=100, m=5, n=1000, maxr =2;
        List<Particle> particleList = new ArrayList<>();
        for (int i = 0; i < n ; i++) {
            int x = (int)(Math.random()*(l-1));
            int y = (int)(Math.random()*(l-1));
            int r = 1;
            Point p = new Point(x,y);
            particleList.add(new Particle(p,r));
        }

        space s = new space(l,m);
        for (int i = 0; i < n; i++) {
            s.add(particleList.get(i));
        }
        System.out.println("terminado");
        System.out.println("");
        System.out.println("imprimo las p creadas");
        System.out.println(particleList.toString());
        System.out.println("");
        System.out.println("grafico");
        System.out.println("");
        s.print();
        System.out.println("");
        System.out.println("el vector");
        System.out.println(s.toString());
    }
    */
}
