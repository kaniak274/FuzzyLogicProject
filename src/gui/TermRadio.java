package gui;

import javax.swing.JRadioButton;

@SuppressWarnings("serial")
public class TermRadio extends JRadioButton {
    private String methodName;
    private double hedge;

    public TermRadio(String text, String methodName) {
        super(text);
		
        this.methodName = methodName;
        this.hedge = 0.0;
    }

    public TermRadio(String text, String methodName, double hedge) {
        super(text);
		
        this.methodName = methodName;
        this.hedge = hedge;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodName() {
	    return methodName;
    }
    
    public void setHedge(double hedge) {
        this.hedge = hedge;
    }
    
    public double getHedge() {
        return hedge;
    }
}
