package generators;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import db.Repository;
import db.Weather;
import fuzzy_set.FuzzySet;
import gui.exceptions.NotConvexException;
import gui.summary.AttributeToClass;
import gui.summary.Conjunctions;
import hedges.PowerHedge;
import quantifiers.ManyRelativeQ;
import quantifiers.Matcher;
import subjects.Subject;
import terms.Term;
import terms.TermData;

public class ManyFirst {
    public static String generate(Repository repo, Subject sub1, Subject sub2,
        List<String> attrs, List<String> terms, List<String> hedge, List<String> conjunctions) {
        List<Subject> subs = new ArrayList<>();
        subs.add(sub1);
        subs.add(sub2);

        List<Term> results = new ArrayList<>();
        List<TermData> data = new ArrayList<>();
        Matcher matcher = Conjunctions.getMatcher(conjunctions, terms, attrs);

        for (int j = 0; j < 2; j++) {
            List<Weather> records = subs.get(j).getAllRecords(repo);
            data = new ArrayList<>();

            for (int i = 0; i < terms.size(); i++) {
                String attrChoice = attrs.get(i);
                TermData term = getTermForAttribute(records, attrChoice, terms.get(i));

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

            results.add(Conjunctions.quantify(conjunctions, data, matcher));
        }

        Term quantifier = ManyRelativeQ.quantify(results.get(0).getRelativeQ(), results.get(1).getRelativeQ());
        String summary = quantifier.getLabel() + sub1.getLabel() + "w porównaniu do" + sub2.getLabel() + "by³a "
            + PowerHedge.toString(Double.parseDouble(hedge.get(0))) + data.get(0).getTerm().getPluralLabel();

        for (int i = 1; i < terms.size(); i++) {
            summary += Conjunctions.getConjuctionLabel(conjunctions.get(i - 1)) + PowerHedge.toString(Double.parseDouble(hedge.get(i))) + data.get(i).getTerm().getPluralLabel();
        }

        // TODO DEGREES
        return summary;
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
