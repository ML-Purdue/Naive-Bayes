import java.util.ArrayList;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class NBClassifier {
    private HashMap<String,Double> spamHM;      //Hashmap containing P(word|spam) for each word that appears in spam messages
    private HashMap<String,Double> hamHM;       //Hashmap containing P(word|ham) for each word that appears in ham messages
    private String csvFileName;                 //The filepath to the csv file
    private int spamCount;                      //The total # of times 'spam' appears in data
    private int hamCount;                       //The total # of times 'spam' appears in data

    public NBClassifier(String filename) {
        spamHM = new HashMap<>();
        hamHM = new HashMap<>();
        csvFileName = filename;
    }

    public void learn(){
        //TODO
        /*Calculate the frequency tables:
        1. Create two hashmaps: one for spam, another for ham(not spam)
        2. Loop through the data; when you encounter a 'spam' example,
                    a.get all the words in the text
                    b. increment each words' counter in the spam hashmap.
        3. Once completed the hashmap, divide all values by the sum of all values in the hashmap
            to obtain P(word_x|class)


        Do the same for 'ham' examples but with the ham hashmap.*/
        BufferedReader br = null;
        String line = "";
        try {
            br = new BufferedReader(new FileReader(csvFileName));
            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] example = line.split(",");
                String label = example[0];
                String text = example[1]; //Array of words in text example
                //Do something if label is 'spam',
                //Do something else if label is 'ham
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }

    private double getClassProbability(String c) {
        //TODO
        //Calculate P(Class). P(class) = # of times 'class' is in the data / total # of examples in data
        return 0.0;
    }

    //Helper function to parse text into individual words. Returns a String array containing the individual words
    public String[] parseText(String text) {
        text = text.replaceAll("[!?,]", "");
        String[] words = text.split("\\s+");
        return words;
    }

    public String predict(String text) {
        //TODO
        //You can now predict values.
        //  a. Find P(spam | some_text) and P(ham | some_text) using the hashmaps. P(spam | some_text) ≈ P(spam)P(word_1|spam)*P(word_2|spam)*...* P(word_n|spam)
        //  b. If P(spam | some_text) > P(ham | some_text), return 'spam', else return 'ham'
        return null;
    }
}
