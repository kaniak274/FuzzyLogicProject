package gui.views;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import db.Repository;

public class MainWindow implements ItemListener {
    private JPanel cards;
    private Repository repo;
    
    final static String FROMFILE = "Wczytaj z pliku dane do podsumowania";
    
    public MainWindow(Repository repo) {
    	this.repo = repo;
    }
    
    public void createWindow() {
    	JFrame frame = new JFrame("KADI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         
        this.addComponentToPane(frame.getContentPane());

        frame.setPreferredSize(new Dimension(400, 900));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    public void addComponentToPane(Container pane) {
        JPanel comboBoxPane = new JPanel();
        String comboBoxItems[] = { FROMFILE };

	    JComboBox<String> cb = new JComboBox<>(comboBoxItems);
        cb.setEditable(false);
        cb.addItemListener(this);

        comboBoxPane.add(cb);
         
        cards = new JPanel(new CardLayout());
        cards.add(new FromFile(repo).getPanel(), FROMFILE);
         
        pane.add(comboBoxPane, BorderLayout.PAGE_START);
        pane.add(cards, BorderLayout.CENTER);
    }
    
    public void itemStateChanged(ItemEvent evt) {
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, (String)evt.getItem());
    }
}
