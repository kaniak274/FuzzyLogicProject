package fuzzy_set;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

import memberships.Membership;


public class FuzzySet {
    private ArrayList<FuzzyElement> fuzzySet = new ArrayList<>();
    
    public FuzzySet(ArrayList<FuzzyElement> fuzzySet) {
        this.fuzzySet = fuzzySet;
    }
    
    public FuzzySet() {}
    
    public FuzzySet(List<FuzzyElement> fuzzySet) {
        this.fuzzySet = new ArrayList<>(fuzzySet);
    }
    
    public Stream<FuzzyElement> getStreamOfSet() {
        return fuzzySet.stream();
    }
    
    public DoubleStream getDoubleStreamOfSet() {
        return fuzzySet.stream().mapToDouble(element -> element.getMembership());
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
    
    public double getSigmaCountCompliment(DoubleStream stream) {
        return stream.reduce(0.0, (acc, val) -> acc + (1 - val));
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
    
    public boolean isNormal() {
        if (this.getSupport().count() == 0) {
            return false;
        }

        return this.getHeight(this.getSupport()) == 1;
    }
    
    public Stream<FuzzyElement> normalize(Stream<FuzzyElement> stream) {
        Stream<FuzzyElement> allElements = this.getStreamOfSet();

        double height = this.getHeight(allElements.mapToDouble(element -> element.getMembership()));
        return stream.map(element -> element.saveElement(element.getKey(), element.getMembership() / height));
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

        for (double i = min + 0.1; i <= max; i += 0.1) {
            i = new BigDecimal(i).setScale(1, RoundingMode.HALF_UP).doubleValue();
            
            double newMembership = membershipObject.get_membership(i);
            
            if (!changedDirection && newMembership - previous < 0) {
                return false;
            }
        	
            if (changedDirection && newMembership - previous > 0) {
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
