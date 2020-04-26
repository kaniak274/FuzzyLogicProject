package memberships;

import java.util.ArrayList;

import fuzzy_set.FuzzyElement;
import fuzzy_set.FuzzySet;
import terms.Term;

public abstract class Membership {
	private Term term;
	
    public Membership(Term term) {
        this.term = term;
    }
	
    public Term getTerm() {
        return term;
    }
	
    public double get_membership(double x) {
        throw new UnsupportedOperationException();
    }
    
    public FuzzySet createSetFromScope() {
        ArrayList<Double> scope = term.getScope();
        ArrayList<FuzzyElement> elements = new ArrayList<>();

        for (Double element : scope) {
        	elements.add(new FuzzyElement().saveMembership(element, this));
        }
        
        return new FuzzySet(elements);
    }
    
    public FuzzySet createSetFromScope(ArrayList<Double> scope) {
        ArrayList<FuzzyElement> elements = new ArrayList<>();

        for (Double element : scope) {
        	elements.add(new FuzzyElement().saveMembership(element, this));
        }
        
        return new FuzzySet(elements);
    }
}
