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

public class Temperature {
    public ArrayList<Entry<Date, Double>> data = new ArrayList<>();
    
    public Temperature (List<Entry<Date, Double>> data) {
        this.data = new ArrayList<>(data);
    }
	
    public Entry<Term, Membership> hot() {
        ArrayList<Double> scope = new ArrayList<>();

        scope.add(20.00);
        scope.add(30.00);
        scope.add(50.00);
        scope.add(50.00);

        Term term = new Term("ciep³y", scope, "ciep³ych");
        return new AbstractMap.SimpleEntry<Term, Membership>(term, new Trapezoid(term));
    }
    
    public Entry<Term, Membership> moderate() {
        ArrayList<Double> scope = new ArrayList<>();

        scope.add(5.00);
        scope.add(15.00);
        scope.add(25.00);

        Term term = new Term("umiarkowany", scope, "umiarkowanych");
        return new AbstractMap.SimpleEntry<Term, Membership>(term, new Triangle(term));
    }
    
    public Entry<Term, Membership> cold() {
        ArrayList<Double> scope = new ArrayList<>();

    	scope.add(-50.00);
        scope.add(-50.00);
        scope.add(0.00);
        scope.add(10.00);
        
        Term term = new Term("zimny", scope, "zimnych");
        return new AbstractMap.SimpleEntry<Term, Membership>(term, new Trapezoid(term));
    }
    
    public ArrayList<Term> listOfTerms() {
        ArrayList<Term> terms = new ArrayList<>();
    	
        Entry<Term, Membership> cold = cold();
        Entry<Term, Membership> hot = hot();
        Entry<Term, Membership> moderate = moderate();

        terms.add(cold.getKey());
        terms.add(hot.getKey());
        terms.add(moderate.getKey());
    	
        return terms;
    }
    
    public ArrayList<Entry<Term, Membership>> listTermsFull() {
        ArrayList<Entry<Term, Membership>> terms = new ArrayList<>();
    	
        terms.add(cold());
        terms.add(hot());
        terms.add(moderate());
    	
        return terms;
    }
    
    public LinguisticVariable createVariable() {
        ArrayList<Double> universe = new ArrayList<>();
    	
        universe.add(-20.00);
        universe.add(50.00);
    	
        return new LinguisticVariable("temperatura", new ArrayList<>(listTermsFull()), universe);
    }
    
    public FuzzySet coldSet() {
        return createVariable().getSetForTerm(data, cold().getValue());
    }
    
    public FuzzySet hotSet() {
        return createVariable().getSetForTerm(data, hot().getValue());
    }
    
    public FuzzySet moderateSet() {
        return createVariable().getSetForTerm(data, moderate().getValue());
    }
    
    public Entry<Term, FuzzySet> coldSetWithTerm() {
        return new AbstractMap.SimpleEntry<Term, FuzzySet>(cold().getKey(), coldSet());
    }
    
    public Entry<Term, FuzzySet> hotSetWithTerm() {
        return new AbstractMap.SimpleEntry<Term, FuzzySet>(hot().getKey(), hotSet());
    }
    
    public Entry<Term, FuzzySet> moderateSetWithTerm() {
        return new AbstractMap.SimpleEntry<Term, FuzzySet>(moderate().getKey(), moderateSet());
    }
    
    public boolean wasHot(double membership) {
        return membership >= 0.4;
    }
    
    public boolean wasCold(double membership) {
        return membership >= 0.4;
    }
    
    public boolean wasModerate(double membership) {
        return membership >= 0.7;
    }
}
