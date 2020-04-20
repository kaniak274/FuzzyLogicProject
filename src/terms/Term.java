package terms;

import java.util.ArrayList;

public class Term {
    private String label;
    private ArrayList<Double> scope;
    
    public Term(String label, ArrayList<Double> scope) {
        this.label = label;
        this.scope = scope;
    }

    public void setLabel(String label) {
        this.label = label;
    }
    
    public String getLabel() {
        return label;
    }
    
    public ArrayList<Double> getScope() {
        return scope;
    }
}
