package memberships;

import terms.Term;

public class Triangle extends Membership {
    public Triangle(Term term) {
        super(term);
    }

    public double get_membership(double x) {
        // TODO
        throw new UnsupportedOperationException();
    }
	
    public double getA() {
        return this.getTerm().getScope().get(0);
    }
    
    public double getB() {
        return this.getTerm().getScope().get(1);
    }
    
    public double getC() {
        return this.getTerm().getScope().get(2);
    }
}
