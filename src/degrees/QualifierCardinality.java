package degrees;

import terms.TermData;

// T10
public class QualifierCardinality {
    public static double calculate(TermData qualifier) {
    	double product  = qualifier.getSet().getFuzzySet().size() / Math.abs(qualifier.getUniverse().get(1) - qualifier.getUniverse().get(0));
        return Math.abs(1 - product);
    }
}
