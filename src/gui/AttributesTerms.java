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
                new TermRadio("Ciep³o", "hotSetWithTerm"),
                new TermRadio("Bardzo Ciep³o", "hotHedgeSetWithTerm", 2.00));
            
            layout = new GridLayout(0, 3);
        }
        
        else if (key == "Ciœnienie atmosferyczne") {
            addButtonsToPanel(
                panel, bg, listener,
                new TermRadio("Niskie", "lowSetWithTerm"),
                new TermRadio("Œrednie", "mediumSetWithTerm"),
                new TermRadio("Wysokie", "highSetWithTerm"));
            
            layout = new GridLayout(0, 3);
        }
        
        else if (key == "Widocznoœæ") {
            addButtonsToPanel(
                panel, bg, listener,
                new TermRadio("Ma³a", "lowSetWithTerm"),
                new TermRadio("Œrednia", "mediumSetWithTerm"),
                new TermRadio("Du¿a", "highSetWithTerm"));
            
            layout = new GridLayout(0, 3);
        }
        
        else if (key == "Iloœæ opadów") {
            addButtonsToPanel(
                panel, bg, listener,
                new TermRadio("Ma³a", "lowSetWithTerm"),
                new TermRadio("Œrednia", "mediumSetWithTerm"),
                new TermRadio("Du¿a", "highSetWithTerm"));
            
            layout = new GridLayout(0, 3);
        }
        
        else if (key == "Si³a wiatru") {
            addButtonsToPanel(
                panel, bg, listener,
                new TermRadio("S³aba", "slowSetWithTerm"),
                new TermRadio("Umiarkowana", "moderateSetWithTerm"),
                new TermRadio("Silna", "hotSetWithTerm"));
            
            layout = new GridLayout(0, 3);
        }
        
        else if (key == "Kierunek wiatru") {
            addButtonsToPanel(
               panel, bg, listener,
               new TermRadio("Pó³nocny", "NorthSetWithTerm"), // TODO JOIN with North2
               new TermRadio("Wschodni", "EastSetWithTerm"),
               new TermRadio("Po³udniowy", "SouthSetWithTerm"),
               new TermRadio("Zachodni", "WestSetWithTerm"));
            
            layout = new GridLayout(0, 4);
        }
        
        else if (key == "Nas³onecznienie") {
            addButtonsToPanel(
                panel, bg, listener,
                new TermRadio("S³oneczne", "highSetWithTerm"),
                new TermRadio("Pochmurne", "mediumSetWithTerm"),
                new TermRadio("Deszczowe", "lowSetWithTerm"));
            
            layout = new GridLayout(0, 3);
        }
        
        else if (key == "Wilgotnoœæ powietrza") {
            addButtonsToPanel(
                panel, bg, listener,
                new TermRadio("Ma³a", "lowSetWithTerm"),
                new TermRadio("Œrednia", "mediumSetWithTerm"),
                new TermRadio("Du¿a", "highSetWithTerm"));
            
            layout = new GridLayout(0, 3);
        }
        
        else if (key == "Stê¿enie PM2.5") {
            addButtonsToPanel(
                panel, bg, listener,
                new TermRadio("Ma³e", "lowSetWithTerm"),
                new TermRadio("Œrednie", "mediumSetWithTerm"),
                new TermRadio("Du¿e", "highSetWithTerm"));
            
            layout = new GridLayout(0, 3);
        }
        
        else {
            addButtonsToPanel(
                panel, bg, listener,
                new TermRadio("Ma³e", "lowSetWithTerm"),
                new TermRadio("Œrednie", "mediumSetWithTerm"),
                new TermRadio("Du¿e", "highSetWithTerm"));
            
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
