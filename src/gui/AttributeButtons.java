package gui;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;


public class AttributeButtons {
    public static JPanel generateButtons(ButtonGroup bg, ActionListener listener) {
        JPanel buttons = new JPanel();
        GridLayout layout = new GridLayout(0, 2);
    	
        JRadioButton temp = new JRadioButton("Temperatura");
        JRadioButton c = new JRadioButton("Ciœnienie atmosferyczne");
        JRadioButton visi = new JRadioButton("Widocznoœæ");
        JRadioButton preci = new JRadioButton("Iloœæ opadów");
        JRadioButton wp = new JRadioButton("Si³a wiatru");
        JRadioButton wd = new JRadioButton("Kierunek wiatru");
        JRadioButton inso = new JRadioButton("Nas³onecznienie");
        JRadioButton air = new JRadioButton("Wilgotnoœæ powietrza");
        JRadioButton pm2 = new JRadioButton("Stê¿enie PM2.5");
        JRadioButton pm10 = new JRadioButton("Stê¿enie PM10");
        
        temp.addActionListener(listener);
        c.addActionListener(listener);
        visi.addActionListener(listener);
        preci.addActionListener(listener);
        wp.addActionListener(listener);
        wd.addActionListener(listener);
        inso.addActionListener(listener);
        air.addActionListener(listener);
        pm2.addActionListener(listener);
        pm10.addActionListener(listener);

        bg = new ButtonGroup();
        
        bg.add(temp);
        bg.add(c);
        bg.add(visi);
        bg.add(preci);
        bg.add(wp);
        bg.add(wd);
        bg.add(inso);
        bg.add(air);
        bg.add(pm2);
        bg.add(pm10);
    	
        buttons.setLayout(layout);

        buttons.add(temp);
        buttons.add(c);
        buttons.add(visi);
        buttons.add(preci);
        buttons.add(wp);
        buttons.add(wd);
        buttons.add(inso);
        buttons.add(air);
        buttons.add(pm2);
        buttons.add(pm10);
        
        return buttons;
    }
}
