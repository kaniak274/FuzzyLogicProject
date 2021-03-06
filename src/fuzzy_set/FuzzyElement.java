package fuzzy_set;

import java.util.AbstractMap;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import memberships.Membership;
import terms.TermData;

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
    
    public FuzzyElement intersect(FuzzyElement el, FuzzyElement el2) {
        double val = el.getMembership();
        double val2 = el2.getMembership();

        if (val >= val2) {
            return el;
        } else {
            return el2;
        }
    }
    
    public FuzzyElement intersect(List<TermData> sets, int i) {
        FuzzyElement result = this;
        
        for (TermData set : sets) {
            result = this.intersect(result, set.getSet().getFuzzySet().get(i));
        }
        
        return result;
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
    
    public FuzzyElement union(FuzzyElement el, FuzzyElement el2) {
        double val = el.getMembership();
        double val2 = el2.getMembership();

        if (val <= val2) {
            return el;
        } else {
            return el2;
        }
    }
    
    public FuzzyElement union(List<TermData> sets, int i) {
        FuzzyElement result = this;
        
        for (TermData set : sets) {
            result = this.union(result, set.getSet().getFuzzySet().get(i));
        }
        
        return result;
    }
    
    public String toString() {
        return this.value.toString();
    }
}
