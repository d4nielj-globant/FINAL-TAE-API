package utils;


import java.util.Random;

public abstract class ModelsUtils {

    /**
     * @return one of the 4 different transaction types Strings randomly
     */
    public static String getRandomValueForTransactionType() {
       int n = new Random().nextInt(4);
       if (n == 0) return "withdrawal";
       if (n == 1) return "payment";
       if (n == 2) return "deposit";
       return "invoice";
    }
}
