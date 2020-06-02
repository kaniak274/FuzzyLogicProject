package quantifiers;

import java.util.List;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

import db.Weather;
import fuzzy_set.FuzzySet;
import terms.TermData;

public class Truth {
    public static double degreeOfTruthRelative(List<TermData> sets, Matcher matcher) {
    	FuzzySet set = sets.get(0).getSet();

        DoubleStream stream = getMatchingUnits(set, sets, matcher);
        return getSigmaCount(stream) / getSigmaCount(set.getStreamOfSet().mapToDouble(el -> el.getMembership()));
    }

    public static double degreeOfTruthAbsolute() {
        return 1.0;
    }
    
    public static double degreeOfTruthManyFirst(List<TermData> sets, List<TermData> sets2, List<Weather> sub1Records, List<Weather> sub2Records, Matcher matcher) {
        FuzzySet set = sets.get(0).getSet();
        double sigmaCount1 = getSigmaCount(getMatchingUnits(set, sets, matcher));
        double product = sigmaCount1 / sub1Records.size();

        FuzzySet set2 = sets2.get(0).getSet();
        double sigmaCount2 = getSigmaCount(getMatchingUnits(set2, sets2, matcher));
        double product2 = sigmaCount2 / sub2Records.size();
        
        double productsSum = product + product2;
        
        return product / productsSum;
    }
    
    public static double degreeOfTruthManySecond(List<TermData> sets, List<TermData> sets2,
        List<Weather> sub1Records, List<Weather> sub2Records, Matcher matcher, TermData qualifier) {
        FuzzySet qSet = qualifier.getSet();
    	
        FuzzySet set = sets.get(0).getSet();
        double sigmaCount1 = getSigmaCount(getMatchingUnits(set, sets, matcher));
        double product = sigmaCount1 / sub1Records.size();

        FuzzySet set2 = sets2.get(0).getSet();
        double sigmaCount2 = getSigmaCount(getMatchingUnitsWithQualifier(set2, sets2, matcher, qSet));
        double product2 = sigmaCount2 / sub2Records.size();
        
        double productsSum = product + product2;
        
        return product / productsSum;
    }
    
    public static double degreeOfTruthManyThird(List<TermData> sets, List<TermData> sets2,
        List<Weather> sub1Records, List<Weather> sub2Records, Matcher matcher, TermData qualifier) {
        FuzzySet qSet = qualifier.getSet();
    	
        FuzzySet set = sets.get(0).getSet();
        double sigmaCount1 = getSigmaCount(getMatchingUnitsWithQualifier(set, sets, matcher, qSet));
        double product = sigmaCount1 / sub1Records.size();

        FuzzySet set2 = sets2.get(0).getSet();
        double sigmaCount2 = getSigmaCount(getMatchingUnits(set2, sets2, matcher));
        double product2 = sigmaCount2 / sub2Records.size();
        
        double productsSum = product + product2;
        
        return product / productsSum;
    }
    
    public static double degreeOfTruthManyFourth(List<TermData> sets, List<TermData> sets2, Matcher matcher) {
        FuzzySet set = sets.get(0).getSet();
        double sum1 = getMatchingUnits(set, sets, matcher).count();

        FuzzySet set2 = sets2.get(0).getSet();
        double sum2 = getMatchingUnits(set2, sets2, matcher).count();

        if (sum1 >= sum2) {
            return 1 - (sum2 / sum1);
        } else {
            return 1 - (sum1 / sum2);
        }
    }

    private static DoubleStream getMatchingUnits(FuzzySet set, List<TermData> sets, Matcher matcher) {
        return IntStream.range(0, set.getFuzzySet().size())
            .mapToDouble(i -> set.getFuzzySet().get(i).intersect(sets, i).getMembership())
            .filter(membership -> matcher.matcher(membership));
    }
    
    private static DoubleStream getMatchingUnitsWithQualifier(FuzzySet set, List<TermData> sets, Matcher matcher, FuzzySet qualifierSet) {
        return IntStream.range(0, set.getFuzzySet().size())
            .mapToDouble(i -> decideWhich(set.getFuzzySet().get(i).intersect(sets, i).getMembership(), qualifierSet.getFuzzySet().get(i).getMembership()))
            .filter(membership -> matcher.matcher(membership));
    }
    
    private static double decideWhich(double membership, double qualifierMembership) {
        if (membership >= qualifierMembership) {
            return membership;
        } else {
        	return qualifierMembership;
        }
    }

    private static double getSigmaCount(DoubleStream stream) {
    	return new FuzzySet().getSigmaCount(stream);
    }
}
