package gui;

import javax.swing.JRadioButton;

@SuppressWarnings("serial")
public class TermRadio extends JRadioButton {
	private String methodName;
	
	public TermRadio(String text, String methodName) {
		super(text);
		
		this.methodName = methodName;
	}
	
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	
	public String getMethodName() {
		return methodName;
	}
}
