package gui.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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
            String attribute = params.get(3);
            String term = params.get(4);  // TODO
            String quantifier = params.get(5);
            Double hedge = Double.parseDouble(params.get(6));

            return FirstForm.generate(
                repo,
                dp1,
                dp2,
                attribute,
                term,
                quantifier,
                hedge
            );
        }

        private String generateSecondForm(List<String> params) {
            Date dp1 = DatePicker.getDate(params.get(1));
            Date dp2 = DatePicker.getDate(params.get(2));
            String attribute = params.get(3);
            String term = params.get(4);  // TODO
            Double hedge = Double.parseDouble(params.get(5));
            String attribute2 = params.get(6);
            String term2 = params.get(7);
            Double hedge2 = Double.parseDouble(params.get(8));
            String conjuction = params.get(9);

            return SecondForm.generate(
                repo,
                dp1,
                dp2,
                attribute,
                term,
                hedge,
                attribute2,
                term2,
                hedge2,
                conjuction
            );
        }

        private void writeSummary(String summary) {
            try {
                FileWriter myWriter = new FileWriter(pathInput.getText(), true);//
                myWriter.write(summary);
                myWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
