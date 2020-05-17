package gui.views;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import db.Repository;

public class UserWindow {
    Repository repo;
    JButton casual;
    JButton advanced;
    JFrame frame;
	
    public UserWindow(Repository repo) {
        this.repo = repo;
    }
    
    public void createWindow() {
        frame = new JFrame("KADI");
        frame.setLayout(new GridLayout(0, 2));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        casual = new JButton("Niezaawansowany u¿ytkownik");
        advanced = new JButton("Zaawansowany u¿ytkownik");
        
        casual.addActionListener(new CasualButtonActionListener());
        advanced.addActionListener(new AdvancedButtonActionListener());
        
        frame.add(casual);
        frame.add(advanced);

        frame.setPreferredSize(new Dimension(600, 300));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    private class CasualButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            frame.setVisible(false);
            frame.dispose();
            
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    UIManager.put("swing.boldMetal", Boolean.FALSE);
                    MainWindow window = new MainWindow(repo);
                    window.createWindow();
                }
            });
        }
    }
    
    private class AdvancedButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            frame.setVisible(false);
            frame.dispose();
        }
    }
}
