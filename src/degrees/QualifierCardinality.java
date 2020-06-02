package degrees;

import terms.TermData;

// T10
public class QualifierCardinality {
    public static double calculate(TermData qualifier) {    	
    	double sigmaCount = qualifier.getSet().getSigmaCount(qualifier.getSet().getDoubleStreamOfSet());
    	double sigmaCountCompliment = qualifier.getSet().getSigmaCountCompliment(qualifier.getSet().getDoubleStreamOfSet());
        double product = sigmaCount / sigmaCountCompliment;

        return Math.abs(1 - product);
    }
}
