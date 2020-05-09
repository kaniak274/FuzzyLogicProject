package main;

import java.util.Map.Entry;

import fuzzy_set.FuzzySet;
import memberships.Membership;
import terms.Term;

public class TermData {
    private Term term;
    private FuzzySet set;
    private Membership membership;

    public TermData(Entry<Term, FuzzySet> entry, Membership membership) {
        this.term = entry.getKey();
        this.set = entry.getValue();
        this.membership = membership;
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
}
