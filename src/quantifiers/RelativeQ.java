package quantifiers;

import java.util.stream.IntStream;

import fuzzy_set.FuzzySet;

public class RelativeQ {
	// These methods assume that both sets are equal in size.
    public static String quantifyAnd(FuzzySet set, FuzzySet set2, Matcher matcher) {
        double average = IntStream.range(0, set.getFuzzySet().size())
            .filter(i -> matcher.matcher(set
                .getFuzzySet()
                .get(i)
                .union(set2.getFuzzySet().get(i))
                .getMembership()))
            .count() / set.getFuzzySet().size();
        
        return averageToLabel(average);
    }
	
    public static String quantifyOr(FuzzySet set, FuzzySet set2, Matcher matcher) {
        double average = IntStream.range(0, set.getFuzzySet().size())
            .filter(i -> matcher.matcher(set
                .getFuzzySet()
                .get(i)
                .intersect(set2.getFuzzySet().get(i))
                .getMembership()))
            .count() / set.getFuzzySet().size();
            
        return averageToLabel(average);
    }
    
    public static String quantifyNot(FuzzySet set, Matcher matcher) {
        double average = IntStream.range(0, set.getFuzzySet().size())
            .filter(i -> matcher.matcher(set
                .getFuzzySet()
                .get(i)
                .compliment()))
            .count() / set.getFuzzySet().size();
                
        return averageToLabel(average);
    }
    
    private static String averageToLabel(double average) {
        if (average <= 0.2) {
            return "Niska liczba";
        }
        
        if (average <= 0.3) {
            return "Oko³o 1/4";
        }
        
        if (average <= 0.6) {
            return "Oko³o po³owa";
        }
        
        if (average <= 0.8) {
            return "Blisko 75%";
        }
        
        if (average <= 0.9) {
            return "Wiêkszoœæ";
        }
        
        return "Zdecydowana wiêkszoœæ";
    }
}
