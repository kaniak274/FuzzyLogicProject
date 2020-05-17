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
import generators.SecondForm;
import gui.buttons.AttributeButtons;
import gui.buttons.AttributesTerms;
import gui.buttons.ConjunctionButtons;
import gui.buttons.TermRadio;
import gui.date_picker.DatePicker;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;

@SuppressWarnings("serial")
public class OneSubjectSecondForm extends JPanel {
    private Repository repo;

    private JPanel panel;
    private JPanel terms;
    private JPanel terms2;
    private JTextArea textArea;
    private ButtonGroup bg;
    
    private String attrChoice;
    private String termChoice;
    
    private String attr2Choice;
    private String term2Choice;
    
    private String conjuction;
    
    private double hedge;
    private double hedge2;
    
    private JDatePickerImpl dp1;
    private JDatePickerImpl dp2;
	
    public OneSubjectSecondForm(Repository repo) {
    	this.repo = repo;
        attrChoice = null;
        hedge = 0;
        hedge2 = 0;
    	
        panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));
        
        JButton generate = new JButton("Generuj");
        generate.addActionListener(new Generate());
        
        terms = new JPanel();
        terms2 = new JPanel();
        
        JPanel buttons = AttributeButtons.generateButtons(bg, new ButtonGroupListener());
        JPanel buttons2 = AttributeButtons.generateButtons(bg, new Button2GroupListener());
        
        dp1 = DatePicker.createDatePicker();
        dp2 = DatePicker.createDatePicker();
        
        panel.add(new JLabel("Atrybut1"));
        panel.add(buttons);
        panel.add(terms);
        
        panel.add(new JLabel("Atrybut2"));
        panel.add(buttons2);
        panel.add(terms2);
        
        panel.add(new JLabel("Spójniki"));
        panel.add(ConjunctionButtons.createButtons(new ConjuctionActionListener()));

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
        
        JRadioButton b2 = (JRadioButton)buttons2.getComponent(1);
        b2.setSelected(true);
        attr2Choice = b2.getActionCommand();
        AttributesTerms.showTerms(terms2, attr2Choice, new Term2ActionListener());
    }
    
    public JPanel getPanel() {
        return panel;
    }
    
    class Generate implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent arg0) {
            textArea.setText(SecondForm.generate(
                repo,
                DatePicker.getDate(dp1),
                DatePicker.getDate(dp2),
                attrChoice,
                termChoice,
                hedge,
                attr2Choice,
                term2Choice,
                hedge2,
                conjuction
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
    
    class Button2GroupListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            attr2Choice = e.getActionCommand();

            AttributesTerms.showTerms(terms2, attr2Choice, new TermActionListener());
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
    
    class Term2ActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            TermRadio source = (TermRadio)e.getSource();
            term2Choice = source.getMethodName();
            hedge2 = source.getHedge();
        }
    }
    
    class ConjuctionActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            conjuction = e.getActionCommand();
        }
    }
}
