package degrees;

import java.util.ArrayList;
import java.util.List;

import terms.TermData;

// T8
public class SummarizerCardinality {
    public static double calculate(List<TermData> sets) {        
        List<Double> sumarizersCardinality = new ArrayList<>();
        
        for (TermData set : sets) {
            sumarizersCardinality.add(set.getSet().getFuzzySet().size() / Math.abs(set.getUniverse().get(1) - set.getUniverse().get(0)));
        }
        
        double product = sumarizersCardinality.stream().reduce(1.00, (acc, value) -> acc * value);
        return 1.00 - Math.pow(product, 1.00 / sets.size());
    }
}
