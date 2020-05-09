package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

import db.Repository;
import db.Weather;
import fuzzy_set.FuzzySet;
import hedges.PowerHedge;
import main.TermData;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import quantifiers.AbsoluteQ;
import quantifiers.Matcher;
import quantifiers.RelativeQ;

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
            List<Weather> records = repo.getDaysBetweenDates(DatePicker.getDate(dp1), DatePicker.getDate(dp2));
        	
            textArea.setText("");

            if (attrChoice == null || termChoice == null || quantifierChoice == null) {
                textArea.setText("Musisz wybra� jeden z atrybut�w powy�ej");
                return;
            }

            AttributeToClass attr = new AttributeToClass();
            TermData term = attr.getTerm(records, attrChoice, termChoice); 
            
            String quantifier = "";
            double degreeOfTruth = 0.0;
            
            if (quantifierChoice.equals("Absolutne")) {
                degreeOfTruth = 1;
            	
                quantifier = AbsoluteQ.exactMatching(term.getSet(), new Matcher() {
                    @Override
                    public boolean matcher(double membership) {
                        return Belongs.belongsToTerm(attrChoice, termChoice, membership);
                    }
                });
            } else {
                ArrayList<Double> universe = attr.getUniverse(attrChoice);

                if (!term.getSet().isConvex(universe.get(0), universe.get(1), term.getMembership())) {
                    textArea.setText("Zbi�r rozmyty nie jest wypuk�y");
                    return;
                }

                if (!term.getSet().isNormal()) {
                   term.setSet(this.normalizeSet(term.getSet()));
                }

                // TODO check degree of truth.
                quantifier = RelativeQ.quantifySingle(term.getSet(), new Matcher() {
                    @Override
                    public boolean matcher(double membership) {
                        return Belongs.belongsToTerm(attrChoice, termChoice, membership);
                    }
                });
            }
            
            String text = quantifier + Utils.getPluralSubject(true) + PowerHedge.toString(hedge) + term.getTerm().getPluralLabe() + "\n\n Prawdziwo��: " + degreeOfTruth;
            textArea.setText(text);
        }
        
        private FuzzySet normalizeSet(FuzzySet set) {        	
            return new FuzzySet(set.normalize(set.getStreamOfSet()).collect(Collectors.toList()));
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
