package generators;

import java.util.ArrayList;
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
import gui.summary.Conjunctions;
import hedges.PowerHedge;
import quantifiers.ManyRelativeQ;
import quantifiers.Matcher;
import quantifiers.QualifierMatcher;
import subjects.Subject;
import terms.Term;
import terms.TermData;

public class ManySecond {
    public static String generate(Repository repo, Subject sub1, Subject sub2, List<String> qualifierAttrs, List<String> qualifierTerms, List<String> qualifierHedges,
        List<String> attrs, List<String> terms, List<String> hedge, List<String> conjunctions, HashMap<String, List<String>> termsFromFile) {
        List<Weather> sub2Records = sub2.getAllRecords(repo);        
        List<TermData> qualifier = new ArrayList<>();

        QualifierMatcher qualifierMatcher = Conjunctions.getQualifierMatcher(qualifierTerms, qualifierAttrs);

        for (int i = 0; i < qualifierTerms.size(); i++) {
            String attrChoice = qualifierAttrs.get(i);
            TermData term = CreateTerm.create(termsFromFile, attrChoice, qualifierTerms.get(i), sub2Records);

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

        for (int i = 0; i < sub2Records.size(); i++) {
            if (recordsToFilter.contains(i)) {
                filteredRecords.add(sub2Records.get(i));
            }
        }
        
        List<Subject> subs = new ArrayList<>();
        subs.add(sub1);
        subs.add(sub2);

        List<Term> results = new ArrayList<>();
        List<TermData> data = new ArrayList<>();
        List<TermData> summarizer = new ArrayList<>();
        Matcher matcher = Conjunctions.getMatcher(conjunctions, terms, attrs);

        for (int j = 0; j < 2; j++) {
        	List<Weather> records = null;

        	if (j == 0) {
                records = subs.get(j).getAllRecords(repo);
        	} else {
        	    records = filteredRecords;
        	}

            data = new ArrayList<>();

            for (int i = 0; i < terms.size(); i++) {
                String attrChoice = attrs.get(i);
                TermData term = CreateTerm.create(termsFromFile, attrChoice, terms.get(i), records);

                try {
                    isConvex(term, attrChoice);
                } catch (NotConvexException e) {
                    return "Jeden z zbiorów rozmytych nie jest wypuk³y";
                }

                if (!term.getSet().isNormal()) {
                    term.setSet(normalizeSet(term.getSet()));
                }

                data.add(term);
                summarizer.add(term);
            }

            results.add(Conjunctions.quantify(conjunctions, data, matcher));
        }

        Term quantifier = ManyRelativeQ.quantify(results.get(0).getRelativeQ(), results.get(1).getRelativeQ());
        String summary = quantifier.getLabel() + sub1.getLabel() + "w porównaniu do tych" + sub2.getLabel() + "które by³y "
        	+ PowerHedge.toString(Double.parseDouble(qualifierHedges.get(0))) + qualifier.get(0).getTerm().DoubleFormLabel();
        
        for (int i = 1; i < qualifier.size(); i++) {
        	summary += " i " + PowerHedge.toString(Double.parseDouble(qualifierHedges.get(i))) + qualifier.get(i).getTerm().DoubleFormLabel();
        }

        summary += ", by³o " + PowerHedge.toString(Double.parseDouble(hedge.get(0))) + data.get(0).getTerm().getPluralLabel();

        for (int i = 1; i < terms.size(); i++) {
            summary += Conjunctions.getConjuctionLabel(conjunctions.get(i - 1)) + PowerHedge.toString(Double.parseDouble(hedge.get(i))) + data.get(i).getTerm().getPluralLabel();
        }

        double degree = OptimalSummary.calculateManySecondForm(summarizer, matcher, quantifier, subs.get(0).getAllRecords(repo),
            subs.get(1).getAllRecords(repo), qualifier, qualifierMatcher);

        return summary + "\nWartoœæ podsumowania optymalnego: " + degree;
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
