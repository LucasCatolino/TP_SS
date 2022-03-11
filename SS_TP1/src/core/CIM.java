package core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

import sun.security.action.OpenFileInputStreamAction;
import sun.tools.tree.ThisExpression;

public class CIM {
	
	private Scanner inputScanner;
	
	public CIM() throws FileNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		InputStream is = getClass().getClassLoader().getResourceAsStream("Static100.txt");
		inputScanner = new Scanner(is);
		
		String token;
		
		while(inputScanner.hasNext()) {
			token= inputScanner.next();
			System.out.println(token);
		}
		
		inputScanner.close();
		
	}
	
    public static void main(String[] args) throws FileNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
    	CIM file= new CIM();
    }



}
