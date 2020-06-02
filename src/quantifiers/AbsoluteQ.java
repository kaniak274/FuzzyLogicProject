package quantifiers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import fuzzy_set.FuzzySet;
import terms.Term;
import terms.TermData;

public class AbsoluteQ {
    public static Term exactMatching(List<TermData> sets, Matcher matcher) {
        long count = countMatchingElements(sets, matcher);
        ArrayList<Double> scope = new ArrayList<>();
        scope.add((double) count);

        return new Term(getString(count), scope, "");
    }
	
    private static long countMatchingElements(List<TermData> sets, Matcher matcher) {
        FuzzySet set = sets.get(0).getSet();
    	
    	return IntStream.range(0, set.getFuzzySet().size())
            .filter(i -> matcher.matcher(set
                .getFuzzySet()
                .get(i)
                .union(sets, i)
                .getMembership()))
            .count();
    }
    
    private static String getString(long count) {
        String[] terms = {"Oko³o ", "Ponad ", "Wiêcej ni¿ "};
        Random generator = new Random();
        int num = generator.nextInt(3);
    	
        if (count <= 1) {
            return terms[0] + count;
        }
        
    	return terms[num] + (count - 1);
    }
}
