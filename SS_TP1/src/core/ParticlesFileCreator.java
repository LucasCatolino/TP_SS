package core;
import java.io.*;


@SuppressWarnings("unused") //Warnings because Writer is not used, it only creates files
public class ParticlesFileCreator {
    public static void main(String[] args) throws  IllegalArgumentException, SecurityException, IOException {

    	System.out.println("Insert L");
        BufferedReader readerL = new BufferedReader(new InputStreamReader(System.in));
        String auxL = readerL.readLine();
 
    	System.out.println("Insert N");
        BufferedReader readerN = new BufferedReader(new InputStreamReader(System.in));
        String auxN = readerN.readLine();

    	int L= Integer.parseInt(auxL);
    	int N= Integer.parseInt(auxN);


    	System.out.println("L: " + L + " N: " + N);
    	
		Writer writerDinamic = new Writer(L, N, "dynamic");
        Writer writerStatic = new Writer(L, N, "static");

    }





}
