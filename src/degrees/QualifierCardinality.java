package degrees;

import java.util.ArrayList;
import java.util.List;

import terms.TermData;

// T10
public class QualifierCardinality {
    public static double calculate(List<TermData> qualifier) {
    	if (qualifier.size() == 1) {
    	    double sigmaCount = qualifier.get(0).getSet().getSigmaCount(qualifier.get(0).getSet().getDoubleStreamOfSet());
            double sigmaCountCompliment = qualifier.get(0).getSet().getSigmaCountCompliment(qualifier.get(0).getSet().getDoubleStreamOfSet());
            double product = sigmaCount / sigmaCountCompliment;

            return Math.abs(1 - product);
    	} else {
    		List<Double> sigmas = new ArrayList<>();
    		
    	    for (int i = 0; i < qualifier.size(); i++) {
    	    	double sigmaCount = qualifier.get(i).getSet().getSigmaCount(qualifier.get(i).getSet().getDoubleStreamOfSet());
                double sigmaCountCompliment = qualifier.get(i).getSet().getSigmaCountCompliment(qualifier.get(i).getSet().getDoubleStreamOfSet());
                double product = sigmaCount / sigmaCountCompliment;
                
                sigmas.add(product);
    	    }
    	    
    	    double sum = sigmas.stream().reduce(1.00, (acc, value) -> acc * value);
    	    return 1 - Math.pow(sum, 1.0 / (double) qualifier.size());
    	}
    }
}
