
public class Runner {
    public static void main(String[] args) {
        //Load in the training csv file
        NBClassifier nb = new NBClassifier("src/spam_train.csv");
        nb.learn();

        Tester test = new Tester();
        double acc = test.getAccuracy(nb,"src/spam_test.csv");
        System.out.printf("Accuracy of your model: %.2f\n",acc);

    }
}
