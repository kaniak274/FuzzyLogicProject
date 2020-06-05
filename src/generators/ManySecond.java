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
import gui.summary.Belongs;
import gui.summary.Conjunctions;
import hedges.PowerHedge;
import quantifiers.ManyRelativeQ;
import quantifiers.Matcher;
import quantifiers.QualifierMatcher;
import subjects.Subject;
import terms.Term;
import terms.TermData;

public class ManySecond {
    public static String generate(Repository repo, Subject sub1, Subject sub2, String qualifierAttr, String qualifierTerm, String qualifierHedge,
        List<String> attrs, List<String> terms, List<String> hedge, List<String> conjunctions, HashMap<String, List<String>> termsFromFile) {
        List<Weather> sub2Records = sub2.getAllRecords(repo);
        
        TermData qualifierData = CreateTerm.create(termsFromFile, qualifierAttr, qualifierTerm, sub2Records);
        QualifierMatcher qualifierMatcher = new QualifierMatcher() {
            @Override
            public boolean matcher(double membership) {
                return Belongs.belongsToTerm(qualifierAttr, qualifierTerm, membership);
            }
        };

        List<Integer> recordsToFilter = IntStream.range(0, qualifierData.getSet().getFuzzySet().size())
            .filter(i -> qualifierMatcher.matcher(qualifierData.getSet()
                .getFuzzySet()
                .get(i)
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
        	+ PowerHedge.toString(Double.parseDouble(qualifierHedge)) + qualifierData.getTerm().DoubleFormLabel() + ", by³o "
            + PowerHedge.toString(Double.parseDouble(hedge.get(0))) + data.get(0).getTerm().getPluralLabel();

        for (int i = 1; i < terms.size(); i++) {
            summary += Conjunctions.getConjuctionLabel(conjunctions.get(i - 1)) + PowerHedge.toString(Double.parseDouble(hedge.get(i))) + data.get(i).getTerm().getPluralLabel();
        }

        double degree = OptimalSummary.calculateManySecondForm(summarizer, matcher, quantifier, subs.get(0).getAllRecords(repo), subs.get(1).getAllRecords(repo), qualifierData);

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
