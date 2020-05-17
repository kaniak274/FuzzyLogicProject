package generators;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import db.Repository;
import db.Weather;
import fuzzy_set.FuzzySet;
import gui.exceptions.NotConvexException;
import gui.summary.AttributeToClass;
import gui.summary.Conjuctions;
import gui.summary.Utils;
import hedges.PowerHedge;
import quantifiers.Matcher;
import quantifiers.RelativeQ;
import quantifiers.Truth;
import terms.TermData;

public class SecondForm {
    public static String generate(Repository repo, Date dp1, Date dp2, String attrChoice, String termChoice, double hedge,
        String attr2Choice, String term2Choice, double hedge2, String conjuction) {
        List<Weather> records = getDays(repo, dp1, dp2);

        if (termChoice == null || term2Choice == null) {
            return "Musisz wybraæ oba atrybuty";
        }
        
        if (attrChoice.equals(attr2Choice)) {
            return "Nie mo¿esz wybraæ dwóch takich samych atrybutów";
        }
        
        TermData term1 = getTermForAttribute(records, attrChoice, termChoice);
        TermData term2 = getTermForAttribute(records, attr2Choice, term2Choice);
        
        String quantifier = "";
        double degreeOfTruth = 0.0;
        
        try {
            isConvex(term1, attrChoice);
            isConvex(term2, attr2Choice);
        } catch (NotConvexException e) {
            return "Jeden z zbiorów rozmytych nie jest wypuk³y";
        }

        if (!term1.getSet().isNormal()) {
           term1.setSet(normalizeSet(term1.getSet()));
        }
        
        if (!term2.getSet().isNormal()) {
            term2.setSet(normalizeSet(term2.getSet()));
        }
        
        Matcher matcher = Conjuctions.getMatcher(conjuction, attrChoice, termChoice, attr2Choice, term2Choice);
        quantifier = Conjuctions.quantify(conjuction, term1, term2, matcher);
        degreeOfTruth = Truth.degreeOfTruthRelative(term1.getSet(), term2.getSet(), matcher);
        degreeOfTruth = RelativeQ.matchTruth(degreeOfTruth);
        
        return quantifier + Utils.getPluralSubject(true) + PowerHedge.toString(hedge) + term1.getTerm().getPluralLabel() + Conjuctions.getConjuctionLabel(conjuction)
            + PowerHedge.toString(hedge2) + term2.getTerm().getPluralLabel() + "\nPrawdziwoœæ: " + degreeOfTruth;
    }


    private static List<Weather> getDays(Repository repo, Date dp1, Date dp2) {
        return repo.getDaysBetweenDates(dp1, dp2);
    }
    
    private static TermData getTermForAttribute(List<Weather> records, String attribute, String term) {
        AttributeToClass attr = new AttributeToClass();
        return attr.getTerm(records, attribute, term); 
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
