package quantifiers;

import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

import fuzzy_set.FuzzySet;

public class Truth {
    public static double degreeOfTruthRelative(FuzzySet set, Matcher matcher) {
        DoubleStream stream = getMatchingUnits(set, matcher);
        return getSigmaCount(stream) / set.getStreamOfSet().count();
    }

    public static double degreeOfTruthRelative(FuzzySet set, FuzzySet set2, Matcher matcher) {
        DoubleStream stream = getMatchingUnits(set, set2, matcher);
        return getSigmaCount(stream) / getSigmaCount(set.getStreamOfSet().mapToDouble(el -> el.getMembership()));
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
    
    private static DoubleStream getMatchingUnits(FuzzySet set, FuzzySet set2, Matcher matcher) {
        return IntStream.range(0, set2.getFuzzySet().size())
            .mapToDouble(i -> set2.getFuzzySet().get(i).intersect(set.getFuzzySet().get(i)).getMembership())
            .filter(membership -> matcher.matcher(membership));
    }

    private static double getSigmaCount(DoubleStream stream) {
    	return new FuzzySet().getSigmaCount(stream);
    }
}
