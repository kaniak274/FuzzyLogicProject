package quantifiers;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.stream.IntStream;

import fuzzy_set.FuzzySet;
import memberships.Trapezoid;
import memberships.Triangle;
import terms.Term;

public class RelativeQ {
    // These methods assume that both sets are equal in size.
    public static String quantifyAnd(FuzzySet set, FuzzySet set2, Matcher matcher) {
        long count = IntStream.range(0, set.getFuzzySet().size())
            .filter(i -> matcher.matcher(set
                .getFuzzySet()
                .get(i)
                .union(set2.getFuzzySet().get(i))
                .getMembership()))
            .count();
        
        double average = (double)count / (double)set.getFuzzySet().size();
        return getLabel(average);
    }
	
    public static String quantifyOr(FuzzySet set, FuzzySet set2, Matcher matcher) {
        long count = IntStream.range(0, set.getFuzzySet().size())
            .filter(i -> matcher.matcher(set
                .getFuzzySet()
                .get(i)
                .intersect(set2.getFuzzySet().get(i))
                .getMembership()))
            .count();
        
        double average = (double)count / (double)set.getFuzzySet().size();
        return getLabel(average);
    }
    
    public static String quantifyNot(FuzzySet set, FuzzySet set2, Matcher matcher) {
        long count = IntStream.range(0, set.getFuzzySet().size())
            .filter(i -> matcher.matcher(set.getFuzzySet().get(i).getMembership(), set2.getFuzzySet().get(i).getMembership()))
            .count();
        
        double average = (double)count / (double)set.getFuzzySet().size();
        return getLabel(average);
    }
    
    public static String quantifySingle(FuzzySet set, Matcher matcher) {
        long count = set
            .getStreamOfSet()
            .filter(element -> matcher.matcher(element.getMembership()))
            .count();
        
        double average = (double)count / (double)set.getFuzzySet().size();
        return getLabel(average);
    }
    
    private static Entry<Double, Term> low(double average) {
        ArrayList<Double> scope = new ArrayList<>();
        scope.add(0.0);
        scope.add(0.0);
        scope.add(0.1);
        scope.add(0.2);
    	
        Term term = new Term("Niska liczba", scope, "");
        Trapezoid membership = new Trapezoid(term);
        
        return new AbstractMap.SimpleEntry<Double, Term>(membership.get_membership(average), term);
    }
    
    private static Entry<Double, Term> some(double average) {
        ArrayList<Double> scope = new ArrayList<>();
        scope.add(0.2);
        scope.add(0.25);
        scope.add(0.3);
    	
        Term term = new Term("Oko³o 1/4", scope, "");
        Triangle membership = new Triangle(term);
        
        return new AbstractMap.SimpleEntry<Double, Term>(membership.get_membership(average), term);
    }
    
    private static Entry<Double, Term> half(double average) {
        ArrayList<Double> scope = new ArrayList<>();
        scope.add(0.3);
        scope.add(0.5);
        scope.add(0.8);
    	
        Term term = new Term("Oko³o po³owa", scope, "");
        Triangle membership = new Triangle(term);
        
        return new AbstractMap.SimpleEntry<Double, Term>(membership.get_membership(average), term);
    }
    
    private static Entry<Double, Term> seventyFive(double average) {
        ArrayList<Double> scope = new ArrayList<>();
        scope.add(0.65);
        scope.add(0.75);
        scope.add(0.85);
    	
        Term term = new Term("Blisko 75%", scope, "");
        Triangle membership = new Triangle(term);
        
        return new AbstractMap.SimpleEntry<Double, Term>(membership.get_membership(average), term);
    }
    
    private static Entry<Double, Term> high(double average) {
        ArrayList<Double> scope = new ArrayList<>();
        scope.add(0.8);
        scope.add(0.9);
        scope.add(1.0);
    	
        Term term = new Term("Wiêkszoœæ", scope, "");
        Triangle membership = new Triangle(term);
        
        return new AbstractMap.SimpleEntry<Double, Term>(membership.get_membership(average), term);
    }
    
    private static Entry<Double, Term> veryHigh(double average) {
        ArrayList<Double> scope = new ArrayList<>();
        scope.add(0.85);
        scope.add(0.9);
        scope.add(1.0);
        scope.add(1.0);
    	
        Term term = new Term("Zdecydowana wiêkszoœæ", scope, "");
        Triangle membership = new Triangle(term);
        
        return new AbstractMap.SimpleEntry<Double, Term>(membership.get_membership(average), term);
    }
    
    private static Entry<Double, Term> getBestMatch(double average) {
        ArrayList<Entry<Double, Term>> memberships = new ArrayList<>(); 
        memberships.add(low(average));
        memberships.add(some(average));
        memberships.add(half(average));
        memberships.add(seventyFive(average));
        memberships.add(high(average));
        memberships.add(veryHigh(average));
        
        return memberships.stream().max(Comparator::compare).get();
    }
    
    private static String getLabel(double average) {
        return getBestMatch(average).getValue().getLabel();
    }
    
    public static Double matchTruth(double average) {
        return getBestMatch(average).getKey();
    }
}
