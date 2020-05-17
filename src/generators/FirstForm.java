package generators;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import db.Repository;
import db.Weather;
import fuzzy_set.FuzzySet;
import gui.summary.AttributeToClass;
import gui.summary.Belongs;
import gui.summary.Utils;
import hedges.PowerHedge;
import quantifiers.AbsoluteQ;
import quantifiers.Matcher;
import quantifiers.RelativeQ;
import quantifiers.Truth;
import terms.TermData;

public class FirstForm {
    public static String generate(Repository repo, Date dp1, Date dp2, String attrChoice, String termChoice, String quantifierChoice, double hedge) {
        List<Weather> records = repo.getDaysBetweenDates(dp1, dp2);

        if (attrChoice == null || termChoice == null || quantifierChoice == null) {
            return "Musisz wybraæ jeden z atrybutów powy¿ej";
        }

        AttributeToClass attr = new AttributeToClass();
        TermData term = attr.getTerm(records, attrChoice, termChoice); 
        
        String quantifier = "";
        double degreeOfTruth = 0.0;
        
        Matcher matcher = new Matcher() {
            @Override
            public boolean matcher(double membership) {
                return Belongs.belongsToTerm(attrChoice, termChoice, membership);
            }
            
            public boolean matcher(double membership, double membership2) { throw new RuntimeException(); }
        };
        
        if (quantifierChoice.equals("Absolutne")) {
            quantifier = AbsoluteQ.exactMatching(term.getSet(), matcher);
            degreeOfTruth = Truth.degreeOfTruthAbsolute(term.getSet(), matcher);
        } else {
            ArrayList<Double> universe = attr.getUniverse(attrChoice);

            if (!term.getSet().isConvex(universe.get(0), universe.get(1), term.getMembership())) {
                return "Zbiór rozmyty nie jest wypuk³y";
            }

            if (!term.getSet().isNormal()) {
               term.setSet(normalizeSet(term.getSet()));
            }

            quantifier = RelativeQ.quantifySingle(term.getSet(), matcher);
            degreeOfTruth = Truth.degreeOfTruthRelative(term.getSet(), matcher);
            degreeOfTruth = RelativeQ.matchTruth(degreeOfTruth);
        }
        
        return quantifier + Utils.getPluralSubject(true) + PowerHedge.toString(hedge) + term.getTerm().getPluralLabel() + "\nPrawdziwoœæ: " + degreeOfTruth;
    }
    
    private static FuzzySet normalizeSet(FuzzySet set) {
        return new FuzzySet(set.normalize(set.getStreamOfSet()).collect(Collectors.toList()));
    }
}
