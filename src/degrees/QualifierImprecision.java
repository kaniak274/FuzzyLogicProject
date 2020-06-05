package degrees;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import memberships.Trapezoid;
import terms.TermData;

// T9
public class QualifierImprecision {
    public static double calculate(List<TermData> qualifier) {
    	if (qualifier.size() == 1) {
    	    ArrayList<Double> scope = qualifier.get(0).getTerm().getScope();
            double result = 0;

            if (qualifier.get(0).getMembership() instanceof Trapezoid) {
                int minIndex = scope.indexOf(Collections.min(scope));
                int maxIndex = scope.indexOf(Collections.max(scope));

                double min = scope.get(minIndex);
                double max = scope.get(maxIndex);
                
                result = Math.abs(max - min);
            } else {
                result = qualifier.get(0).getTerm().getScope().get(1) - qualifier.get(0).getTerm().getScope().get(0);
            }
            
            result /= Math.abs(qualifier.get(0).getUniverse().get(1) - qualifier.get(0).getUniverse().get(0));
            return Math.abs(1.00 - result);
    	} else {
    		List<Double> ins = new ArrayList<>();
    		
    	    for (int i = 0; i < qualifier.size(); i++) {
    	    	ArrayList<Double> scope = qualifier.get(i).getTerm().getScope();
                double result = 0;

                if (qualifier.get(i).getMembership() instanceof Trapezoid) {
                    int minIndex = scope.indexOf(Collections.min(scope));
                    int maxIndex = scope.indexOf(Collections.max(scope));

                    double min = scope.get(minIndex);
                    double max = scope.get(maxIndex);
                    
                    result = Math.abs(max - min);
                } else {
                    result = qualifier.get(i).getTerm().getScope().get(1) - qualifier.get(i).getTerm().getScope().get(0);
                }
                
                result /= Math.abs(qualifier.get(i).getUniverse().get(1) - qualifier.get(i).getUniverse().get(0));
                ins.add(result);
    	    }
    	    
    	    double sum = ins.stream().reduce(1.00, (acc, value) -> acc * value);
    	    return 1 - Math.pow(sum, 1.0 / (double) qualifier.size());
    	}
    }
}
