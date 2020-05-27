package degrees;

import java.util.List;

import terms.TermData;

public class Length {
    public static double calculate(List<TermData> sets) {
        return 2 * Math.pow(0.5, sets.size());
    }
}
