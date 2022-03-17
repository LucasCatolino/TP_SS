package core.cim;

import core.Particle;
import jdk.internal.org.jline.reader.impl.history.DefaultHistory;
import jdk.internal.org.objectweb.asm.tree.TryCatchBlockNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class space {


    private final List<Particle>[] mySpace;
    private final int L;
    private final int M;
    private final int numProjCell;
    private final int cellsCant;
    

    public space(int l, int m) {
        numProjCell = l/m;
        mySpace = (List<Particle>[]) new List[(numProjCell*numProjCell)];
        //mySpace = new ArrayList<>(l*l);
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


    //TODO: testear
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
        if(cell < 0 || (cell-numProjCell)%numProjCell == 0)//estoy en un borde de la derecha
            return -1;
        return ++cell;
    }

    private int topCellNum(int cell){
        if(cell < 0 || (cell+=numProjCell) > numProjCell*numProjCell)//estoy en el borde superior
            return -1;
        return cell;
    }
    private int bottomCellNum(int cell){
        if(cell < 0 || (cell-=numProjCell) < 0)//estoy en el borde inferiro
            return -1;
        return cell;
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
	public List<Particle> getNeighbors(Particle particle) { //TODO: ver si se puede refactorizar mas lindo con menos ifs y fors, sino a casa
		int actualCell= this.getCell(particle);
		List<Particle> neighborsList= new ArrayList<Particle>();
		
		if (actualCell + this.numProjCell < cellsCant) {			
			List<Particle> possibleUp= this.getParticles(actualCell + this.numProjCell);
			if (possibleUp != null) {
				for (Particle part : possibleUp) {
					neighborsList.add(part);
				}
			}
			
			List<Particle> possibleUpR= this.getParticles(actualCell + this.numProjCell + 1);
			if (possibleUpR != null) {
				for (Particle part : possibleUpR) {
					neighborsList.add(part);
				}				
			}
		}
		
		if (actualCell + 1 < numProjCell) {			
			List<Particle> possibleR= this.getParticles(actualCell + 1);
			if (possibleR != null) {
				for (Particle part : possibleR) {
					neighborsList.add(part);
				}
			}
		}
		
		if (actualCell - this.numProjCell + 1 > 0) {
			List<Particle> possibleDownR= this.getParticles(actualCell - this.numProjCell + 1);
			if (possibleDownR != null) {
				for (Particle part : possibleDownR) {
					neighborsList.add(part);
				}				
			}
		}
		
		return neighborsList;
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
