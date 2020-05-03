package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import db.Repository;

public class MainWindow implements ItemListener {
    private JPanel cards;
    private Repository repo;
    
    final static String ONESUBJECTSUMMARY = "Podsumowanie lingwistyczne jednopodmiotowe w pierwszej formie";
    final static String ONESUBJECTMANYSUMMARY = "Podsumowanie lingwistyczne jednopodmiotowe w drugiej formie";
    final static String MANYSUBJECTSSUMMARY = "Podsumowanie wielopodmiotowe";
    
    public MainWindow(Repository repo) {
    	this.repo = repo;
    }
    
    public void createWindow() {
    	JFrame frame = new JFrame("KADI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         
        this.addComponentToPane(frame.getContentPane());
         
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    public void addComponentToPane(Container pane) {
        JPanel comboBoxPane = new JPanel();
        String comboBoxItems[] = { ONESUBJECTSUMMARY, ONESUBJECTMANYSUMMARY, MANYSUBJECTSSUMMARY };

	    JComboBox<String> cb = new JComboBox<>(comboBoxItems);
        cb.setEditable(false);
        cb.addItemListener(this);

        comboBoxPane.add(cb);
         
        JPanel card2 = new JPanel();
        card2.add(new JTextField("TextField", 20));
        
        JPanel card3 = new JPanel();
        card3.add(new JTextField("TextField", 20));
         
        cards = new JPanel(new CardLayout());
        cards.add(new OneSubjectFirstForm(repo).getPanel(), ONESUBJECTSUMMARY);
        cards.add(card2, ONESUBJECTMANYSUMMARY);
        cards.add(card3, MANYSUBJECTSSUMMARY);
         
        pane.add(comboBoxPane, BorderLayout.PAGE_START);
        pane.add(cards, BorderLayout.CENTER);
    }
    
    public void itemStateChanged(ItemEvent evt) {
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, (String)evt.getItem());
    }
}
