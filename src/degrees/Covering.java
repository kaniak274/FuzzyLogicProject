package degrees;

import java.util.List;
import java.util.stream.IntStream;

import fuzzy_set.FuzzySet;
import quantifiers.Matcher;
import terms.TermData;

// T3
public class Covering {
    public static double calculate(List<TermData> sets, TermData qualifier, Matcher qualifierMatcher, Matcher sumarizerMatcher) {
        FuzzySet qualifierSet = qualifier.getSet();
        
        int sumarizerResult = IntStream.range(0, sets.get(0).getSet().getFuzzySet().size())
            .reduce(0, (acc, value) -> acc + isOneOrZero(sets, sumarizerMatcher, value));
        
        return (double) sumarizerResult / (double) qualifierValue(qualifierSet, qualifierMatcher);
    }
    
    public static double calculate(List<TermData> sets, Matcher sumarizerMatcher) {
        FuzzySet set = sets.get(0).getSet();
            
        int sumarizerResult = IntStream.range(0, set.getFuzzySet().size())
            .reduce(0, (acc, value) -> acc + isOneOrZero(sets, sumarizerMatcher, value));
        
        return (double) sumarizerResult / (double) set.getFuzzySet().size();
    }
    
    private static int isOneOrZero(List<TermData> sets, Matcher matcher, int i) {
        return matcher.matcher(
            sets.get(0).getSet()
            .getFuzzySet()
            .get(i)
            .union(sets, i)
            .getMembership()) ? 1 : 0;
    }
    
    private static int qualifierValue(FuzzySet set, Matcher matcher) {
        return set
            .getDoubleStreamOfSet()
            .mapToInt(membership -> matcher.matcher(membership) ? 1 : 0)
            .reduce(0, (acc, value) -> acc + value);
    }
}
