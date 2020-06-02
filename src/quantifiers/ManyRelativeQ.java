package quantifiers;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map.Entry;

import memberships.Trapezoid;
import memberships.Triangle;
import terms.Term;

public class ManyRelativeQ {    
    public static Term quantify(double value1, double value2) {
        return getTerm(value2 / value1);
    }
    
    private static Entry<Double, Term> same(double average) {
        ArrayList<Double> scope = new ArrayList<>();
        scope.add(0.7);
        scope.add(1.0);
        scope.add(1.3);
    	
        Term term = new Term("Podobna liczba", scope, "", average);
        Triangle membership = new Triangle(term);
        
        return new AbstractMap.SimpleEntry<Double, Term>(membership.get_membership(average), term);
    }
    
    private static Entry<Double, Term> more(double average) {
        ArrayList<Double> scope = new ArrayList<>();
        scope.add(1.25);
        scope.add(1.5);
        scope.add(1.75);
    	
        Term term = new Term("Wiele", scope, "", average);
        Triangle membership = new Triangle(term);
        
        return new AbstractMap.SimpleEntry<Double, Term>(membership.get_membership(average), term);
    }
    
    private static Entry<Double, Term> muchMore(double average) {
        ArrayList<Double> scope = new ArrayList<>();
        scope.add(1.6);
        scope.add(1.8);
        scope.add(50.0);
        scope.add(50.0);
    	
        Term term = new Term("Bardzo wiele", scope, "", average);
        Trapezoid membership = new Trapezoid(term);
        
        return new AbstractMap.SimpleEntry<Double, Term>(membership.get_membership(average), term);
    }
    
    private static Entry<Double, Term> little(double average) {
        ArrayList<Double> scope = new ArrayList<>();
        scope.add(0.5);
        scope.add(0.65);
        scope.add(0.8);
    	
        Term term = new Term("Ma³o", scope, "", average);
        Triangle membership = new Triangle(term);
        
        return new AbstractMap.SimpleEntry<Double, Term>(membership.get_membership(average), term);
    }
    
    private static Entry<Double, Term> veryLittle(double average) {
        ArrayList<Double> scope = new ArrayList<>();
        scope.add(0.0);
        scope.add(0.0);
        scope.add(0.4);
        scope.add(0.5);
    	
        Term term = new Term("Bardzo ma³o", scope, "", average);
        Trapezoid membership = new Trapezoid(term);
        
        return new AbstractMap.SimpleEntry<Double, Term>(membership.get_membership(average), term);
    }
    
    private static Entry<Double, Term> getBestMatch(double average) {
        ArrayList<Entry<Double, Term>> memberships = new ArrayList<>(); 
        memberships.add(same(average));
        memberships.add(more(average));
        memberships.add(muchMore(average));
        memberships.add(little(average));
        memberships.add(veryLittle(average));
        
        return memberships.stream().max(Comparator::compare).get();
    }
    
    private static Term getTerm(double average) {
        return getBestMatch(average).getValue();
    }
    
    public static Double matchTruth(double average) {
        return getBestMatch(average).getKey();
    }
}
