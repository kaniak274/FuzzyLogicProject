package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import db.Repository;
import db.Weather;
import fuzzy_set.FuzzySet;
import main.Temperature;
import quantifiers.AbsoluteQ;
import quantifiers.Matcher;
import terms.Term;

@SuppressWarnings("serial")
public class OneSubjectFirstForm extends JPanel {
    private JPanel panel;
    private JPanel terms;
    private JTextArea textArea;
    private ButtonGroup bg;
    private String attrChoice;
    private String termChoice;
    private Repository repo;
   
    public OneSubjectFirstForm(Repository repo) {
        this.repo = repo;
        attrChoice = null;
    	
        panel = new JPanel();
        panel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        JButton generate = new JButton("Generuj");
        generate.addActionListener(new Generate());
        
        terms = new JPanel();

        panel.add(AttributeButtons.generateButtons(bg, new ButtonGroupListener()));
        panel.add(terms);
        panel.add(Box.createRigidArea(new Dimension(0, 30)));
        panel.add(generate);
        panel.add(Box.createRigidArea(new Dimension(0, 30)));
        
        textArea = new JTextArea(5, 20);
        textArea.setEditable(false);
        panel.add(textArea);
    }
    
    public JPanel getPanel() {
        return panel;
    }
    
    class Generate implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent arg0) {
            List<Weather> records = repo.getDaysBetweenDates(repo.formatDate("1992-01-01"), repo.formatDate("1992-01-31"));
        	
            textArea.setText("");

            if (attrChoice == null) {
                textArea.setText("Musisz wybraæ jeden z atrybutów powy¿ej");
                return;
            }

            Entry<Term, FuzzySet> term = new AttributeToClass().getTerm(records, attrChoice, termChoice);
            
            String count = AbsoluteQ.exactMatching(term.getValue(), new Matcher() {
                @Override
                public boolean matcher(double membership) {
                    return new Temperature().wasHot(membership);  // FIND A WAY HOW TO PASS THIS FUNCTION HERE.
                }
            });
            
            String text = count + Utils.getPluralSubject(true) + term.getKey().getPluralLabe();
            textArea.setText(text);
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
        public void actionPerformed(ActionEvent arg0) {
            TermRadio source = (TermRadio)arg0.getSource();
            termChoice = source.getMethodName();
        }
    }
}
