package core.cim;

import core.Particle;
//import jdk.internal.org.jline.reader.impl.history.DefaultHistory;
//import jdk.internal.org.objectweb.asm.tree.TryCatchBlockNode;

import javax.swing.text.StyledEditorKit;
import java.util.*;

public class Space{
    private final List<Particle>[] mySpace;
    private final int L;
    private final int M;
    private final int numProjCell;
    private final int cellsCant;

    private final boolean withEdges;
    //clave id valor la particula;
    private Map<Integer,vParticle> virtualCell;


    public Space(int l, int m, boolean withEdges) {
        this.withEdges = withEdges;
        numProjCell = l/m;
        mySpace = (List<Particle>[]) new List[(numProjCell*numProjCell)];
        if(!withEdges){
            virtualCell = new HashMap<>();
        }
        L = l;
        M = m;
        cellsCant= numProjCell * numProjCell;
    }

    //agrega una particula al espacio
    public void add(Particle p){
        int index = getCell(p);
        if(mySpace[index] == null){
            mySpace[index]= new ArrayList<>();
        }
        mySpace[index].add(p);
        if(!withEdges || isRightBorderCell(index) || isTopBorderCell(index)){
            virtualCell.put(p.getID(), virtualizeParticle(p, index));
            //virtualCell.putIfAbsent(p.getID(),index);
            //virtualCell.get(index).add(virtualizeParticle(p));
        }
    }

    /*
    comprobación de las celdas bordes
     */
    private boolean isRightBorderCell(int index){
        return index % L == 0;
    }

    private boolean isLeftBorderCell(int index){
        return index % L == L - 1;
    }

    private boolean isTopBorderCell(int index){
        return index >= (L * (L - 1));
    }

    private boolean isBottomBorderCell(int index){
        return index < L;
    }

    private boolean isBorderCell(int index){
        return isRightBorderCell(index) || isLeftBorderCell(index)
                || isTopBorderCell(index) || isBottomBorderCell(index);
    }

    /*
    celdas y particulas virtualizadas
     */
    private vParticle virtualizeParticle(Particle p, int cell){
        //distancia hasta el final de la grilla
        double rightX = L - p.getCenter().getX();
        double leftX = p.getCenter().getX()%M;
        double topY = M - p.getCenter().getY()%M;
        //ditancia hasta el piso
        double bottomY = p.getCenter().getY();
        return  new vParticle(rightX, leftX, topY, bottomY, cell);
    }

    public double getDistance(Particle p1, int cellOfP1, int p2Id){
        if(!withEdges && virtualCell.containsKey(p2Id)){
            vParticle vPcle = virtualCell.get(p2Id);
            if(isRightBorderCell(cellOfP1) && isLeftBorderCell(vPcle.cell)){
                return Math.sqrt((Math.pow((p1.getCenter().getX()-vPcle.rightX),2)
                        +Math.pow((p1.getCenter().getY()-vPcle.bottomY),2)));
            }
            if(isBottomBorderCell(cellOfP1) && isTopBorderCell(p2Id)){
                return Math.sqrt((Math.pow((p1.getCenter().getX()-vPcle.leftX),2)
                        +Math.pow((p1.getCenter().getY()-vPcle.topY),2)));
            }
        }
        return -1;
    }


    /*
    general
     */

    public int totalCells(){
        return numProjCell*numProjCell;
    }

    //se utilizan en add
    //dada una particula devuelve la celda a la que pertenece
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

    //devuelve una lista con todas la particulas de la celda
    public List<Particle> getParticles(int cell){
        if(cell < 0 || cell > numProjCell*numProjCell)
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
        if(cell < 0 || cell%numProjCell == 0)//estoy en un borde de la izquierda
            return -1;
        return --cell;
    }
    private int rightCellNum(int cell){
        if(cell < 0 || (cell+1)%numProjCell == 0 )//estoy en un borde de la derecha
            return -1;
        return ++cell;
    }

    private int topCellNum(int cell){
        if(cell < 0 || (cell+numProjCell) >= numProjCell*numProjCell)//estoy en el borde superior
            return -1;
        return cell+numProjCell;
    }
    private int bottomCellNum(int cell){
        if(cell < 0 || (cell-numProjCell) < 0)//estoy en el borde inferiro
            return -1;
        return cell-numProjCell;
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

    //Given a particle it returns half of the neighbor cells (Up, Up Right, Right, Down Right)
    private List<Particle> avoidNullList(List<Particle> l) {
        if (l != null){
            return l;
        }
        return new ArrayList<>();
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

class vParticle {
    double rightX;
    double leftX;
    double topY;
    double bottomY;
    int cell;

    public vParticle(double rightX, double leftX, double topY, double bottomY, int cell) {
        this.rightX = rightX;
        this.leftX = leftX;
        this.topY = topY;
        this.bottomY = bottomY;
    }

}
