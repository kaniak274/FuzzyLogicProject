package generators;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import db.Repository;
import db.Weather;
import degrees.OptimalSummary;
import fuzzy_set.FuzzySet;
import gui.exceptions.NotConvexException;
import gui.summary.AttributeToClass;
import gui.summary.Conjunctions;
import gui.summary.Utils;
import hedges.PowerHedge;
import quantifiers.AbsoluteQ;
import quantifiers.Matcher;
import terms.Term;
import terms.TermData;

public class FirstForm {
    public static String generate(Repository repo, Date dp1, Date dp2,
        List<String> attrs, List<String> terms, List<String> hedge, List<String> conjunctions, String quantifierChoice, HashMap<String, List<String>> termsFromFile) {
        List<Weather> records = getDays(repo, dp1, dp2);
        List<TermData> data = new ArrayList<>();
        
        Term quantifier = null;
        double degree = 0.0;

        Matcher matcher = Conjunctions.getMatcher(conjunctions, terms, attrs);
        
        if (quantifierChoice.equals("Absolutne")) {
            for (int i = 0; i < terms.size(); i++) {
                String attrChoice = attrs.get(i);
                TermData term = CreateTerm.create(termsFromFile, attrChoice, terms.get(i), records);
            	
                data.add(term);
            }

            quantifier = AbsoluteQ.exactMatching(data, matcher);
            degree = OptimalSummary.calculateFirstFormAbsolute(data, matcher, quantifier.getScope().get(0));
        } else {
            for (int i = 0; i < terms.size(); i++) {
                String attrChoice = attrs.get(i);
                TermData term = CreateTerm.create(termsFromFile, attrChoice, terms.get(i), records);
            	
                try {
                    isConvex(term, attrChoice);
                } catch (NotConvexException e) {
                    return "Jeden z zbior�w rozmytych nie jest wypuk�y";
                }
            	
                if (!term.getSet().isNormal()) {
                    term.setSet(normalizeSet(term.getSet()));
                }
            	
                data.add(term);
            }
        	
            quantifier = Conjunctions.quantify(conjunctions, data, matcher);
            degree = OptimalSummary.calculateFirstFormRelative(data, matcher, quantifier);
        }
        
        String summary = quantifier.getLabel() + Utils.getPluralSubject(true) + PowerHedge.toString(Double.parseDouble(hedge.get(0))) + data.get(0).getTerm().getPluralLabel();
        
        for (int i = 1; i < terms.size(); i++) {
            summary += Conjunctions.getConjuctionLabel(conjunctions.get(i - 1)) + PowerHedge.toString(Double.parseDouble(hedge.get(i))) + data.get(i).getTerm().getPluralLabel();
        }
        
        return summary + "\nWarto�� podsumowania optymalnego: " + degree;
    }

    private static List<Weather> getDays(Repository repo, Date dp1, Date dp2) {
        return repo.getDaysBetweenDates(dp1, dp2);
    }

    private static void isConvex(TermData attr, String key) throws NotConvexException {
        ArrayList<Double> universe = getAttributeUniverse(key);
    	
        if (!attr.getSet().isConvex(universe.get(0), universe.get(1), attr.getMembership())) {
            throw new NotConvexException();
        }
    }

    private static ArrayList<Double> getAttributeUniverse(String attribute) {
        return new AttributeToClass().getUniverse(attribute);
    }

    private static FuzzySet normalizeSet(FuzzySet set) {
        return new FuzzySet(set.normalize(set.getStreamOfSet()).collect(Collectors.toList()));
    }
}
