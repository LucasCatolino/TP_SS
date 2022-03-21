package CIM;

import java.util.ArrayList;
import java.util.List;

public class InfiniteSpace extends Space {

    private final List<Particle>[] virtualSpace;
    private final int size ;

    public InfiniteSpace(int spaceSize, int numOfCells) {
        super(spaceSize, numOfCells);
        size = numOfCells+numOfCells+2;
        virtualSpace = (List<Particle>[]) new List[(size)];
    }

    @Override
    public int add(Particle p) {
        int particleCell = super.add(p); //la agrgo como simpre

        //si la celda tiene una celda virtual la agrego
        if(isTopBorderCell(particleCell)){
            int vCell = numOfCells+2+ particleCell%numOfCells;
            addVirtualP(p.getVirtual(0, spaceSize), vCell);
        }
        if(isRightBorderCell(particleCell)){
            int vCell = (numOfCells+1)-(particleCell+1)/numOfCells;
            addVirtualP(p.getVirtual(spaceSize, 0), vCell);
        }
        if(isTopRightCornerCell(particleCell)){
            addVirtualP(p.getVirtual(spaceSize, spaceSize), numOfCells+1);
        }
        if(isBottomRightCornerCell(particleCell)){
            addVirtualP(p.getVirtual(spaceSize, -spaceSize), 0);
        }
        return particleCell;
    }

    private void addVirtualP(Particle p, int vCell){
        if(virtualSpace[vCell] == null){
            virtualSpace[vCell] = new ArrayList<>();
        }
        virtualSpace[vCell].add(p);
    }

    @Override
    public List<Particle> getAllNeighboringParticles(int cell) {
        //busco los vecions como siempre
        List<Particle> toReturn = super.getAllNeighboringParticles(cell);

        //si es una celda bode izquierdo o borde inferior le gargo sus vecion viruales
        if( isLeftBorderCell(cell) || isBottomBorderCell(cell)){
            union(toReturn, getVirtualNeighboringParticles(cell));
        }
        return toReturn;
    }

    private List<Particle> getVirtualNeighboringParticles(int cell){
        List<Particle> toReturn = new ArrayList<>();
        if(cell == 0){
            union(toReturn,virtualSpace[numOfCells]);
            union(toReturn, virtualSpace[numOfCells+1]);
            union(toReturn, virtualSpace[numOfCells+2]);
            return toReturn;
        }
        if(isLeftBorderCell(cell)){
            int vCell = numOfCells - (cell/numOfCells);
            union(toReturn,virtualSpace[vCell]);
            union(toReturn, virtualSpace[vCell-1]);
            union(toReturn, virtualSpace[vCell+1]);
            return toReturn;
        }
        if(isBottomBorderCell(cell)){
            int vCell = cell+numOfCells+2;
            union(toReturn,virtualSpace[vCell]);
            union(toReturn, virtualSpace[vCell-1]);
            if(!(vCell+1 >= size))
                union(toReturn, virtualSpace[vCell+1]);
            return toReturn;
        }
        System.out.println("Error1");
        return null;
    }

    /*
    checkeo de las celdas bordes
     */
    //si es cell borde superior
    private boolean isTopBorderCell(int index){
        return index >= numOfCells*(numOfCells-1);
    }
    //si es cellda borede izquierdo
    private boolean isRightBorderCell(int index){
        return index%numOfCells == numOfCells-1;
    }
    //si es la celda de arriba a la derecha;
    private boolean isTopRightCornerCell(int index){
        return index == numOfCells*numOfCells-1;
    }
    //si es la celda de arriba a la derecha;
    private boolean isBottomRightCornerCell(int index){
        return index == numOfCells-1;
    }
    //si es una celda del borde izquierdo
    private boolean isLeftBorderCell(int index){
        return index%numOfCells == 0;
    }
    //si es una celda del borde inferior y menos la celda 0
    private boolean isBottomBorderCell(int index){
        return index != 0 && index < numOfCells-1;
    }

    /*
    retonan las celdas virtualmentes continuas

    private int vLeftCellNum(int cell){
        if(!isLeftBorderCell(cell))
            return -1;//no es una celda del borde izquierdo
        return cell+numOfCells-1;
    }
    private int vBottomCellNum(int cell){
        if(isBottomBorderCell(cell))
            return -1;//no es una celda del borde inferior
        return cell+(numOfCells*(numOfCells-1));
    }

    private int vRightBottomCellNum(int cell){

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
*/


}
