package degrees;

import java.util.ArrayList;
import java.util.Collections;

import memberships.Trapezoid;
import terms.TermData;

public class QuantifierImprecision {
    public static double calculate(TermData qualifier, boolean isAbsolute) {
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
        
        double universe = 1;
        
        if (isAbsolute) {
            universe = qualifier.getSet().getFuzzySet().size();
        }

        return 1.00 - (result / universe);
    }
}
