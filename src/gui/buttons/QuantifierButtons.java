package gui.buttons;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class QuantifierButtons {
    public static JPanel generateQButtons(boolean showAbsolute, ActionListener listener) {
        JPanel panel = new JPanel();
        GridLayout layout = new GridLayout(0, 2);
        panel.setLayout(layout);
    	
        ButtonGroup bg = new ButtonGroup();

        if (showAbsolute) {
            JRadioButton absolute = new JRadioButton("Absolutne");
            bg.add(absolute);
            panel.add(absolute);
            absolute.addActionListener(listener);
        }
        
        JRadioButton relative = new JRadioButton("Relatywne");
        bg.add(relative);
        panel.add(relative);
        relative.addActionListener(listener);

        return panel;
    }
}
