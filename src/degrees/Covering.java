package degrees;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import fuzzy_set.FuzzySet;
import quantifiers.Matcher;
import terms.TermData;

public class Covering {
    public static double calculate(List<TermData> sets, Matcher qualifierMatcher, Matcher sumarizerMatcher) {
        FuzzySet qualifier = sets.get(0).getSet();
    	
        List<TermData> sumarizer = IntStream.range(0, sets.size())
            .filter(i -> i != 0)
            .mapToObj(i -> sets.get(i))
            .collect(Collectors.toList());
        
        int sumarizerResult = IntStream.range(0, sumarizer.get(0).getSet().getFuzzySet().size())
            .reduce(0, (acc, value) -> acc + isOneOrZero(sets, sumarizerMatcher, value));
        
        return sumarizerResult / qualifierValue(qualifier, qualifierMatcher);
    }
    
    public static double calculate(List<TermData> sets, Matcher sumarizerMatcher) {
        FuzzySet set = sets.get(0).getSet();
            
        int sumarizerResult = IntStream.range(0, set.getFuzzySet().size())
            .reduce(0, (acc, value) -> acc + isOneOrZero(sets, sumarizerMatcher, value));
        
        return sumarizerResult / set.getFuzzySet().size();
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
