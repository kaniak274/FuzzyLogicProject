package generators;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import db.Repository;
import db.Weather;
import degrees.OptimalSummary;
import fuzzy_set.FuzzySet;
import gui.exceptions.NotConvexException;
import gui.summary.AttributeToClass;
import gui.summary.Belongs;
import gui.summary.Conjunctions;
import hedges.PowerHedge;
import quantifiers.Matcher;
import quantifiers.QualifierMatcher;
import terms.Term;
import terms.TermData;

public class SecondForm {
    public static String generate(Repository repo, Date dp1, Date dp2, List<String> qualifierAttrs, List<String> qualifierTerms, List<String> qualifierHedges,
        List<String> attrs, List<String> terms, List<String> hedge, List<String> conjunctions, HashMap<String, List<String>> termsFromFile) {
        List<Weather> records = getDays(repo, dp1, dp2);
        List<TermData> data = new ArrayList<>();
        List<TermData> qualifier = new ArrayList<>();

        Term quantifier = null;
        double degree = 0.0;

        QualifierMatcher qualifierMatcher = Conjunctions.getQualifierMatcher(qualifierTerms, qualifierAttrs);

        for (int i = 0; i < qualifierTerms.size(); i++) {
            String attrChoice = qualifierAttrs.get(i);
            TermData term = CreateTerm.create(termsFromFile, attrChoice, qualifierTerms.get(i), records);

            try {
                isConvex(term, attrChoice);
            } catch (NotConvexException e) {
                return "Jeden z zbiorów rozmytych nie jest wypuk³y";
            }

            if (!term.getSet().isNormal()) {
            	term.setSet(normalizeSet(term.getSet()));
            }

            qualifier.add(term);
        }

        List<Integer> recordsToFilter = IntStream.range(0, qualifier.get(0).getSet().getFuzzySet().size())
            .filter(i -> qualifierMatcher.matcher(qualifier.get(0).getSet()
                .getFuzzySet()
                .get(i)
                .union(qualifier, i)
                .getMembership()))
            .boxed()
            .collect(Collectors.toList());

        List<Weather> filteredRecords = new ArrayList<>();

        for (int i = 0; i < records.size(); i++) {
            if (recordsToFilter.contains(i)) {
                filteredRecords.add(records.get(i));
            }
        }

        Matcher matcher = Conjunctions.getMatcher(conjunctions, terms, attrs);

    	for (int i = 0; i < terms.size(); i++) {
            String attrChoice = attrs.get(i);
            TermData term = CreateTerm.create(termsFromFile, attrChoice, terms.get(i), filteredRecords);

            try {
                isConvex(term, attrChoice);
            } catch (NotConvexException e) {
                return "Jeden z zbiorów rozmytych nie jest wypuk³y";
            }

            if (!term.getSet().isNormal()) {
                term.setSet(normalizeSet(term.getSet()));
            }

            data.add(term);
        }

        quantifier = Conjunctions.quantify(conjunctions, data, matcher);
        degree = OptimalSummary.calculateSecondForm(data, matcher, quantifier, qualifier, qualifierMatcher);

        String summary = quantifier.getLabel() + " dni które by³y ";

        for (int i = 0; i < qualifier.size(); i++) {
            if (i == 0) {
                summary += PowerHedge.toString(Double.parseDouble(qualifierHedges.get(0))) + qualifier.get(0).getTerm().DoubleFormLabel();
            } else {
                summary += " i " + PowerHedge.toString(Double.parseDouble(qualifierHedges.get(i))) + qualifier.get(i).getTerm().DoubleFormLabel();
            }
        }
        
        summary += " by³y równie¿ ";
        
        for (int i = 0; i < terms.size(); i++) {
            if (i == 0) {
                summary += PowerHedge.toString(Double.parseDouble(hedge.get(i))) + data.get(i).getTerm().getPluralLabel();
            } else {
                summary += Conjunctions.getConjuctionLabel(conjunctions.get(i - 1)) + PowerHedge.toString(Double.parseDouble(hedge.get(i))) + data.get(i).getTerm().getPluralLabel();	
            }
        }

        return summary + "\nWartoœæ podsumowania optymalnego: " + degree;
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
