package gui.views;

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
import gui.AttributeButtons;
import gui.AttributeToClass;
import gui.AttributesTerms;
import gui.Belongs;
import gui.ConjunctionButtons;
import gui.DatePicker;
import gui.TermRadio;
import gui.Utils;
import gui.exceptions.NotConvexException;
import hedges.PowerHedge;
import main.TermData;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import quantifiers.Matcher;
import quantifiers.RelativeQ;
import quantifiers.Truth;

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
            List<Weather> records = getDays();
        	
            textArea.setText("");

            if (termChoice == null || term2Choice == null) {
                textArea.setText("Musisz wybraæ oba atrybuty");
                return;
            }
            
            if (attrChoice.equals(attr2Choice)) {
                textArea.setText("Nie mo¿esz wybraæ dwóch takich samych atrybutów");
                return;
            }
            
            TermData term1 = getTermForAttribute(records, attrChoice, termChoice);
            TermData term2 = getTermForAttribute(records, attr2Choice, term2Choice);
            
            String quantifier = "";
            double degreeOfTruth = 0.0;
            
            try {
                isConvex(term1, attrChoice);
                isConvex(term2, attr2Choice);
            } catch (NotConvexException e) {
                textArea.setText("Jeden z zbiorów rozmytych nie jest wypuk³y");
                return;
            }

            if (!term1.getSet().isNormal()) {
               term1.setSet(this.normalizeSet(term1.getSet()));
            }
            
            if (!term2.getSet().isNormal()) {
                term2.setSet(this.normalizeSet(term2.getSet()));
            }
            
            Matcher matcher = getMatcher();
            quantifier = quantify(term1, term2, matcher);
            
            // TODO
            // degreeOfTruth = Truth.degreeOfTruthRelative(term.getSet(), matcher);
            
            String text = quantifier + Utils.getPluralSubject(true) + PowerHedge.toString(hedge) + term1.getTerm().getPluralLabel() + getConjuctionLabel()
                + PowerHedge.toString(hedge2) + term2.getTerm().getPluralLabel();
            textArea.setText(text);
        }
        
        private List<Weather> getDays() {
            return repo.getDaysBetweenDates(DatePicker.getDate(dp1), DatePicker.getDate(dp2));
        }
        
        private TermData getTermForAttribute(List<Weather> records, String attribute, String term) {
            AttributeToClass attr = new AttributeToClass();
            return attr.getTerm(records, attribute, term); 
        }
        
        private void isConvex(TermData attr, String key) throws NotConvexException {
            ArrayList<Double> universe = getAttributeUniverse(key);
        	
            if (!attr.getSet().isConvex(universe.get(0), universe.get(1), attr.getMembership())) {
                throw new NotConvexException();
            }
        }
        
        private ArrayList<Double> getAttributeUniverse(String attribute) {
        	return new AttributeToClass().getUniverse(attribute);
        }
        
        private FuzzySet normalizeSet(FuzzySet set) {        	
            return new FuzzySet(set.normalize(set.getStreamOfSet()).collect(Collectors.toList()));
        }
        
        private Matcher getMatcher() {
            if (conjuction.equals("I")) {
                return new Matcher() {
                    @Override
                    public boolean matcher(double membership) {
                        return Belongs.belongsToTerm(attrChoice, termChoice, membership)
                            && Belongs.belongsToTerm(attr2Choice, term2Choice, membership);
                    }
                    
                    public boolean matcher(double membership, double membership2) { throw new RuntimeException(); }
                };
            }
            
            if (conjuction.equals("Lub")) {
                return new Matcher() {
                    @Override
                    public boolean matcher(double membership) {
                        return Belongs.belongsToTerm(attrChoice, termChoice, membership)
                            || Belongs.belongsToTerm(attr2Choice, term2Choice, membership);
                    }
                    
                    public boolean matcher(double membership, double membership2) { throw new RuntimeException(); }
                };
            }
            
            if (conjuction.equals("I nie")) {
                return new Matcher() {
                    @Override
                    public boolean matcher(double membership) { throw new RuntimeException(); }
                    
                    public boolean matcher(double membership, double membership2) {
                    	return Belongs.belongsToTerm(attrChoice, termChoice, membership)
                            && !Belongs.belongsToTerm(attr2Choice, term2Choice, membership2);
                    }
                };
            }

            return new Matcher() {
                @Override
                public boolean matcher(double membership) { throw new RuntimeException(); }
                
                public boolean matcher(double membership, double membership2) {
                	return Belongs.belongsToTerm(attrChoice, termChoice, membership)
                        || !Belongs.belongsToTerm(attr2Choice, term2Choice, membership2);
                }
            };
        }
        
        private String quantify(TermData attr1, TermData attr2, Matcher matcher) {
            if (conjuction.equals("I")) {
                return RelativeQ.quantifyAnd(attr1.getSet(), attr2.getSet(), matcher);
            }
            
            if (conjuction.equals("Lub")) {
                return RelativeQ.quantifyOr(attr1.getSet(), attr2.getSet(), matcher);
            }
            
            return RelativeQ.quantifyNot(attr1.getSet(), attr2.getSet(), matcher);
        }
        
        private String getConjuctionLabel() {
            if (conjuction.equals("I")) {
                return " i ";
            }
            
            if (conjuction.equals("Lub")) {
                return " lub " ;
            }
            
            if (conjuction.equals("I nie")) {
                return " i nie ";
            }

            return " lub nie ";
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
