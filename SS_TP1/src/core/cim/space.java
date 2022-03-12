package core.cim;

import core.Particle;

import java.util.ArrayList;
import java.util.List;

public class space {

    private List<List<Particle>> mySpace;

    public space(List<Particle> particles, Integer L, Integer M) {
        //TODO: chequear que los parametros sean validos
        //int numOfCell = (L/M)^2;
        mySpace = new ArrayList<>();
        for (Particle p : particles) {
            int index = getCell(p,M,L);
            if(mySpace.get(index) == null){
                //TODO: crear unn arrayList y gregar p
            }else
                mySpace.get(index).add(p);  //no se si puedo hacer esto
        }
    }

    private int getCell(Particle p,double M, double L){
        double x = p.getCenter().getX();
        double y = p.getCenter().getY();

        double xBase = getBase(x, M);
        double yBase = getBase(y, M);

        return (int) (xBase+( L/M * yBase));
    }

    private double getBase(double num, double M){
        double aux = num - num %M;
        return (aux/M) + 1;
    }

    


}
