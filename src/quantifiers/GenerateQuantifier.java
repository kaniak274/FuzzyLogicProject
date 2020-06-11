package quantifiers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Map.Entry;

import memberships.Gauss;
import memberships.Trapezoid;
import memberships.Triangle;
import terms.Term;

public class GenerateQuantifier {
    public static List<Entry<Double, Term>> generate(double average, String label) {
        List<Entry<Double, Term>> quantifiers = new ArrayList<>();
	    
        try {
            File termsFile = new File("C:/Users/Kaniak/Documents/newTerms.txt");
            Scanner scanner = new Scanner(termsFile);
            
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                
                List<String> term = Arrays.asList(data.split(","));
                
                if (term.get(0).equals("F")) {
                    if (term.get(2).equals("Trapezoid")) {
                        if (!term.get(7).equals(label)) {
                            continue;
                        }
                    } else {
                        if (!term.get(6).equals(label)) {
                            continue;
                        }
                    }

                    continue;
                }
                
                quantifiers.add(createMembership(term, average));
            }
            
            scanner.close();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }
	    
	    return quantifiers;
    }
    
    private static Entry<Double, Term> createMembership(List<String> data, double average) {    	                
        if (data.get(1).equals("Trapezoid")) {
            ArrayList<Double> scope = new ArrayList<>();

            scope.add(Double.parseDouble(data.get(3)));
            scope.add(Double.parseDouble(data.get(4)));
            scope.add(Double.parseDouble(data.get(5)));
            scope.add(Double.parseDouble(data.get(6)));
            
            Term term = new Term(data.get(7), scope, "", average);
            Trapezoid membership = new Trapezoid(term);

            return new AbstractMap.SimpleEntry<Double, Term>(membership.get_membership(average), term);
        }
        
        if (data.get(1).equals("Trójk¹tna")) {
            ArrayList<Double> scope = new ArrayList<>();

            scope.add(Double.parseDouble(data.get(3)));
            scope.add(Double.parseDouble(data.get(4)));
            scope.add(Double.parseDouble(data.get(5)));
            
            Term term = new Term(data.get(6), scope, "", average);
            Triangle membership = new Triangle(term);

            return new AbstractMap.SimpleEntry<Double, Term>(membership.get_membership(average), term);
        }
        
        ArrayList<Double> scope = new ArrayList<>();

        scope.add(Double.parseDouble(data.get(3)));
        scope.add(Double.parseDouble(data.get(4)));
        scope.add(Double.parseDouble(data.get(5)));
        
        Term term = new Term(data.get(6), scope, "", average);
        Gauss membership = new Gauss(term);

        return new AbstractMap.SimpleEntry<Double, Term>(membership.get_membership(average), term);
    }
}
