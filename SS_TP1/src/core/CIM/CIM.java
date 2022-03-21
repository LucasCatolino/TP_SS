package core.CIM;


import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class CIM {
    private final Space mySpace;
    private int cantOfParticle = 0;
    private final Map<Integer, Map<Integer, Boolean>> interactions;

    public CIM(int spaceSize, int numOfCell, boolean infiniteSpace) {
        interactions = new HashMap<>();
        if (infiniteSpace) {
            mySpace = new InfiniteSpace(spaceSize, numOfCell);
        }else{
            mySpace = new Space(spaceSize, numOfCell);
        }

    }

    public void add(Particle p) {
        mySpace.add(p);
        cantOfParticle++;
    }

    public void solveAlgorithm() {
        //recorrer mySpace celda a celda buscando los vecinos
        for (int currentCell = 0; currentCell < mySpace.totalCells(); currentCell++) {

            List<Particle> particlesOfCurrentCell = mySpace.getParticles(currentCell); //todas la particulas de la celda currentCell
            if (particlesOfCurrentCell == null) {
                continue;//no hay partiluas en esta celda
            }

            for (Particle currentP : particlesOfCurrentCell) { //itero por particlesOfCurrentCell
                interactions.putIfAbsent(currentP.getID(), new HashMap<>());
                //busco todas las posibles particulas que puden interactua con currentP
                List<Particle> AllNeighboringParticles = mySpace.getAllNeighboringParticles(currentCell);

                //veo si estas
                for (Particle neighboringParticle : AllNeighboringParticles) {
                    //primero me fijo si son la misma particula
                    if(neighboringParticle.equals(currentP)){
                        continue; //paso a la suguiente
                    }
                //veo si neighboringParticle y currentP interactuan
                    //Primero me fijo si ya guarde esta interaccion
                    if (interactions.containsKey(neighboringParticle.getID()))
                        if (interactions.get(neighboringParticle.getID()).containsKey(currentP.getID()))
                            continue; //ya se si estas particulas interactuan o no

                    //sino me fijo si estas partuclas interacuan
                    if (currentP.interactWith(neighboringParticle)) {
                        //si interactuan.
                        interactions.get(currentP.getID()).put(neighboringParticle.getID(), true);
                        //como interactúan mutuamente tambien agrego al revés
                        interactions.putIfAbsent(neighboringParticle.getID(), new HashMap<>());
                        interactions.get(neighboringParticle.getID()).put(currentP.getID(), true);
                    } else {
                        //no interactuan.
                        interactions.get(currentP.getID()).put(neighboringParticle.getID(), false);
                        //como NO interactúan mutuamente tambien agrego al revés
                        interactions.putIfAbsent(neighboringParticle.getID(), new HashMap<>());
                        interactions.get(neighboringParticle.getID()).put(currentP.getID(), false);
                    }

                }//salgo del for parso al siguinte neighboringParticle

            }//salgo del for paso al siguiente currentP
        }//salgo del for, paso al siguiente currentCell

    }//termino

    public void printResults(String fileName) {
        StringBuilder fileString = new StringBuilder();

        for (int id = 0; id < cantOfParticle; id++) {
            if(!interactions.containsKey(id)){continue;}
            fileString.append(id).append(" ");

            for (Map.Entry<Integer, Boolean> entry : interactions.get(id).entrySet()) {
                if(entry.getValue()){
                    fileString.append(entry.getKey()).append(" ");
                }
            }
            fileString.append("\n");
        }

        try (PrintWriter outFile = new PrintWriter(fileName)) {
            outFile.println(fileString);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //no chequea con bordes infinitos
    public boolean interactionsCheck(List<Particle> allParticle) {
        int aux = 0;
        for (Particle currentP : allParticle) {
            aux++;
            for (Particle p : allParticle) {
                if (currentP.getID() == p.getID()) {
                    continue;//son la misma particula
                }
                if (currentP.interactWith(p)) {
                    if(interactions.containsKey(currentP.getID())){
                        if(interactions.get(currentP.getID()).containsKey(p.getID())){
                            if (!interactions.get(currentP.getID()).get(p.getID())) {
                                System.out.println("error: "+currentP.getID()+"y "+p.getID()+" si interactuan");
                                return false;
                            }
                        }else{
                            System.out.println("el pnt: "+currentP.getID()+ "no contiene al pnt: "+p.getID());
                            return false;
                        }
                    }else{
                        System.out.println("interactions no contiene al pnt: "+currentP.getID());
                        return false;
                    }

                }
            }
        }
        if(aux != cantOfParticle){
            System.out.println("error en la cantidad de partculas");
            System.out.println("cim = "+ cantOfParticle );
            System.out.println("contadas = "+ aux );
            return false;
        }
        return true;
    }


}