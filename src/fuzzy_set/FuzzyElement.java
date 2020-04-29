package fuzzy_set;

import java.util.AbstractMap;
import java.util.Date;
import java.util.Map.Entry;

import memberships.Membership;

public class FuzzyElement {
    private Entry<Double, Double> value;
    private Date date;

    public Date getDate() {
    	return date;
    }
    
    public FuzzyElement setDate(Date date) {
        this.date = date;
        return this;
    }
    
    public double getMembership() {
        return value.getValue();
    }
    
    public double getKey() {
        return value.getKey();
    }
    
    public FuzzyElement saveElement(double key, double membership) {
        this.value = new AbstractMap.SimpleEntry<Double, Double>(key, membership);
        return this;
    }
    
    public FuzzyElement saveMembership(double key, Membership membershipObject) {
    	double membership = membershipObject.get_membership(key);
        this.value = new AbstractMap.SimpleEntry<Double, Double>(key, membership);
        
        return this;
    }
    
    public Entry<Double, Double> calculateMembership(double key, Membership membershipObject) {
        double membership = membershipObject.get_membership(key);
        this.value = new AbstractMap.SimpleEntry<Double, Double>(key, membership);
    	
        return value;
    }

    public double compliment() {
        return 1 - this.getMembership();
    }
    
    public FuzzyElement intersect(FuzzyElement other) {
        double val = this.getMembership();
        double val2 = other.getMembership();
        
        if (val >= val2) {
            return this;
        } else {
            return other;
        }
    }
    
    public FuzzyElement union(FuzzyElement other) {
        double val = this.getMembership();
        double val2 = other.getMembership();

        if (val <= val2) {
            return this;
        } else {
            return other;
        }
    }
    
    public String toString() {
        return this.value.toString();
    }
}
