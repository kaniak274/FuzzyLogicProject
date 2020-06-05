package degrees;

import java.util.List;
import java.util.stream.IntStream;

import fuzzy_set.FuzzySet;
import quantifiers.Matcher;
import quantifiers.QualifierMatcher;
import terms.TermData;

// T3
public class Covering {
    public static double calculate(List<TermData> sets, List<TermData> qualifier, QualifierMatcher qualifierMatcher, Matcher sumarizerMatcher) {        
        int sumarizerResult = IntStream.range(0, sets.get(0).getSet().getFuzzySet().size())
            .reduce(0, (acc, value) -> acc + isOneOrZero(sets, sumarizerMatcher, value));
        
        return (double) sumarizerResult / (double) qualifierValue(qualifier, qualifierMatcher);
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
    
    private static int qualifierValue(List<TermData> sets, QualifierMatcher matcher) {        
        return IntStream.range(0, sets.get(0).getSet().getFuzzySet().size())
	        .filter(i -> matcher.matcher(sets.get(0).getSet()
	            .getFuzzySet()
	            .get(i)
	            .union(sets, i)
	            .getMembership()))
	        .map(i -> 1)
	        .reduce(0,  (acc, value) -> acc + value);
    }
}
