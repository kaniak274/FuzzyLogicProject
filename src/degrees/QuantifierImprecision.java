package degrees;

import java.util.ArrayList;
import java.util.Collections;

import terms.Term;

// T6
public class QuantifierImprecision {
    public static double calculate(Term matchingTerm) {
    	ArrayList<Double> scope = matchingTerm.getScope();
        double result = 0;

        if (scope.size() == 4) {
            int minIndex = scope.indexOf(Collections.min(scope));
            int maxIndex = scope.indexOf(Collections.max(scope));

            double min = scope.get(minIndex);
            double max = scope.get(maxIndex);
            
            result = Math.abs(max - min);
        } else {
            result = scope.get(1) - scope.get(0);
        }

        return Math.abs(1.00 - result);
    }
    
    public static double calculate(double matchingElements, double records) {
        return 1 - (matchingElements / records);
    }
}
