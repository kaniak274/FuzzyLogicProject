package quantifiers;

import java.util.List;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

import fuzzy_set.FuzzySet;
import terms.TermData;

public class Truth {
    public static double degreeOfTruthRelative(List<TermData> sets, Matcher matcher) {
    	FuzzySet set = sets.get(0).getSet();
    	System.out.println(sets);

        DoubleStream stream = getMatchingUnits(set, sets, matcher);
        return getSigmaCount(stream) / getSigmaCount(set.getStreamOfSet().mapToDouble(el -> el.getMembership()));
    }

    public static double degreeOfTruthAbsolute() {
        return 1.0;
    }
    
    private static DoubleStream getMatchingUnits(FuzzySet set, List<TermData> sets, Matcher matcher) {
        return IntStream.range(0, set.getFuzzySet().size())
            .mapToDouble(i -> set.getFuzzySet().get(i).intersect(sets, i).getMembership())
            .filter(membership -> matcher.matcher(membership));
    }

    private static double getSigmaCount(DoubleStream stream) {
    	return new FuzzySet().getSigmaCount(stream);
    }
}
