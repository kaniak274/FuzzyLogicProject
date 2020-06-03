package gui.buttons;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class MembershipInputs {
    public static void showTerms(JPanel panel, String key) {
        panel.removeAll();
        GridLayout layout = null;

        if (key == "Trójk¹tna") {
            panel.add(new JTextField());
            panel.add(new JTextField());
            panel.add(new JTextField());
            layout = new GridLayout(0, 3);
        }
        
        else if (key == "Trapezoid") {
            panel.add(new JTextField());
            panel.add(new JTextField());
            panel.add(new JTextField());
            panel.add(new JTextField());
            layout = new GridLayout(0, 4);
        }
        
        else {
            panel.add(new JTextField());
            panel.add(new JTextField());
            panel.add(new JTextField());
            layout = new GridLayout(0, 3);
        }
        
        panel.setLayout(layout);
    }
}
