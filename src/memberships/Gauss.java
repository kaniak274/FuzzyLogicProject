package memberships;

import terms.Term;

public class Gauss extends Membership {
    public Gauss(Term term) {
        super(term);
    }

    public double get_membership(double x) {
        return Math.exp(
            -
            (
                (Math.pow((x - getCenter()), 2))
                /
                (Math.pow(2 * getWidth(), 2))
            )
        );
    }
    
    public double getWidth() {
        return this.getTerm().getScope().get(0);
    }
    
    public double getCenter() {
        return this.getTerm().getScope().get(1);
    }
}
