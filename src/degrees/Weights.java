package degrees;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Weights {
    public static List<Double> getWeights(int n) {
        double start = 1;
        List<Double> weights = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            Random r = new Random();
            double randomValue = 0.1 + (0.2 - 0.1) * r.nextDouble();

            start -= randomValue;

            if (start < 0) {
                weights.add(randomValue);
                break;
            } else {
                weights.add(randomValue);
            }
        }
        
        int size = weights.size();
        int lastElemIndex = size - 1;
        
        if (start < 0) {
            weights.set(lastElemIndex, weights.get(lastElemIndex) - Math.abs(start));
        } else if (start > 0) {
            weights.set(lastElemIndex, weights.get(lastElemIndex) + start);
        }
        
        if (size != n) {
            for (int i = size; i < n; i++) {
                weights.add(0.0);
            }
        }
        
        Collections.shuffle(weights);
        return weights;
    }
}
