package gui.summary;

import quantifiers.Matcher;
import quantifiers.RelativeQ;
import terms.TermData;

public class Conjuctions {
    public static Matcher getMatcher(String conjuction, String key, String term, String key2, String term2) {
        if (conjuction.equals("I")) {
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
        };
    }
    
    public static String quantify(String conjuction, TermData attr1, TermData attr2, Matcher matcher) {
        if (conjuction.equals("I")) {
            return RelativeQ.quantifyAnd(attr1.getSet(), attr2.getSet(), matcher);
        }

        if (conjuction.equals("Lub")) {
            return RelativeQ.quantifyOr(attr1.getSet(), attr2.getSet(), matcher);
        }

        return RelativeQ.quantifyNot(attr1.getSet(), attr2.getSet(), matcher);
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
