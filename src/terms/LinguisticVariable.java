package terms;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import fuzzy_set.FuzzyElement;
import fuzzy_set.FuzzySet;
import memberships.Membership;

public class LinguisticVariable {
    private String label;
    private ArrayList<Entry<Term, Membership>> terms;
    
    // Universe array. First element should contain start of universe and the second should be end of universe.
    private ArrayList<Double> universe;
   
    public LinguisticVariable(String label, ArrayList<Entry<Term, Membership>> terms) {
        this.label = label;
        this.terms = terms;
    }
    
    public LinguisticVariable(String label, ArrayList<Entry<Term, Membership>> terms, ArrayList<Double> universe) {
        this.label = label;
        this.terms = terms;
        this.universe = universe;
    }
    
    public String getLabel() {
        return label;
    }
    
    public ArrayList<Entry<Term, Membership>> getTerms() {
        return terms;
    }
    
    public void setLabel(String label) {
        this.label = label;
    }
    
    public void setTerms(ArrayList<Entry<Term, Membership>> terms) {
        this.terms = terms;
    }
    
    public void setUniverse(ArrayList<Double> universe) {
        this.universe = universe;
    }
    
    public ArrayList<Double> getUniverse() {
        return universe;
    }
    
    public Double getUniverseStart() {
        return universe.get(0);
    }
    
    public Double getUniverseEnd() {
        return universe.get(1);
    }
    
    public List<Double> generateScope(boolean isInt) {
        if (isInt) {
            return IntStream
                .rangeClosed(universe.get(0).intValue(), universe.get(1).intValue())
                .asDoubleStream()
                .boxed()
                .collect(Collectors.toList());
        }
        
        ArrayList<Double> scope = new ArrayList<>();
        
        for (double i = getUniverseStart(); i <= getUniverseEnd(); i += 0.01) {
            scope.add(i);
        }
        
        return scope;
    }
    
    public FuzzySet getSetForTerm(ArrayList<Entry<Date, Double>> data, Membership membership) {
        return new FuzzySet(data
            .stream()
            .map(element -> new FuzzyElement().saveMembership(element.getValue(), membership).setDate(element.getKey()))
            .collect(Collectors.toList()));
    }
}
