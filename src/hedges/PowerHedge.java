package hedges;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import fuzzy_set.FuzzyElement;
import fuzzy_set.FuzzySet;

public class PowerHedge {
    public static FuzzySet powerSet(double exponent, FuzzySet set) {
        List<FuzzyElement> elements = set.getStreamOfSet()
            .map(element -> new FuzzyElement().saveElement(element.getKey(), Math.pow(element.getMembership(), exponent)))
            .collect(Collectors.toList());
        
        return new FuzzySet(new ArrayList<FuzzyElement>(elements));
    }
    
    public static Stream<FuzzyElement> powerStream(double exponent, FuzzySet set) {
        return set.getStreamOfSet()
            .map(element -> new FuzzyElement().saveElement(element.getKey(), Math.pow(element.getMembership(), exponent)));
    }
}
