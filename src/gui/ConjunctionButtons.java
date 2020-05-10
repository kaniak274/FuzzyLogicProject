package gui;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class ConjunctionButtons {
    public static JPanel createButtons(ActionListener listener) {
        JPanel panel = new JPanel(new GridLayout(0, 3));
        ButtonGroup bg = new ButtonGroup();

        JRadioButton and = new JRadioButton("I");
        JRadioButton or = new JRadioButton("Oraz");
        JRadioButton not = new JRadioButton("Nie");
        
        and.addActionListener(listener);
        or.addActionListener(listener);
        not.addActionListener(listener);
        
        bg.add(and);
        bg.add(or);
        bg.add(not);
        
        panel.add(and);
        panel.add(or);
        panel.add(not);
        
        return panel;
    }
}
