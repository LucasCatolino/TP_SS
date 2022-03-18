package core.cim;

import core.Particle;
import core.Point;
import core.VirtualParticle;
//import jdk.internal.org.jline.reader.impl.history.DefaultHistory;
//import jdk.internal.org.objectweb.asm.tree.TryCatchBlockNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Space {


    private final List<Particle>[] mySpace;
    private final int L;
    private final int cellSize;
    private final int M;
    private final int cellsCant;


    public Space(int l, int m) {
        cellsCant= m*m;
        mySpace = (List<Particle>[]) new List[(cellsCant)];
        //mySpace = new ArrayList<>(l*l);
        L = l;
        cellSize = l/m;
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

    public int size(){
        return M*M;
    }

    //se utilizan en add
    //dada una particula devuelve la celda a la que pertenece
    private int getCell(Particle p){
        double x = p.getCenter().getX();
        double y = p.getCenter().getY();

        double xProjections = getProjections(x);
        double yProjections = getProjections(y);

        return (int) (xProjections +( M * (yProjections)));
    }

    //se utiliza en add
    private double getProjections(double num){
        double aux = num - num %cellSize;
        return (aux/cellSize);
    }


    //TODO: testear
    //devuelve una lista con todas la particulas de la celda
    public List<Particle> getParticles(int cell){
        if(cell < 0 || cell > M*M)
            return null;
        return mySpace[cell];
    }

    //devuelve las particulas de las celda izquierda
    public List<Particle> LeftCellParticles(int cell){
        return getParticles(leftCellNum(cell));
    }

    //devuelve las particulas de las celda derecha
    public List<Particle> rightCellParticles(int cell){
        return getParticles(rightCellNum(cell));
    }

    //devuelve las particulas de las celda de arriba
    public List<Particle> topCellParticles(int cell){
        return getParticles(topCellNum(cell));
    }

    //devuelve las particulas de las celda de abajo
    public List<Particle> bottomCellParticles(int cell){
        return getParticles(bottomCellNum(cell));
    }
    //devuelve las particulas de las celda diagonal arriba der
    public List<Particle> topRightCellParticles(int cell){
        return getParticles(rightCellNum(topCellNum(cell)));
    }
    //devuelve las particulas de las celda diag arriba izq
    public List<Particle> topLeftParticles(int cell){
        return getParticles(leftCellNum(topCellNum(cell)));
    }
    //devuelve las particulas de las celda diag abajo der
    public List<Particle> bottomRightCellParticles(int cell){
        return getParticles(rightCellNum(bottomCellNum(cell)));
    }
    //devuelve las particulas de las celda diag abajo izq
    public List<Particle> bottomLeftCellParticles(int cell){
        return getParticles(leftCellNum(bottomCellNum(cell)));
    }









    //TODO: testear
    ///funciones de calculo de celdas vecinas
    private int leftCellNum(int cell){
        if(cell < 0 || cell%M == 0)//estoy en un borde de la izquierda
            return -1;
        return --cell;
    }
    private int rightCellNum(int cell){
        if(cell < 0 || (cell+1)%M == 0 )//estoy en un borde de la derecha
            return -1;
        return ++cell;
    }

    private int topCellNum(int cell){
        if(cell < 0 || (cell+M) >= M*M)//estoy en el borde superior
            return -1;
        return cell+M;
    }
    private int bottomCellNum(int cell){
        if(cell < 0 || (cell-M) < 0)//estoy en el borde inferiro
            return -1;
        return cell-M;
    }




    //intenta imprimir una especie de gráfico de space
    public void print(){
        for (int y = M-1; y >= 0; y--) {
            System.out.print(y+" |");
            for (int x = 0; x<M; x++) {
                System.out.print(mySpace[(x+(M*y))]);
                System.out.print(" | ");
            }
            System.out.println();
        }
    }

    @Override
    public String toString() {
        return Arrays.toString(mySpace);
    }


    private List<Particle> avoidNullList(List<Particle> l) {
        if (l != null){
            return l;
        }
        return new ArrayList<>();
    }


    private boolean isRightBorderCell(int index){
        return index % M == 0;
    }

    private boolean isLeftBorderCell(int index){
        return index % M == M - 1;
    }

    private boolean isTopBorderCell(int index){
        return index >= (M * (M - 1));
    }

    private boolean isBottomBorderCell(int index){
        return index < M;
    }

    private boolean isBorderCell(int i){
        return isLeftBorderCell(i) || isBottomBorderCell(i);
    }


    public List<VirtualParticle> getVirtualCellNeighbours(int i){
        List<VirtualParticle> virtualneighbours = new ArrayList<>();

        if(isBorderCell(i)){

            if(isLeftBorderCell(i) && getParticles(i + M-1) != null){
                for(Particle p : getParticles(i + M-1)){
                    virtualneighbours.add(new VirtualParticle(new Point(p.getCenter().getX() - L,p.getCenter().getY()), p.getID()));
                }
                if(isBottomBorderCell(i)&& getParticles(cellsCant-1) != null){
                    for(Particle p : getParticles(cellsCant-1)){
                        virtualneighbours.add(new VirtualParticle(new Point(p.getCenter().getX() - L,p.getCenter().getY() -L), p.getID()));
                    }
                }
                if(isTopBorderCell(i) && getParticles(M-1) != null){
                    for(Particle p : getParticles(M-1)){
                        virtualneighbours.add(new VirtualParticle(new Point(p.getCenter().getX() - L,p.getCenter().getY() + L), p.getID()));
                    }
                }
            }
            if(isBottomBorderCell(i) && getParticles((i + M*(M-1))-1 )!= null){
                for(Particle p : getParticles((i + M*(M-1))-1 )){
                    virtualneighbours.add(new VirtualParticle(new Point(p.getCenter().getX(),p.getCenter().getY() - L), p.getID()));
                }
            }
        }
        return  virtualneighbours;
    }
    public List<Particle> getCellNeighbours(int i){
        List<Particle> cellneighbours = new ArrayList<>();


        cellneighbours.addAll(avoidNullList(LeftCellParticles(i)));
        cellneighbours.addAll(avoidNullList(rightCellParticles(i)));
        cellneighbours.addAll(avoidNullList(bottomCellParticles(i)));
        cellneighbours.addAll(avoidNullList(topCellParticles(i)));
        cellneighbours.addAll(avoidNullList(bottomLeftCellParticles(i)));
        cellneighbours.addAll(avoidNullList(bottomRightCellParticles(i)));
        cellneighbours.addAll(avoidNullList(topLeftParticles(i)));
        cellneighbours.addAll(avoidNullList(topRightCellParticles(i)));
        cellneighbours.addAll(avoidNullList(getParticles(i)));

        return cellneighbours;
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