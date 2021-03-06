package terms;

import java.util.ArrayList;

public class Term {
    private String label;
    private ArrayList<Double> scope;
    private String pluralLabel;
    private Double relativeQ;
    private String doubleFormLabel;
    
    public Term(String label, ArrayList<Double> scope, String pluralLabel) {
        this.label = label;
        this.scope = scope;
        this.pluralLabel = pluralLabel;
    }
    
    public Term(String label, ArrayList<Double> scope, String pluralLabel, double relativeQ) {
        this.label = label;
        this.scope = scope;
        this.pluralLabel = pluralLabel;
        this.relativeQ = relativeQ;
    }
    
    public Term(String label, ArrayList<Double> scope, String pluralLabel, String doubleFormLabel) {
        this.label = label;
        this.scope = scope;
        this.pluralLabel = pluralLabel;
        this.relativeQ = relativeQ;
        this.doubleFormLabel = doubleFormLabel;
    }

    public void setLabel(String label) {
        this.label = label;
    }
    
    public String getLabel() {
        return label;
    }
    
    public String getPluralLabel() {
        return pluralLabel;
    }
    
    public ArrayList<Double> getScope() {
        return scope;
    }
    
    public double getRelativeQ() {
        return relativeQ;
    }
    
    public String DoubleFormLabel() {
        return doubleFormLabel;
    }
}