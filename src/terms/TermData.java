package terms;

import java.util.ArrayList;
import java.util.Map.Entry;

import fuzzy_set.FuzzySet;
import memberships.Membership;

public class TermData {
    private Term term;
    private FuzzySet set;
    private Membership membership;
    private ArrayList<Double> universe;

    public TermData(Entry<Term, FuzzySet> entry, Membership membership) {
        this.term = entry.getKey();
        this.set = entry.getValue();
        this.membership = membership;
    }
    
    public TermData(Entry<Term, FuzzySet> entry, Membership membership, ArrayList<Double> universe) {
        this.term = entry.getKey();
        this.set = entry.getValue();
        this.membership = membership;
        this.universe = universe;
    }

    public Term getTerm() {
        return term;
    }

    public FuzzySet getSet() {
        return set;
    }

    public FuzzySet setSet(FuzzySet set) {
        this.set = set;
        return this.set;
    }
    
    public Membership getMembership() {
        return membership;
    }
    
    public ArrayList<Double> getUniverse() {
        return universe;
    }
}
