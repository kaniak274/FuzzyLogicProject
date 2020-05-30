package degrees;

public class QuantifierCardinality {
    public static double calculate(double quantifier) {
        return 1 - quantifier;
    }
    
    public static double calculate(double matchingElements, double setSize) {
        return 1 - (matchingElements / setSize);
    }
}
