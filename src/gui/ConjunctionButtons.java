package gui;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class ConjunctionButtons {
    public static JPanel createButtons(ActionListener listener) {
        JPanel panel = new JPanel(new GridLayout(0, 4));
        ButtonGroup bg = new ButtonGroup();

        JRadioButton and = new JRadioButton("I");
        JRadioButton or = new JRadioButton("Lub");
        JRadioButton andnot = new JRadioButton("I nie");
        JRadioButton ornot = new JRadioButton("Lub nie");
        
        and.addActionListener(listener);
        or.addActionListener(listener);
        andnot.addActionListener(listener);
        ornot.addActionListener(listener);
        
        bg.add(and);
        bg.add(or);
        bg.add(andnot);
        bg.add(ornot);
        
        panel.add(and);
        panel.add(or);
        panel.add(andnot);
        panel.add(ornot);
        
        return panel;
    }
}
