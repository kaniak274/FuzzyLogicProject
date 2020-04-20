package memberships;

import terms.Term;

public class Trapezoid extends Membership {
    public Trapezoid(Term term) {
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
            return 1;
        }
    	
        if (x <= this.getD()) {
            return this.getValueBetweenCAndD(x);
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
    
    public double getD() {
        return this.getTerm().getScope().get(3);
    }
    
    private double getValueBetweenAAndB(double x) {
        return (x - this.getA()) / (this.getB() - this.getA());
    }
    
    private double getValueBetweenCAndD(double x) {
        return (this.getD() - x) / (this.getD() - this.getC());
    }
}
