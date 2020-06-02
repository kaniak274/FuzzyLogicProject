package degrees;

import java.util.ArrayList;
import java.util.Collections;

import memberships.Trapezoid;
import terms.TermData;

// T9
public class QualifierImprecision {
    public static double calculate(TermData qualifier) {
        ArrayList<Double> scope = qualifier.getTerm().getScope();
        double result = 0;

        if (qualifier.getMembership() instanceof Trapezoid) {
            int minIndex = scope.indexOf(Collections.min(scope));
            int maxIndex = scope.indexOf(Collections.max(scope));

            double min = scope.get(minIndex);
            double max = scope.get(maxIndex);
            
            result = Math.abs(max - min);
        } else {
            result = qualifier.getTerm().getScope().get(1) - qualifier.getTerm().getScope().get(0);
        }
        
        result /= Math.abs(qualifier.getUniverse().get(1) - qualifier.getUniverse().get(0));
        return Math.abs(1.00 - result);
    }
}
