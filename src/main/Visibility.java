package main;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import fuzzy_set.FuzzySet;
import memberships.Membership;
import memberships.Trapezoid;
import memberships.Triangle;
import terms.LinguisticVariable;
import terms.Term;

public class Visibility extends Attribute {
    public Visibility () {}
    
    public Visibility(List<Entry<Date, Double>> data) {
        this.data = new ArrayList<>(data);
    }
	
    public Entry<Term, Membership> high() {
        ArrayList<Double> scope = new ArrayList<>();

        scope.add(3000.00);
        scope.add(4000.00);
        scope.add(5000.00);
        scope.add(5000.00);

        Term term = new Term("z wysok¹ widocznoœci¹", scope, "z wysok¹ widocznoœci¹");
        return new AbstractMap.SimpleEntry<Term, Membership>(term, new Trapezoid(term));
    }
    
    public Entry<Term, Membership> medium() {
        ArrayList<Double> scope = new ArrayList<>();

        scope.add(1000.00);
        scope.add(2500.00);
        scope.add(3500.00);

        Term term = new Term("z œredni¹ widocznoœci¹", scope, "z œredni¹ widocznoœci¹");
        return new AbstractMap.SimpleEntry<Term, Membership>(term, new Triangle(term));
    }
    
    public Entry<Term, Membership> low() {
        ArrayList<Double> scope = new ArrayList<>();

    	scope.add(200.00);
        scope.add(200.00);
        scope.add(700.00);
        scope.add(1500.00);
        
        Term term = new Term("z nisk¹ widocznoœci¹", scope, "z nisk¹ widocznoœci¹");
        return new AbstractMap.SimpleEntry<Term, Membership>(term, new Trapezoid(term));
    }
    
    public ArrayList<Term> listOfTerms() {
        ArrayList<Term> terms = new ArrayList<>();
    	
        Entry<Term, Membership> low = low();
        Entry<Term, Membership> high = high();
        Entry<Term, Membership> medium = medium();

        terms.add(low.getKey());
        terms.add(high.getKey());
        terms.add(medium.getKey());
    	
        return terms;
    }
    
    public ArrayList<Entry<Term, Membership>> listTermsFull() {
        ArrayList<Entry<Term, Membership>> terms = new ArrayList<>();
    	
        terms.add(low());
        terms.add(high());
        terms.add(medium());
    	
        return terms;
    }
    
    public LinguisticVariable createVariable() {
        ArrayList<Double> universe = new ArrayList<>();
    	
        universe.add(-20.00);
        universe.add(50.00);
    	
        return new LinguisticVariable("ciœnienie", new ArrayList<>(listTermsFull()), universe);
    }
    
    public FuzzySet lowSet() {
        return createVariable().getSetForTerm(data, low().getValue());
    }
    
    public FuzzySet highSet() {
        return createVariable().getSetForTerm(data, high().getValue());
    }
    
    public FuzzySet mediumSet() {
        return createVariable().getSetForTerm(data, medium().getValue());
    }
    
    public Entry<Term, FuzzySet> lowSetWithTerm() {
        return new AbstractMap.SimpleEntry<Term, FuzzySet>(low().getKey(), lowSet());
    }
    
    public Entry<Term, FuzzySet> highSetWithTerm() {
        return new AbstractMap.SimpleEntry<Term, FuzzySet>(high().getKey(), highSet());
    }
    
    public Entry<Term, FuzzySet> mediumSetWithTerm() {
        return new AbstractMap.SimpleEntry<Term, FuzzySet>(medium().getKey(), mediumSet());
    }
    
    public boolean wasHigh(double membership) {
        return membership >= 0.6;
    }
    
    public boolean wasLow(double membership) {
        return membership >= 0.6;
    }
    
    public boolean wasMedium(double membership) {
        return membership >= 0.8;
    }
}
