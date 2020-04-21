package memberships;

import terms.Term;

public class Triangle extends Membership {
    public Triangle(Term term) {
        super(term);
    }

    public double get_membership(double x) {
    	if (x <= this.getA()) {
            return 0;
        }
    	
    	if (x <= this.getB()) {
    		return this.getValueBetweenAAndB(x);
    	}
    	
    	if (x <= this.getC()) {
    		return this.getValueBetweenBAndC(x);
    	}
    	
    	return 0;
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
    
    private double getValueBetweenAAndB(double x) {
    	return (x - this.getA()) / (this.getB() - this.getA());
    }
    
    private double getValueBetweenBAndC(double x) {
    	return (this.getC() - x) / (this.getC() - this.getB());
    }
}
