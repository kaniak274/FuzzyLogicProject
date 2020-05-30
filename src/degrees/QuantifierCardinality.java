package degrees;

public class QuantifierCardinality {
    public static double calculate(double quantifier) {
        return Math.abs(1 - quantifier);
    }
    
    public static double calculate(double matchingElements, double setSize) {
        return Math.abs(1 - (matchingElements / setSize));
    }
}
