package degrees;

import java.util.ArrayList;
import java.util.List;

import terms.TermData;

// T4
public class Appropriateness {
    public static double calculate(List<TermData> sets, double t3) {
        List<Double> supports = new ArrayList<>();
        double count = sets.get(0).getSet().getFuzzySet().size();
        
        for (TermData set : sets) {
        	supports.add((double) set.getSet().getSupport().count());
        }
        
        double product = supports.stream().reduce(1.0, (acc, value) -> acc * (value / count));
        return Math.abs(product - t3);
    }
}
