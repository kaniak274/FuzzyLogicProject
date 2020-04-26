package terms;

import java.util.ArrayList;

public class Variable {
    private String label;
    private ArrayList<Term> terms;
    
    public Variable (String label, ArrayList<Term> terms) {
        this.label = label;
        this.terms = terms;
    }
    
    public String getLabel() {
        return label;
    }
    
    public ArrayList<Term> getTerms() {
        return terms;
    }
    
    public void setLabel(String label) {
        this.label = label;
    }
    
    public void setTerms(ArrayList<Term> terms) {
        this.terms = terms;
    }
}
