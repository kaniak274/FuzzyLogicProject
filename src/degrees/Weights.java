package degrees;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Weights {
    public static List<Double> getWeights(int n) {
        List<Double> weights = new ArrayList<>();
        double sum = 0;

        for (int i = 0; i < n; i++) {
            Random r = new Random();
            double randomValue = 0.0 + (1.0 - 0.0) * r.nextDouble();

            sum += randomValue;
            weights.add(randomValue);
            
            if (sum >= 1) {
                break;
            }
        }
        
        for (int i = weights.size(); i < n; i++) {
            weights.add(0.0);
        }
        
        Collections.shuffle(weights);
        return weights;
    }
}
