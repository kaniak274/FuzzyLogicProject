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
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;

import db.Repository;
import generators.FirstForm;
import generators.ManyFirst;
import generators.ManyFourth;
import generators.ManySecond;
import generators.ManyThird;
import generators.SecondForm;
import gui.date_picker.DatePicker;
import subjects.Autumn;
import subjects.Spring;
import subjects.Subject;
import subjects.Summer;
import subjects.Winter;

public class FromFile {
    Repository repo;
    JPanel panel;
    JTextField pathInput;

    JFileChooser fc = new JFileChooser();
    File file;
    File newFile;

    final static String FIRSTFORM = "F";
    final static String SECONDFORM = "S";
    final static String MANYFIRST = "MF";
    final static String MANYSECOND = "MS";
    final static String MANYTHIRD = "MT";
    final static String MANYFOURTH = "MFT";

    final static String WINTER = "W";
    final static String SUMMER = "SU";
    final static String SPRING = "SP";
    final static String AUTUMN = "A";

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
            HashMap<String, List<String>> terms = getTerms();

            while (scanner.hasNextLine()) {
                List<String> params = Arrays.asList(scanner.nextLine().split(","));
                String type = params.get(0);
                String summary = "";

                if (type.equals(FIRSTFORM)) {
                    summary = generateFirstForm(params, terms);
                } else if (type.equals(SECONDFORM)) {
                    summary = generateSecondForm(params, terms);
                } else if (type.equals(MANYFIRST)) {
                    summary = generateManyFirst(params, terms);
                } else if (type.equals(MANYSECOND)) {
                    summary = generateManySecond(params, terms);
                } else if (type.equals(MANYTHIRD)) {
                    summary = generateManyThird(params, terms);
                } else if (type.equals(MANYFOURTH)) {
                    summary = generateManyFourth(params, terms);
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
        
        private HashMap<String, List<String>> getTerms() {
            HashMap<String, List<String>> terms = new HashMap<>();
            
            try {
                File termsFile = new File("C:/Users/Kaniak/Documents/newTerms.txt");
                Scanner scanner = new Scanner(termsFile);
                
                while (scanner.hasNextLine()) {
                    String data = scanner.nextLine();
                    
                    List<String> term = Arrays.asList(data.split(","));
                    
                    if (term.get(1).equals("Trapezoid")) {
                        terms.put(term.get(6), term);
                    } else {
                        terms.put(term.get(5), term);
                    }
                }
                
                scanner.close();
            } catch(FileNotFoundException e) {
                e.printStackTrace();
            }
            
            return terms;
        }

        private String generateFirstForm(List<String> params, HashMap<String, List<String>> termsFromFile) {
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
                quantifierChoice,
                termsFromFile
            );
        }
        
        public String generateSecondForm(List<String> params, HashMap<String, List<String>> termsFromFile) {
            Date dp1 = DatePicker.getDate(params.get(1));
            Date dp2 = DatePicker.getDate(params.get(2));
            
            List<String> qualifierAttrs = new ArrayList<>();
            List<String> qualifierTerms = new ArrayList<>();
            List<String> qualifierHedges = new ArrayList<>();
            
            qualifierAttrs.add(params.get(3));
            qualifierTerms.add(params.get(4));
            qualifierHedges.add(params.get(5));

            for (int i = 6;; i++) {
                if (params.get(i).equals("S")) {
                    break;
                }
                
                qualifierAttrs.add(params.get(i));
                qualifierTerms.add(params.get(i + 1));
                qualifierHedges.add(params.get(i + 2));
            }
            
            int summarizerStartPoint = qualifierAttrs.size() * 3 + 4;

            List<String> terms = new ArrayList<>();
            List<String> attrs = new ArrayList<>();
            List<String> conjunctions = new ArrayList<>();
            List<String> hedges = new ArrayList<>();
            
            attrs.add(params.get(summarizerStartPoint));
            terms.add(params.get(summarizerStartPoint + 1));
            hedges.add(params.get(summarizerStartPoint + 2));
            
            for (int i = summarizerStartPoint + 3; i < params.size(); i += 4) {
                attrs.add(params.get(i));
                terms.add(params.get(i + 1));
                hedges.add(params.get(i + 2));
                conjunctions.add(params.get(i + 3));
            }

            return SecondForm.generate(
                repo,
                dp1,
                dp2,
                qualifierAttrs,
                qualifierTerms,
                qualifierHedges,
                attrs,
                terms,
                hedges,
                conjunctions,
                termsFromFile
            );
        }
        
        public String generateManyFirst(List<String> params, HashMap<String, List<String>> termsFromFile) {
            String season = params.get(1);
            String season2 = params.get(2);
            
            List<String> terms = new ArrayList<>();
            List<String> attrs = new ArrayList<>();
            List<String> conjunctions = new ArrayList<>();
            List<String> hedges = new ArrayList<>();
            
            attrs.add(params.get(3));
            terms.add(params.get(4));
            hedges.add(params.get(5));
            
            for (int i = 6; i < params.size(); i += 4) {
                attrs.add(params.get(i));
                terms.add(params.get(i + 1));
                hedges.add(params.get(i + 2));
                conjunctions.add(params.get(i + 3));
            }
            
            return ManyFirst.generate(
                repo,
                getSeason(season),
                getSeason(season2),
                attrs,
                terms,
                hedges,
                conjunctions,
                termsFromFile
            );
        }
        
        public String generateManySecond(List<String> params, HashMap<String, List<String>> termsFromFile) {
            String season = params.get(1);
            String season2 = params.get(2);
            
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
            
            return ManySecond.generate(
                repo,
                getSeason(season),
                getSeason(season2),
                qualifierAttr,
                qualifierTerm,
                qualifierHedge,
                attrs,
                terms,
                hedges,
                conjunctions,
                termsFromFile
            );
        }
        
        public String generateManyThird(List<String> params, HashMap<String, List<String>> termsFromFile) {
            String season = params.get(1);
            String season2 = params.get(2);
            
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
            
            return ManyThird.generate(
                repo,
                getSeason(season),
                getSeason(season2),
                qualifierAttr,
                qualifierTerm,
                qualifierHedge,
                attrs,
                terms,
                hedges,
                conjunctions,
                termsFromFile
            );
        }
        
        public String generateManyFourth(List<String> params, HashMap<String, List<String>> termsFromFile) {
            String season = params.get(1);
            String season2 = params.get(2);
            
            List<String> terms = new ArrayList<>();
            List<String> attrs = new ArrayList<>();
            List<String> conjunctions = new ArrayList<>();
            List<String> hedges = new ArrayList<>();
            
            attrs.add(params.get(3));
            terms.add(params.get(4));
            hedges.add(params.get(5));
            
            for (int i = 6; i < params.size(); i += 4) {
                attrs.add(params.get(i));
                terms.add(params.get(i + 1));
                hedges.add(params.get(i + 2));
                conjunctions.add(params.get(i + 3));
            }
            
            return ManyFourth.generate(
                repo,
                getSeason(season),
                getSeason(season2),
                attrs,
                terms,
                hedges,
                conjunctions,
                termsFromFile
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
        
        private Subject getSeason(String season) {
            if (season.equals(WINTER)) {
                return new Winter();
            }
            
            if (season.equals(SPRING)) {
                return new Spring();
            }
            
            if (season.equals(AUTUMN)) {
            	return new Autumn();
            }
            
            return new Summer();
        }
    }
}
