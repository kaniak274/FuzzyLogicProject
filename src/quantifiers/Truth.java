package quantifiers;

import java.util.stream.DoubleStream;

import fuzzy_set.FuzzySet;

public class Truth {
    public static double degreeOfTruthRelative(FuzzySet set, Matcher matcher) {
        DoubleStream stream = getMatchingUnits(set, matcher);
        return getSigmaCount(stream) / set.getStreamOfSet().count();
    }
    
    public static double degreeOfTruthAbsolute(FuzzySet set, Matcher matcher) {
        DoubleStream stream = getMatchingUnits(set, matcher);
        return getSigmaCount(stream) / 1;
    }
    
    private static DoubleStream getMatchingUnits(FuzzySet set, Matcher matcher) {
        return set
            .getStreamOfSet()
            .filter(element -> matcher.matcher(element.getMembership()))
            .mapToDouble(element -> element.getMembership());
    }
    
    private static double getSigmaCount(DoubleStream stream) {
    	return new FuzzySet().getSigmaCount(stream);
    }
}
