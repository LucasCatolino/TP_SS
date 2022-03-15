package core;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;

import core.Writer;

public class ParticlesFileCreator {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, IOException {

    	System.out.println("Insert L");
        BufferedReader readerL = new BufferedReader(new InputStreamReader(System.in));
        String auxL = readerL.readLine();
 
    	System.out.println("Insert N");
        BufferedReader readerN = new BufferedReader(new InputStreamReader(System.in));
        String auxN = readerN.readLine();

    	int L= Integer.valueOf(auxL);
    	int N= Integer.valueOf(auxN);

    	System.out.println("L: " + (L+2) + " N: " + N);
        Writer writer = new Writer(L, N);
    	//CIM file= new CIM();
    }

}
