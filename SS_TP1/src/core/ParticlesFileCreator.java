package core;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;

import core.Writer;

@SuppressWarnings("unused") //Warnings because Writer is not used, it only creates files
public class ParticlesFileCreator {
    public static String createFiles(String[] args) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, IOException {

    	System.out.println("Insert L");
        BufferedReader readerL = new BufferedReader(new InputStreamReader(System.in));
        String auxL = readerL.readLine();
 
    	System.out.println("Insert N");
        BufferedReader readerN = new BufferedReader(new InputStreamReader(System.in));
        String auxN = readerN.readLine();

    	int L= Integer.valueOf(auxL);
    	int N= Integer.valueOf(auxN);

    	System.out.println("L: " + L + " N: " + N);
    	
		Writer writerDinamic = new Writer(L, N, "dinamic");
        Writer writerStatic = new Writer(L, N, "static");

        return auxL;
    }

}
