package degrees;

import java.util.List;

import terms.TermData;

// T11
public class QualifierLength {
    public static double calculate(List<TermData> qualifier) {
        return 2 * Math.pow(0.5, qualifier.size());
    }
}
