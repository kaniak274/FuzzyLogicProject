package gui;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;

public class AttributesTerms {
    public static void showTerms(JPanel panel, String key, ActionListener listener) {
        panel.removeAll();
        ButtonGroup bg = new ButtonGroup();
        GridLayout layout = null;
        TermRadio button;
        TermRadio button2;
        TermRadio button3;

        if (key == "Temperatura") {
            button = new TermRadio("Zimno", "coldSetWithTerm");
            button2 = new TermRadio("Umiarkowanie", "moderateSetWithTerm");
            button3 = new TermRadio("Ciep�o", "hotSetWithTerm");
            
            bg.add(button);
            bg.add(button2);
            bg.add(button3);
            
            panel.add(button);
            panel.add(button2);
            panel.add(button3);
            
            layout = new GridLayout(0, 3);
        }
        
        else if (key == "Ci�nienie atmosferyczne") {
            button = new TermRadio("Niskie", "lowSetWithTerm");
            button2 = new TermRadio("�rednie", "mediumSetWithTerm");
            button3 = new TermRadio("Wysokie", "highSetWithTerm");
            
            bg.add(button);
            bg.add(button2);
            bg.add(button3);
            
            panel.add(button);
            panel.add(button2);
            panel.add(button3);
            
            layout = new GridLayout(0, 3);
        }
        
        else if (key == "Widoczno��") {
            button = new TermRadio("Ma�a", "lowSetWithTerm");
            button2 = new TermRadio("�rednia", "mediumSetWithTerm");
            button3 = new TermRadio("Du�a", "highSetWithTerm");
            
            bg.add(button);
            bg.add(button2);
            bg.add(button3);
            
            panel.add(button);
            panel.add(button2);
            panel.add(button3);
            
            layout = new GridLayout(0, 3);
        }
        
        else if (key == "Ilo�� opad�w") {
            button = new TermRadio("Ma�a", "lowSetWithTerm");
            button2 = new TermRadio("�rednia", "mediumSetWithTerm");
            button3 = new TermRadio("Du�a", "highSetWithTerm");
            
            bg.add(button);
            bg.add(button2);
            bg.add(button3);
            
            panel.add(button);
            panel.add(button2);
            panel.add(button3);
            
            layout = new GridLayout(0, 3);
        }
        
        else if (key == "Si�a wiatru") {
            button = new TermRadio("Ma�a", "slowSetWithTerm");
            button2 = new TermRadio("Umiarkowana", "moderateSetWithTerm");
            button3 = new TermRadio("Du�a", "hotSetWithTerm");
            
            bg.add(button);
            bg.add(button2);
            bg.add(button3);
            
            panel.add(button);
            panel.add(button2);
            panel.add(button3);
            
            layout = new GridLayout(0, 3);
        }
        
        else if (key == "Kierunek wiatru") {
            button = new TermRadio("P�nocny", "NorthSetWithTerm"); // TODO
            button2 = new TermRadio("Wschodni", "EastSetWithTerm");
            button3 = new TermRadio("Po�udniowy", "SouthSetWithTerm");
            TermRadio button4 = new TermRadio("Zachodni", "WestSetWithTerm");
            
            bg.add(button);
            bg.add(button2);
            bg.add(button3);
            bg.add(button4);
            
            panel.add(button);
            panel.add(button2);
            panel.add(button3);
            panel.add(button4);
            
            button4.addActionListener(listener);
            
            layout = new GridLayout(0, 4);
        }
        
        else if (key == "Nas�onecznienie") {
            button = new TermRadio("S�oneczne", "highSetWithTerm");
            button2 = new TermRadio("Pochmurne", "mediumSetWithTerm");
            button3 = new TermRadio("Deszczowe", "lowSetWithTerm");
            
            bg.add(button);
            bg.add(button2);
            bg.add(button3);
            
            panel.add(button);
            panel.add(button2);
            panel.add(button3);
            
            layout = new GridLayout(0, 3);
        }
        
        else if (key == "Wilgotno�� powietrza") {
            button = new TermRadio("Ma�a", "lowSetWithTerm");
            button2 = new TermRadio("�rednia", "mediumSetWithTerm");
            button3 = new TermRadio("Du�a", "highSetWithTerm");
            
            bg.add(button);
            bg.add(button2);
            bg.add(button3);
            
            panel.add(button);
            panel.add(button2);
            panel.add(button3);
            
            layout = new GridLayout(0, 3);
        }
        
        else if (key == "St�enie PM2.5") {
            button = new TermRadio("Ma�e", "lowSetWithTerm");
            button2 = new TermRadio("�rednie", "mediumSetWithTerm");
            button3 = new TermRadio("Du�e", "highSetWithTerm");
            
            bg.add(button);
            bg.add(button2);
            bg.add(button3);
            
            panel.add(button);
            panel.add(button2);
            panel.add(button3);
            
            layout = new GridLayout(0, 3);
        }
        
        else {
            button = new TermRadio("Ma�e", "lowSetWithTerm");
            button2 = new TermRadio("�rednie", "mediumSetWithTerm");
            button3 = new TermRadio("Du�e", "highSetWithTerm");
            
            bg.add(button);
            bg.add(button2);
            bg.add(button3);
            
            panel.add(button);
            panel.add(button2);
            panel.add(button3);
            
            layout = new GridLayout(0, 3);
        }
        
        button.addActionListener(listener);
        button2.addActionListener(listener);
        button3.addActionListener(listener);
        
        panel.setLayout(layout);
    }
}
