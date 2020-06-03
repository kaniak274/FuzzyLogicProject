package gui.buttons;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class MembershipButtons {
    public static JPanel generateButtons(ButtonGroup bg, ActionListener listener) {
        JPanel buttons = new JPanel();
        GridLayout layout = new GridLayout(0, 3);
    	
        JRadioButton triangle = new JRadioButton("Trójk¹tna");
        JRadioButton gauss = new JRadioButton("Gaussa");
        JRadioButton trapezoid = new JRadioButton("Trapezoid");

        triangle.addActionListener(listener);
        gauss.addActionListener(listener);
        trapezoid.addActionListener(listener);

        bg = new ButtonGroup();
        
        bg.add(triangle);
        bg.add(gauss);
        bg.add(trapezoid);

        buttons.setLayout(layout);

        buttons.add(triangle);
        buttons.add(gauss);
        buttons.add(trapezoid);
        
        triangle.setSelected(true);
        
        return buttons;
    }
}
