package gui.summary;

import java.util.List;

import quantifiers.Matcher;
import quantifiers.RelativeQ;
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
        /*if (conjuction.equals("I")) {
            return new Matcher() {
                @Override
                public boolean matcher(double membership) {
                    return Belongs.belongsToTerm(key, term, membership)
                        && Belongs.belongsToTerm(key2, term2, membership);
                }
                
                public boolean matcher(double membership, double membership2) { throw new RuntimeException(); }
            };
        }

        if (conjuction.equals("Lub")) {
            return new Matcher() {
                @Override
                public boolean matcher(double membership) {
                    return Belongs.belongsToTerm(key, term, membership)
                        || Belongs.belongsToTerm(key2, term2, membership);
                }
                
                public boolean matcher(double membership, double membership2) { throw new RuntimeException(); }
            };
        }

        if (conjuction.equals("I nie")) {
            return new Matcher() {
                @Override
                public boolean matcher(double membership) { throw new RuntimeException(); }
                
                public boolean matcher(double membership, double membership2) {
                    return Belongs.belongsToTerm(key, term, membership)
                        && !Belongs.belongsToTerm(key2, term2, membership2);
                }
            };
        }

        return new Matcher() {
            @Override
            public boolean matcher(double membership) { throw new RuntimeException(); }
            
            public boolean matcher(double membership, double membership2) {
                return Belongs.belongsToTerm(key, term, membership)
                    || !Belongs.belongsToTerm(key2, term2, membership2);
            }
        };*/
    }
    
    public static String quantify(List<String> conjunctions, List<TermData> attrs, Matcher matcher) {
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