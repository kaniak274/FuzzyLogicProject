package quantifiers;

import java.util.Random;

import fuzzy_set.FuzzySet;

public class AbsoluteQ {
    public static String exactMatching(FuzzySet set, Matcher matcher) {
        return getString(countMatchingElements(set, matcher));
    }
	
    private static long countMatchingElements(FuzzySet set, Matcher matcher) {
        return set
            .getStreamOfSet()
            .filter(element -> matcher.matcher(element.getMembership()))
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
