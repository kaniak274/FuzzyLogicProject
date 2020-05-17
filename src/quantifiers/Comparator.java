package quantifiers;

import java.util.Map.Entry;

import terms.Term;

public class Comparator {
    public static int compare(Entry<Double, Term> term1, Entry<Double, Term> term2) {
        double t1 = term1.getKey();
        double t2 = term2.getKey();
        
        if (t1 > t2) {
            return 1;
        }
        
        if (t1 == t2) {
            return 0;
        }
        
        return -1;
    }
}
