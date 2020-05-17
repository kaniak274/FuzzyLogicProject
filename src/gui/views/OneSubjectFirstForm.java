package gui.views;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

import db.Repository;
import generators.FirstForm;
import gui.buttons.AttributeButtons;
import gui.buttons.AttributesTerms;
import gui.buttons.QuantifierButtons;
import gui.buttons.TermRadio;
import gui.date_picker.DatePicker;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;

@SuppressWarnings("serial")
public class OneSubjectFirstForm extends JPanel {
    private Repository repo;

    private JPanel panel;
    private JPanel terms;
    private JTextArea textArea;
    private ButtonGroup bg;
    
    private String attrChoice;
    private String termChoice;
    private String quantifierChoice;
    private double hedge;
    
    private JDatePickerImpl dp1;
    private JDatePickerImpl dp2;
   
    public OneSubjectFirstForm(Repository repo) {
        this.repo = repo;
        attrChoice = null;
        hedge = 0;
    	
        panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));
        
        JButton generate = new JButton("Generuj");
        generate.addActionListener(new Generate());
        
        terms = new JPanel();
        
        JPanel buttons = AttributeButtons.generateButtons(bg, new ButtonGroupListener());
        
        dp1 = DatePicker.createDatePicker();
        dp2 = DatePicker.createDatePicker();
        
        panel.add(new JLabel("Atrybuty:"));
        panel.add(buttons);
        panel.add(terms);
        panel.add(new JLabel("Kwantyfikatory:"));
        panel.add(QuantifierButtons.generateQButtons(true, new QuantifierListener()));
        panel.add(dp1);
        panel.add(dp2);
        panel.add(generate);
        
        textArea = new JTextArea(5, 20);
        textArea.setEditable(false);
        panel.add(textArea);
        
        JRadioButton b = (JRadioButton)buttons.getComponent(0);
        b.setSelected(true);
        attrChoice = b.getActionCommand();
        AttributesTerms.showTerms(terms, attrChoice, new TermActionListener());
    }
    
    public JPanel getPanel() {
        return panel;
    }
    
    class Generate implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent arg0) {
            textArea.setText(FirstForm.generate(
                repo,
                DatePicker.getDate(dp1),
                DatePicker.getDate(dp2),
                attrChoice,
                termChoice,
                quantifierChoice,
                hedge
            ));
        }
    }
    
    class ButtonGroupListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            attrChoice = e.getActionCommand();

            AttributesTerms.showTerms(terms, attrChoice, new TermActionListener());
            panel.revalidate();
            panel.repaint();
        }
    }
    
    class TermActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            TermRadio source = (TermRadio)e.getSource();
            termChoice = source.getMethodName();
            hedge = source.getHedge();
        }
    }
    
    class QuantifierListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            quantifierChoice = e.getActionCommand();
        }
    }
}
