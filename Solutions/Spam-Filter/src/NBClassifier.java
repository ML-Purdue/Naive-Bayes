import java.util.ArrayList;
import java.util.Arrays;
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
            br.readLine(); // this will skips the first line
            while ((line = br.readLine()) != null) {
                String[] example = line.split(",");  // use comma as separator
                String label = example[0];
                String text = example[1];
                ArrayList<String> words = parseText(text); //Array of words in text example

                HashMap<String, Double> hm = hamHM;
                if (label.equalsIgnoreCase("spam")) {
                    hm = spamHM;
                    spamCount++;
                } else hamCount++;

                for (String word : words) {
                    updateHM(hm, word);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }

    private void updateHM(HashMap<String,Double> hm,String word ) {
        double count = hm.containsKey(word) ? hm.get(word) : 0;
        hm.put(word, count + 1);
    }
    private double getClassProbability(String c) {
        //Calculate P(Class). P(class) = # of times 'class' is in the data / total # of examples in data
        if (c.equalsIgnoreCase("spam")) {
            return (double)spamCount/(spamCount+hamCount);
        }
        if (c.equalsIgnoreCase("ham")) {
            return (double)hamCount/(spamCount+hamCount);
        }
        System.out.printf("Error: %s is not a class!", c);
        return 0;
    }

    //Helper function to parse text into individual words. Returns a String array containing the individual words
    public ArrayList<String> parseText(String text) {
        text = text.replaceAll("[!?,]", "");
        String[] words = text.split("\\s+");
        ArrayList<String> words_list = new ArrayList<String>(Arrays.asList(words));

        return words_list;
    }

    public String predict(String text) {
        //You can now predict values.
        //  a. Find P(spam | some_text) and P(ham | some_text) using the hashmaps. P(spam | some_text) ≈ P(spam)P(word_1|spam)*P(word_2|spam)*...* P(word_n|spam)
        //  b. If P(spam | some_text) > P(ham | some_text), return 'spam', else return 'ham'
        double ham_prior = getClassProbability("ham") ;
        double spam_prior = getClassProbability("spam") ;

        double condProbHam = 1.0;
        double condProbSpam = 1.0;
        ArrayList<String> words = parseText(text);

        double spamSum = 0.0;
        double hamSum = 0.0;

        for(String word : words) {
            double value = 0;
            if(hamHM.containsKey(word)) {
                value = hamHM.get(word);
            }
            hamSum += value;
            condProbHam*=(value+1);
        }

        for(String word : words) {
            double value = 0;
            if(spamHM.containsKey(word)) {
                value = spamHM.get(word);
            }
            spamSum += value;
            condProbSpam*=(value+1);
        }

        condProbHam/=(hamSum+1);
        condProbSpam/=(spamSum+1);

        double finalHam = ham_prior*condProbHam;
        double finalSpam = spam_prior*condProbSpam;

        if(finalHam > finalSpam) {
            return "ham";
        }else return "spam";
    }
}
