package quantifiers;

import fuzzy_set.FuzzySet;

public class AbsoluteQ {
    public String exactMatching(FuzzySet set, Matcher matcher) {
        return Long.toString(countMatchingElements(set, matcher));
    }
	
    private long countMatchingElements(FuzzySet set, Matcher matcher) {
        return set
            .getStreamOfSet()
            .filter(element -> matcher.matcher(element.getMembership()))
            .count();
    }
}
