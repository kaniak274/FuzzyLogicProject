package main;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import fuzzy_set.FuzzySet;
import memberships.Membership;
import memberships.Triangle;
import terms.LinguisticVariable;
import terms.Term;

public class WindDirection extends Attribute {
    public WindDirection () {}
    
    public WindDirection(List<Entry<Date, Double>> data) {
        this.data = new ArrayList<>(data);
    }
	
    public Entry<Term, Membership> North() {
        ArrayList<Double> scope = new ArrayList<>();

        scope.add(0.00); // to jest ehhhhhh
        scope.add(0.00);
        scope.add(90.00);

        Term term = new Term("p�nocny", scope, "p�nocny");
        return new AbstractMap.SimpleEntry<Term, Membership>(term, new Triangle(term));
    }
   
    public Entry<Term, Membership> North2() {
        ArrayList<Double> scope = new ArrayList<>();

        scope.add(270.00); 
        scope.add(360.00);
        scope.add(360.00);

        Term term = new Term("p�nocny", scope, "p�nocny");
        return new AbstractMap.SimpleEntry<Term, Membership>(term, new Triangle(term));
    }
    
    public Entry<Term, Membership> East() {
        ArrayList<Double> scope = new ArrayList<>();

        scope.add(0.00);
        scope.add(90.00);
        scope.add(180.00);

        Term term = new Term("wschodni", scope, "wschodni");
        return new AbstractMap.SimpleEntry<Term, Membership>(term, new Triangle(term));
    }
    public Entry<Term, Membership> South() {
        ArrayList<Double> scope = new ArrayList<>();

        scope.add(90.00);
        scope.add(180.00);
        scope.add(270.00);

        Term term = new Term("po�udniowy", scope, "po�udniowy");
        return new AbstractMap.SimpleEntry<Term, Membership>(term, new Triangle(term));
    }
    public Entry<Term, Membership> West() {
        ArrayList<Double> scope = new ArrayList<>();

        scope.add(180.00);
        scope.add(270.00);
        scope.add(359.00);

        Term term = new Term("zachodni", scope, "zachodni");
        return new AbstractMap.SimpleEntry<Term, Membership>(term, new Triangle(term));
    }
    
    public ArrayList<Term> listOfTerms() {
        ArrayList<Term> terms = new ArrayList<>();
    	
        Entry<Term, Membership> North = North();
        Entry<Term, Membership> North2 = North2();
        Entry<Term, Membership> East = East();
        Entry<Term, Membership> South = South();
        Entry<Term, Membership> West = West();

        terms.add(North.getKey());
        terms.add(North2.getKey());
        terms.add(East.getKey());
        terms.add(South.getKey());
        terms.add(West.getKey());
    	
        return terms;
    }
    
    public ArrayList<Entry<Term, Membership>> listTermsFull() {
        ArrayList<Entry<Term, Membership>> terms = new ArrayList<>();
    	
        terms.add(North());
        terms.add(North2());
        terms.add(East());
        terms.add(South());
        terms.add(West());
    	
        return terms;
    }
    
    public LinguisticVariable createVariable() {
        ArrayList<Double> universe = new ArrayList<>();
    	
        universe.add(0.00);
        universe.add(360.00);
    	
        return new LinguisticVariable("kierunek wiatru", new ArrayList<>(listTermsFull()), universe);
    }
    
    public FuzzySet NorthSet() {
        return createVariable().getSetForTerm(data, North().getValue());
    }
    
    public FuzzySet North2Set() {
        return createVariable().getSetForTerm(data, North2().getValue());
    }
    
    public FuzzySet EastSet() {
        return createVariable().getSetForTerm(data, East().getValue());
    }
    public FuzzySet SouthSet() {
        return createVariable().getSetForTerm(data, South().getValue());
    }
    public FuzzySet WestSet() {
        return createVariable().getSetForTerm(data, West().getValue());
    }
    
    public Entry<Term, FuzzySet> NorthSetWithTerm() {
        return new AbstractMap.SimpleEntry<Term, FuzzySet>(North().getKey(), NorthSet());
    }
    
    public Entry<Term, FuzzySet> North2SetWithTerm() {
        return new AbstractMap.SimpleEntry<Term, FuzzySet>(North2().getKey(), North2Set());
    }
    
    public Entry<Term, FuzzySet> EastSetWithTerm() {
        return new AbstractMap.SimpleEntry<Term, FuzzySet>(East().getKey(), EastSet());
    }
    
    public Entry<Term, FuzzySet> SouthSetWithTerm() {
        return new AbstractMap.SimpleEntry<Term, FuzzySet>(South().getKey(), SouthSet());
    }
    
    public Entry<Term, FuzzySet> WestSetWithTerm() {
        return new AbstractMap.SimpleEntry<Term, FuzzySet>(West().getKey(), WestSet());
    }
 
    public boolean wasNorth(double membership) {
        return membership >= 0.6;
    }
    
    public boolean wasNorth2(double membership) {
        return membership >= 0.6;
    }
 
    public boolean wasEast(double membership) {
        return membership >= 0.6;
    }
    
    public boolean wasSouth(double membership) {
        return membership >= 0.6;
    }

    public boolean wasWest(double membership) {
        return membership >= 0.6;
    }
}
