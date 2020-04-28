package main;

import java.util.ArrayList;

import db.Repository;
import fuzzy_set.FuzzyElement;
import fuzzy_set.FuzzySet;
import memberships.Gauss;
import memberships.Trapezoid;
import memberships.Triangle;
import terms.Term;

public class Main {
    public static void main(String[] args) {
        //Repository repo = new Repository();	
        //System.out.println(repo.getAllObjects().get(0).getTemperature());
    	
        ArrayList<Double> scope = new ArrayList<>();
        scope.add(20.00);
        scope.add(70.00);
        scope.add(70.00);
        scope.add(70.00);
    	
        Term term = new Term("ciep³o", scope);
        Trapezoid membership = new Trapezoid(term);
    	
        ArrayList<FuzzyElement> elems = new ArrayList<>();
    	
        for (int i = 0; i <= 36; i++) {
            elems.add(new FuzzyElement().saveMembership(i, membership));
        }
    	
        FuzzySet set = new FuzzySet(elems);
    	
        System.out.println(set.isConvex(0, 4, membership));
    }
}
