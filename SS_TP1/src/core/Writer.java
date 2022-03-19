import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Writer {

    public Writer(int L, int N, String type) {
        try {
            File file = new File("resources/" + type  + ".txt");
            FileWriter myWriter = new FileWriter("resources/" + type  + ".txt");
            //myWriter.write("" + L + "\n");
            //myWriter.write("" + N + "\n");
            try {
            	if (type.compareTo("dynamic") == 0) {
					this.randomizePositions(L, N, myWriter);
				} else {
					this.randomizeParticles(L, N, myWriter);
				}
			} catch (Exception e) {
				System.err.println("IOException");
			}
            myWriter.close();
            System.out.println("Successfully wrote to the file ./resources/" + type + L + ".txt");
        } catch (IOException e) {
            System.out.println("IOException ocurred");
            e.printStackTrace();
        }
    }

	private void randomizeParticles(Integer l, int n, FileWriter myWriter) throws IOException {
		myWriter.write("" + n + "\n");
		myWriter.write("" + l + "\n");
		for (int i = 0; i < n; i++) {
			double rad= 0.25;
			int prop= 1;
			myWriter.write("" + rad + "\t" + "" + prop + "\n");
		}
		
	}

	private void randomizePositions(int l, int n, FileWriter myWriter) throws IOException {
		myWriter.write("0\n");
		for (int i = 0; i < n; i++) {			
			double x= Math.random()*l;
			double y= Math.random()*l;
			myWriter.write("" + x + "\t" + "" + y + "\n");
		}

	}
}
