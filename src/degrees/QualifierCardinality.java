package degrees;

import terms.TermData;

public class QualifierCardinality {
    public static double calculate(TermData qualifier) {
    	double product  = qualifier.getSet().getFuzzySet().size() / Math.abs(qualifier.getUniverse().get(1) - qualifier.getUniverse().get(0));
        return 1 - product;
    }
}
