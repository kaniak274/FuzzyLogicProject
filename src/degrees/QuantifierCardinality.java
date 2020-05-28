package degrees;

public class QuantifierCardinality {
    public static double calculate(double quantifier, boolean isAbsolute, double setSize) {
        if (isAbsolute) {
        	return 1 - (quantifier / setSize);
        } else {
        	return 1 - (quantifier / 1);
        }
    }
}
