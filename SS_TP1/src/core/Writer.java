package core;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Writer {

    public Writer(int L, int N) {
        try {
            File file = new File("./resources/static" + L + ".txt");
            FileWriter myWriter = new FileWriter("./resources/static" + L + ".txt");
            myWriter.write("" + L + "\n");
            myWriter.write("" + N + "\n");
            try {
            	this.randomizeParticles(L,N, myWriter);				
			} catch (Exception e) {
				System.err.println("IOException");
			}
            myWriter.close();
            System.out.println("Successfully wrote to the file ./resources/static" + L + ".txt");
        } catch (IOException e) {
            System.out.println("IOException ocurred");
            e.printStackTrace();
        }
    }

	private void randomizeParticles(int l, int n, FileWriter myWriter) throws IOException {
		for (int i = 0; i < n; i++) {			
			double x= Math.random()*l;
			double y= Math.random()*l;
			myWriter.write("" + x + "\t" + "" + y + "\n");
		}

	}
}
