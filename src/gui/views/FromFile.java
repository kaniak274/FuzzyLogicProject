package gui.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;

import db.Repository;
import generators.FirstForm;
import generators.SecondForm;
import gui.date_picker.DatePicker;

public class FromFile {
    Repository repo;
    JPanel panel;
    JTextField pathInput;

    JFileChooser fc = new JFileChooser();
    File file;
    File newFile;

    final static String FIRSTFORM = "F";
    final static String SECONDFORM = "S";

    public FromFile(Repository repo) {
        this.repo = repo;
        
        panel = new JPanel();
        
        JButton getFile = new JButton("Wczytaj plik");
        getFile.addActionListener(new FileChooserListener());
        panel.add(getFile);

        JButton generate = new JButton("Generuj");
        generate.addActionListener(new GenerateListener());

        pathInput = new JTextField(30);
        pathInput.setText("Œcie¿ka do pliku");
        
        panel.add(pathInput);
        panel.add(generate);
    }
    
    public JPanel getPanel() {
        return panel;
    }
    
    private class FileChooserListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (fc.showOpenDialog(panel) != JFileChooser.APPROVE_OPTION) {
                return;
            }

            file = fc.getSelectedFile();
        }
    }

    private class GenerateListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Scanner scanner = getScanner();

            while (scanner.hasNextLine()) {
                List<String> params = Arrays.asList(scanner.nextLine().split(","));
                String type = params.get(0);
                String summary = "";

                if (type.equals(FIRSTFORM)) {
                    summary = generateFirstForm(params);
                } else if (type.equals(SECONDFORM)) {
                	summary = generateSecondForm(params);
                }

                writeSummary(summary + "\n\n");
            }
        }

        private Scanner getScanner() {
            try {
				return new Scanner(file, "UTF-8");
			} catch (FileNotFoundException e) {
				throw new RuntimeException(e);
			}
        }

        private String generateFirstForm(List<String> params) {
            Date dp1 = DatePicker.getDate(params.get(1));
            Date dp2 = DatePicker.getDate(params.get(2));
            List<String> terms = new ArrayList<>();
            List<String> attrs = new ArrayList<>();
            List<String> conjunctions = new ArrayList<>();
            List<String> hedges = new ArrayList<>();
            
            attrs.add(params.get(3));
            terms.add(params.get(4));
            hedges.add(params.get(5));
            String quantifierChoice = params.get(6);
            
            for (int i = 7; i < params.size(); i += 4) {
            	attrs.add(params.get(i));
            	terms.add(params.get(i + 1));
            	hedges.add(params.get(i + 2));
            	conjunctions.add(params.get(i + 3));
            }

            return FirstForm.generate(
                repo,
                dp1,
                dp2,
                attrs,
                terms,
                hedges,
                conjunctions,
                quantifierChoice
            );
        }
        
        public String generateSecondForm(List<String> params) {
            Date dp1 = DatePicker.getDate(params.get(1));
            Date dp2 = DatePicker.getDate(params.get(2));
            
            String qualifierAttr = params.get(3);
            String qualifierTerm = params.get(4);
            String qualifierHedge = params.get(5);
            
            List<String> terms = new ArrayList<>();
            List<String> attrs = new ArrayList<>();
            List<String> conjunctions = new ArrayList<>();
            List<String> hedges = new ArrayList<>();
            
            attrs.add(params.get(6));
            terms.add(params.get(7));
            hedges.add(params.get(8));
            
            for (int i = 9; i < params.size(); i += 4) {
            	attrs.add(params.get(i));
            	terms.add(params.get(i + 1));
            	hedges.add(params.get(i + 2));
            	conjunctions.add(params.get(i + 3));
            }

            return SecondForm.generate(
                repo,
                dp1,
                dp2,
                qualifierAttr,
                qualifierTerm,
                qualifierHedge,
                attrs,
                terms,
                hedges,
                conjunctions
            );
        }

        private void writeSummary(String summary) {
            try {
                //FileWriter myWriter = new FileWriter(pathInput.getText(), true);
            	FileWriter myWriter = new FileWriter("C:/Users/Kaniak/Documents/test.txt", true);
                myWriter.write(summary);
                myWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
