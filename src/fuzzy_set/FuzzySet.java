package fuzzy_set;

import java.util.ArrayList;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

import memberships.Membership;


public class FuzzySet {
    private ArrayList<FuzzyElement> fuzzySet = new ArrayList<>();
    
    public FuzzySet(ArrayList<FuzzyElement> fuzzySet) {
    	this.fuzzySet = fuzzySet;
    }
    
    public Stream<FuzzyElement> getStreamOfSet() {
        return fuzzySet.stream();
    }
    
    public ArrayList<FuzzyElement> getFuzzySet() {
        return fuzzySet;
    }
    
    public DoubleStream getSupport() {
        return fuzzySet.stream()
            .mapToDouble(element -> element.getMembership())
            .filter(element -> element > 0);
    }

    public DoubleStream getCore() {
        return fuzzySet.stream()
            .mapToDouble(element -> element.getMembership())
            .filter(element -> element == 1);
    }
    
    public DoubleStream getAlphaCut(Double alpha) {
        return fuzzySet.stream()
            .mapToDouble(element -> element.getMembership())
            .filter(element -> element >= alpha);
    }
    
    public double getSigmaCount(DoubleStream stream) {
        return stream.sum();
    }
    
    public double getDeegreOfFuzziness() {
        // TODO
        return 1.00;
    }
    
    public double getHeight(DoubleStream stream) {
	    return stream
	        .max()
            .getAsDouble();
    }
    
    public boolean isNormal(DoubleStream stream) {
        return this.getHeight(stream) == 1;
    }
    
    public DoubleStream normalize(DoubleStream stream) {
        double height = this.getHeight(stream);
        return stream.map(element -> element / height);
    }
    
    public long getSupportSize(DoubleStream stream) {
        return stream.count();
    }
    
    public boolean isEmpty(DoubleStream stream) {
        return this.getSupportSize(stream) == 0;
    }
    
    public boolean isConvex(double min, double max, Membership membershipObject) {
        boolean changedDirection = false;
        double previous = membershipObject.get_membership(min);

        for (double i = min + 0.1; i <= max; i+= 0.1) {
            double newMembership = membershipObject.get_membership(i);
            
            if (!changedDirection && newMembership - previous < 0) {
                return false;
            }
        	
            if (changedDirection && previous - newMembership > 0) {
                return false;
            }
        	
            if (newMembership == 1) {
                changedDirection = true;
            }
        	
            previous = newMembership;
        }

        return true;
    }

    public String toString() {
        return this.fuzzySet.toString();
    }
}
