package gui.views;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import gui.buttons.AttributeButtons;
import gui.buttons.MembershipButtons;
import gui.buttons.MembershipInputs;

public class AddNewTermWindow {
    private JPanel panel;
    private JPanel membershipInputs;

    private ButtonGroup bg;
    private ButtonGroup mg;
    
    private String attrChoice;
    private String membershipChoice;
    
    private JTextField label1;
    private JTextField label2;
    private JTextField label3;
    
    public JPanel getPanel() {
        return panel;
    }
    
    public void createWindow() {
    	JFrame frame = new JFrame("KADI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.create(frame.getContentPane());

        frame.setPreferredSize(new Dimension(400, 900));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    private void create(Container pane) {
        attrChoice = null;
        membershipChoice = null;
    	
        membershipInputs = new JPanel();
        panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));
        
        JButton generate = new JButton("Dodaj now¹ etykietê");
        generate.addActionListener(new Generate());

        JPanel buttons = AttributeButtons.generateButtons(bg, new ButtonGroupListener());
        JPanel membershipButtons = MembershipButtons.generateButtons(mg, new MembershipGroupListener());
        
        panel.add(new JLabel("Wybierz atrybut"));
        panel.add(buttons);
        
        panel.add(new JLabel("Wybierz typ funkcji przynale¿noœci"));
        panel.add(membershipButtons);
        panel.add(membershipInputs);
        
        label1 = new JTextField();
        label2 = new JTextField();
        label3 = new JTextField();
        
        panel.add(label1);
        panel.add(label2);
        panel.add(label3);

        panel.add(generate);
        
        JRadioButton b = (JRadioButton)buttons.getComponent(0);
        b.setSelected(true);
        attrChoice = b.getActionCommand();
        
        JRadioButton b2 = (JRadioButton)membershipButtons.getComponent(0);
        b2.setSelected(true);
        membershipChoice = b2.getActionCommand();
        MembershipInputs.showTerms(membershipInputs, membershipChoice);

        pane.add(panel);
    }
    
    class Generate implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent arg0) {
            String[] terms = null;
            
            if (membershipChoice.equals("Trapezoid")) {
                JTextField mt1 = (JTextField)membershipInputs.getComponent(0);
                JTextField mt2 = (JTextField)membershipInputs.getComponent(1);
                JTextField mt3 = (JTextField)membershipInputs.getComponent(2);
                JTextField mt4 = (JTextField)membershipInputs.getComponent(3);
                
                terms = new String[] {attrChoice, membershipChoice, mt1.getText(), mt2.getText(),
                    mt3.getText(), mt4.getText(), label1.getText(), label2.getText(), label3.getText()};
            } else {
                JTextField mt1 = (JTextField)membershipInputs.getComponent(0);
                JTextField mt2 = (JTextField)membershipInputs.getComponent(1);
                JTextField mt3 = (JTextField)membershipInputs.getComponent(2);
                
                terms = new String[] {attrChoice, membershipChoice, mt1.getText(), mt2.getText(),
                    mt3.getText(), label1.getText(), label2.getText(), label3.getText()};
            }
            
            String joined = Arrays.asList(terms).stream().collect(Collectors.joining(","));

            try {
                //FileWriter myWriter = new FileWriter(pathInput.getText(), true);
                FileWriter myWriter = new FileWriter("C:/Users/Kaniak/Documents/newTerms.txt", true);
                myWriter.write(joined);
                myWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return;
        }
    }
    
    class ButtonGroupListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            attrChoice = e.getActionCommand();
        }
    }
    
    class MembershipGroupListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            membershipChoice = e.getActionCommand();

            MembershipInputs.showTerms(membershipInputs, membershipChoice);
            panel.revalidate();
            panel.repaint();
        }
    }
}
