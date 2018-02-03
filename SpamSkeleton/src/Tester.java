import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Tester {
    //Takes in trained NB model and testFile path
    public double getAccuracy(NBClassifier nb, String testFile) {
        double count = 0;
        double numCorrect = 0;
        BufferedReader br = null;
        String line = "";

        try {
            br = new BufferedReader(new FileReader(testFile));
            br.readLine(); // this will skips the first line

            while ((line = br.readLine()) != null) {
                String[] example = line.split(",");
                String label = example[0];
                String text = example[1];
                String prediction = nb.predict(text);
                count++;
                if(prediction != null && label.equalsIgnoreCase(prediction)) {
                    numCorrect++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
            if(count == 0) {
            return 0;
            }
         return (numCorrect/count);
    }
}
