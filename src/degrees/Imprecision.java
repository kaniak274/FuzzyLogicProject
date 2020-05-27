package degrees;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import terms.TermData;

public class Imprecision {
    public static double calculate(List<TermData> sets) {
        List<Double> ins = new ArrayList<>();
        
        for (TermData set : sets) {
            ArrayList<Double> scope = set.getTerm().getScope();
            int minIndex = scope.indexOf(Collections.min(scope));
            int maxIndex = scope.indexOf(Collections.max(scope));
        	
            double min = scope.get(minIndex);
            double max = scope.get(maxIndex);
        	
            double universe = set.getUniverse().get(1);
        	
            ins.add((max - min) / universe);
        }
        
        double insProduct = ins.stream().reduce(1.00, (acc, value) -> acc * value);        
        return 1.00 - Math.sqrt(insProduct);
    }
}
