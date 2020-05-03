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

        if (key == "Temperatura") {
            addButtonsToPanel(
                panel, bg, listener,
                new TermRadio("Bardzo Zimno", "coldHedgeSetWithTerm", 2.00),
                new TermRadio("Zimno", "coldSetWithTerm"),
                new TermRadio("Umiarkowanie", "moderateSetWithTerm"),
                new TermRadio("Ciep�o", "hotSetWithTerm"),
                new TermRadio("Bardzo Ciep�o", "hotHedgeSetWithTerm", 2.00));
            
            layout = new GridLayout(0, 3);
        }
        
        else if (key == "Ci�nienie atmosferyczne") {
            addButtonsToPanel(
                panel, bg, listener,
                new TermRadio("Niskie", "lowSetWithTerm"),
                new TermRadio("�rednie", "mediumSetWithTerm"),
                new TermRadio("Wysokie", "highSetWithTerm"));
            
            layout = new GridLayout(0, 3);
        }
        
        else if (key == "Widoczno��") {
            addButtonsToPanel(
                panel, bg, listener,
                new TermRadio("Ma�a", "lowSetWithTerm"),
                new TermRadio("�rednia", "mediumSetWithTerm"),
                new TermRadio("Du�a", "highSetWithTerm"));
            
            layout = new GridLayout(0, 3);
        }
        
        else if (key == "Ilo�� opad�w") {
            addButtonsToPanel(
                panel, bg, listener,
                new TermRadio("Ma�a", "lowSetWithTerm"),
                new TermRadio("�rednia", "mediumSetWithTerm"),
                new TermRadio("Du�a", "highSetWithTerm"));
            
            layout = new GridLayout(0, 3);
        }
        
        else if (key == "Si�a wiatru") {
            addButtonsToPanel(
                panel, bg, listener,
                new TermRadio("S�aba", "slowSetWithTerm"),
                new TermRadio("Umiarkowana", "moderateSetWithTerm"),
                new TermRadio("Silna", "hotSetWithTerm"));
            
            layout = new GridLayout(0, 3);
        }
        
        else if (key == "Kierunek wiatru") {
            addButtonsToPanel(
               panel, bg, listener,
               new TermRadio("P�nocny", "NorthSetWithTerm"), // TODO JOIN with North2
               new TermRadio("Wschodni", "EastSetWithTerm"),
               new TermRadio("Po�udniowy", "SouthSetWithTerm"),
               new TermRadio("Zachodni", "WestSetWithTerm"));
            
            layout = new GridLayout(0, 4);
        }
        
        else if (key == "Nas�onecznienie") {
            addButtonsToPanel(
                panel, bg, listener,
                new TermRadio("S�oneczne", "highSetWithTerm"),
                new TermRadio("Pochmurne", "mediumSetWithTerm"),
                new TermRadio("Deszczowe", "lowSetWithTerm"));
            
            layout = new GridLayout(0, 3);
        }
        
        else if (key == "Wilgotno�� powietrza") {
            addButtonsToPanel(
                panel, bg, listener,
                new TermRadio("Ma�a", "lowSetWithTerm"),
                new TermRadio("�rednia", "mediumSetWithTerm"),
                new TermRadio("Du�a", "highSetWithTerm"));
            
            layout = new GridLayout(0, 3);
        }
        
        else if (key == "St�enie PM2.5") {
            addButtonsToPanel(
                panel, bg, listener,
                new TermRadio("Ma�e", "lowSetWithTerm"),
                new TermRadio("�rednie", "mediumSetWithTerm"),
                new TermRadio("Du�e", "highSetWithTerm"));
            
            layout = new GridLayout(0, 3);
        }
        
        else {
            addButtonsToPanel(
                panel, bg, listener,
                new TermRadio("Ma�e", "lowSetWithTerm"),
                new TermRadio("�rednie", "mediumSetWithTerm"),
                new TermRadio("Du�e", "highSetWithTerm"));
            
            layout = new GridLayout(0, 3);
        }
        
        panel.setLayout(layout);
    }
    
    private static void addButtonsToPanel(JPanel panel, ButtonGroup bg, ActionListener listener, TermRadio ...buttons) {
        for (TermRadio button : buttons) {
            bg.add(button);
            button.addActionListener(listener);
            panel.add(button);
        }
    }
}
