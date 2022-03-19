package CIM;

import java.util.*;

public class Space{
    protected final List<Particle>[] mySpace;
    protected final int spaceSize;
    protected final int numOfCells;
    protected final int cellSize;
    protected final int totalCells;

    public Space(int spaceSize, int numOfCells) {
        if(spaceSize%numOfCells != 0)
            throw new IllegalArgumentException();
        this.spaceSize = spaceSize;
        this.numOfCells = numOfCells; //el la cantidad de celdas de un lado del cuadrado
        this.totalCells = numOfCells*numOfCells;
        this.cellSize = spaceSize/numOfCells;
        mySpace = (List<Particle>[]) new List[(totalCells)];

    }

    public int totalCells(){
        return totalCells;
    }

    //agrega una particula al espacio
    public int add(Particle p){
        int particleCell = getCell(p);
        if(mySpace[particleCell] == null){
            mySpace[particleCell]= new ArrayList<>();
        }
        mySpace[particleCell].add(p);
        return particleCell;
    }
    protected int getCell(Particle p){//dada una particula devuelve la celda a la que pertenece
        double x = p.getCenter().getX();
        double y = p.getCenter().getY();

        double xProjections = getProjections(x);
        double yProjections = getProjections(y);

        return (int) (xProjections +( numOfCells * (yProjections)));
    }
    protected double getProjections(double num){//se utiliza en add
        double aux = num - num % cellSize;
        return (aux/cellSize);
    }

    //devuelve una lista con todas la particulas de la celda
    public List<Particle> getParticles(int cell){
        if(cell < 0 || cell > totalCells-1)
            return null;
        return mySpace[cell];
    }

    /*
    metodos de celdas vecinas
     */
    public List<Particle> getAllNeighboringParticles(int cell){
        List<Particle> toReturn = new ArrayList<>();
        union(toReturn, mySpace[cell]);
        union(toReturn, LeftCellParticles(cell));
        union(toReturn, rightCellParticles(cell));
        union(toReturn, topCellParticles(cell));
        union(toReturn, bottomCellParticles(cell));
        union(toReturn, topRightCellParticles(cell));
        union(toReturn, topLeftParticles(cell));
        union(toReturn, bottomRightCellParticles(cell));
        union(toReturn, bottomLeftCellParticles(cell));
        return  toReturn;
    }

    //addALl pero apcepta que l2 sea null
    protected void union(List<Particle> l1, List<Particle> l2){
        if(l2 != null){
            l1.addAll(l2);
        }
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

    ///funciones de calculo de celdas vecinas
    private int leftCellNum(int cell){
        if(cell < 0 || cell%numOfCells == 0)//estoy en un borde de la izquierda
            return -1;
        return --cell;
    }
    private int rightCellNum(int cell){
        if(cell < 0 || (cell+1)%numOfCells == 0 )//estoy en un borde de la derecha
            return -1;
        return ++cell;
    }

    private int topCellNum(int cell){
        if(cell < 0 || (cell+numOfCells) >= totalCells)//estoy en el borde superior
            return -1;
        return cell+numOfCells;
    }
    private int bottomCellNum(int cell){
        if(cell < 0 || (cell-numOfCells) < 0)//estoy en el borde inferiro
            return -1;
        return cell-numOfCells;
    }

    @Override
    public String toString() {
        return Arrays.toString(mySpace);
    }

}
