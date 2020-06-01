package attributes;

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
import terms.TermData;

public class Pressure extends Attribute {
    public Pressure () {}
    
    public Pressure(List<Entry<Date, Double>> data) {
        this.data = new ArrayList<>(data);
    }
	
    public Entry<Term, Membership> high() {
        ArrayList<Double> scope = new ArrayList<>();

        scope.add(1013.00);
        scope.add(1020.00);
        scope.add(1086.00);
        scope.add(1086.00);

        Term term = new Term("z wysokim ci�nieniem", scope, "z wysokim ci�nieniem", "z wysokim ci�nieniem");
        return new AbstractMap.SimpleEntry<Term, Membership>(term, new Trapezoid(term));
    }
    
    public Entry<Term, Membership> medium() {
        ArrayList<Double> scope = new ArrayList<>();

        scope.add(1000.00);
        scope.add(1013.00);
        scope.add(1020.00);

        Term term = new Term("z �rednim ci�nieniem", scope, "z �rednim ci�nieniem", "z �rednim ci�nieniem");
        return new AbstractMap.SimpleEntry<Term, Membership>(term, new Triangle(term));
    }
    
    public Entry<Term, Membership> low() {
        ArrayList<Double> scope = new ArrayList<>();

    	scope.add(870.00);
        scope.add(870.00);
        scope.add(1000.00);
        scope.add(1013.00);
        
        Term term = new Term("z niskim ci�nieniem", scope, "z niskim ci�nieniem", "z niskim ci�nieniem");
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
    	
        universe.add(870.00);
        universe.add(1086.00);
    	
        return new LinguisticVariable("ci�nienie", new ArrayList<>(listTermsFull()), universe);
    }
    
    public ArrayList<Double> getUniverse() {
        ArrayList<Double> universe = new ArrayList<>();
    	
        universe.add(870.00);
        universe.add(1086.00);
        
        return universe;
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
    
    public TermData lowSetWithTerm() {
        return new TermData(new AbstractMap.SimpleEntry<Term, FuzzySet>(low().getKey(), lowSet()), this.low().getValue(), getUniverse());
    }
    
    public TermData highSetWithTerm() {
        return new TermData(new AbstractMap.SimpleEntry<Term, FuzzySet>(high().getKey(), highSet()), this.high().getValue(), getUniverse());
    }
    
    public TermData mediumSetWithTerm() {
        return new TermData(new AbstractMap.SimpleEntry<Term, FuzzySet>(medium().getKey(), mediumSet()), this.medium().getValue(), getUniverse());
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
