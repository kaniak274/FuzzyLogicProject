package terms;

import java.util.ArrayList;

public class Term {
    private String label;
    private ArrayList<Double> scope;
    private String pluralLabel;
    
    public Term(String label, ArrayList<Double> scope, String pluralLabel) {
        this.label = label;
        this.scope = scope;
        this.pluralLabel = pluralLabel;
    }

    public void setLabel(String label) {
        this.label = label;
    }
    
    public String getLabel() {
        return label;
    }
    
    public String getPluralLabe() {
    	return pluralLabel;
    }
    
    public ArrayList<Double> getScope() {
        return scope;
    }
}
