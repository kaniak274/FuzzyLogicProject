package fuzzy_set;

import java.util.ArrayList;
import java.util.stream.DoubleStream;


public class FuzzySet {
    private ArrayList<FuzzyElement> fuzzySet = new ArrayList<>();
    
    public FuzzySet(ArrayList<FuzzyElement> fuzzySet) {
    	this.fuzzySet = fuzzySet;
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
    
    public double getCLM(DoubleStream stream) {
        // TODO
        return 1.00;
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
    
    public boolean isConvex() {
        // TODO
        return true;
    }
    
    public double getCentroid() {
        // TODO
        return 1.00;
    }
}
