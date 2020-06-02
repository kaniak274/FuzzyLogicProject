package degrees;

import java.util.ArrayList;
import java.util.List;

import terms.TermData;

// T8
public class SummarizerCardinality {
    public static double calculate(List<TermData> sets) {        
        List<Double> sumarizersCardinality = new ArrayList<>();
        
        for (TermData set : sets) {
        	double sigmaCount = set.getSet().getSigmaCount(set.getSet().getDoubleStreamOfSet());
        	double sigmaCountCompliment = set.getSet().getSigmaCountCompliment(set.getSet().getDoubleStreamOfSet());

            sumarizersCardinality.add(sigmaCount / sigmaCountCompliment);
        }
        
        double product = sumarizersCardinality.stream().reduce(1.00, (acc, value) -> acc * value);
        
        return 1.00 - Math.pow(product, 1.00 / sets.size());
    }
}
