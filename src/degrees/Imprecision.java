package degrees;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import memberships.Trapezoid;
import terms.TermData;

public class Imprecision {
    public static double calculate(List<TermData> sets) {
        List<Double> ins = new ArrayList<>();
        
        List<TermData> sumarizer = IntStream.range(0, sets.size())
            .filter(i -> i != 0)
            .mapToObj(i -> sets.get(i))
            .collect(Collectors.toList());

        for (TermData set : sumarizer) {
            double result = 0;
            ArrayList<Double> scope = set.getTerm().getScope();
            
            int minIndex = scope.indexOf(Collections.min(scope));
            int maxIndex = scope.indexOf(Collections.max(scope));

            double min = scope.get(minIndex);
            double max = scope.get(maxIndex);
            
            result = Math.abs(max - min);

            double universe = Math.abs(set.getUniverse().get(1) - set.getUniverse().get(0));
        	
            ins.add(result / universe);
        }
        
        double insProduct = ins.stream().reduce(1.00, (acc, value) -> acc * value);
        return 1.00 - Math.sqrt(insProduct);
    }
}
