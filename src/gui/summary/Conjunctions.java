package gui.summary;

import java.util.List;

import quantifiers.Matcher;
import quantifiers.RelativeQ;
import terms.Term;
import terms.TermData;

public class Conjunctions {
    public static Matcher getMatcher(List<String> conjunctions, List<String> terms, List<String> attrs) {
        return new Matcher() {
            @Override
            public boolean matcher(double membership) {
                boolean result = Belongs.belongsToTerm(attrs.get(0), terms.get(0), membership);

                if (terms.size() < 1) {
                    for (int i = 1; i < terms.size(); i++) {
                        result = result && Belongs.belongsToTerm(attrs.get(i), terms.get(i), membership); // TODO other conjunctions
                    }
                }
                
                return result;
            }
            
            public boolean matcher(double membership, double membership2) { throw new RuntimeException(); }
        };
    }
    
    public static Term quantify(List<String> conjunctions, List<TermData> attrs, Matcher matcher) {
        return RelativeQ.quantifyAnd(attrs, matcher);
    }
    
    public static String getConjuctionLabel(String conjuction) {
        if (conjuction.equals("I")) {
            return " i ";
        }

        if (conjuction.equals("Lub")) {
            return " lub " ;
        }
        
        if (conjuction.equals("I nie")) {
            return " i nie ";
        }

        return " lub nie ";
    }
}
