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

public class WindPower extends Attribute {
    public WindPower () {}
    
    public WindPower (List<Entry<Date, Double>> data) {
        this.data = new ArrayList<>(data);
    }
	
    public Entry<Term, Membership> fast() {
        ArrayList<Double> scope = new ArrayList<>();

        scope.add(150.00);
        scope.add(25.00);
        scope.add(40.00);
        scope.add(40.00);

        Term term = new Term("porywisty", scope, "porywisty", "z porywistym wiatrem");
        return new AbstractMap.SimpleEntry<Term, Membership>(term, new Trapezoid(term));
    }
    
    public Entry<Term, Membership> moderate() {
        ArrayList<Double> scope = new ArrayList<>();

        scope.add(10.00);
        scope.add(15.00);
        scope.add(20.00);

        Term term = new Term("umiarkowany", scope, "umiarkowanych", "z umiarkowanym wiatrem");
        return new AbstractMap.SimpleEntry<Term, Membership>(term, new Triangle(term));
    }
    
    public Entry<Term, Membership> slow() {
        ArrayList<Double> scope = new ArrayList<>();

    	scope.add(0.00);
        scope.add(0.00);
        scope.add(5.00);
        scope.add(15.00);
        
        Term term = new Term("lekki", scope, "lekki", "z lekkim wiatrem");
        return new AbstractMap.SimpleEntry<Term, Membership>(term, new Trapezoid(term));
    }
    
    public ArrayList<Term> listOfTerms() {
        ArrayList<Term> terms = new ArrayList<>();
    	
        Entry<Term, Membership> slow = slow();
        Entry<Term, Membership> fast = fast();
        Entry<Term, Membership> moderate = moderate();

        terms.add(slow.getKey());
        terms.add(fast.getKey());
        terms.add(moderate.getKey());
    	
        return terms;
    }
    
    public ArrayList<Entry<Term, Membership>> listTermsFull() {
        ArrayList<Entry<Term, Membership>> terms = new ArrayList<>();
    	
        terms.add(slow());
        terms.add(slow());
        terms.add(moderate());
    	
        return terms;
    }
    
    public LinguisticVariable createVariable() {
        ArrayList<Double> universe = new ArrayList<>();
    	
        universe.add(0.00);
        universe.add(150.00);
    	
        return new LinguisticVariable("si�a wiatru", new ArrayList<>(listTermsFull()), universe);
    }
    
    public ArrayList<Double> getUniverse() {
        ArrayList<Double> universe = new ArrayList<>();
    	
        universe.add(0.00);
        universe.add(150.00);
        
        return universe;
    }
    
    public FuzzySet slowSet() {
        return createVariable().getSetForTerm(data, slow().getValue());
    }
    
    public FuzzySet fastSet() {
        return createVariable().getSetForTerm(data, fast().getValue());
    }
    
    public FuzzySet moderateSet() {
        return createVariable().getSetForTerm(data, moderate().getValue());
    }
    
    public TermData slowSetWithTerm() {
        return new TermData(new AbstractMap.SimpleEntry<Term, FuzzySet>(slow().getKey(), slowSet()), this.slow().getValue(), getUniverse());
    }
    
    public TermData fastSetWithTerm() {
        return new TermData(new AbstractMap.SimpleEntry<Term, FuzzySet>(fast().getKey(), fastSet()), this.fast().getValue(), getUniverse());
    }
    
    public TermData moderateSetWithTerm() {
        return new TermData(new AbstractMap.SimpleEntry<Term, FuzzySet>(moderate().getKey(), moderateSet()), this.moderate().getValue(), getUniverse());
    }
    
    public boolean wasFast(double membership) {
        return membership >= 0.5;
    }
    
    public boolean wasSlow(double membership) {
        return membership >= 0.5;
    }
    
    public boolean wasModerate(double membership) {
        return membership >= 0.7;
    }
}
