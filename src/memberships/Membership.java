package memberships;

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
}
