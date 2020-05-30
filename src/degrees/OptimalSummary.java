package degrees;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import quantifiers.Matcher;
import quantifiers.RelativeQ;
import quantifiers.Truth;
import terms.Term;
import terms.TermData;

public class OptimalSummary {
    private static int n = 5;
	
    public static double calculateFirstFormRelative(List<TermData> summarizer, Matcher matcher, Term quantifier) {
        List<Double> weights = Weights.getWeights(n);
        List<Double> degrees = new ArrayList<>();

        degrees.add(RelativeQ.matchTruth(Truth.degreeOfTruthRelative(summarizer, matcher))); // T1
        degrees.add(Imprecision.calculate(summarizer)); // T2
        degrees.add(Covering.calculate(summarizer, matcher)); // T3
        degrees.add(Appropriateness.calculate(summarizer, degrees.get(2))); // T4
        degrees.add(Length.calculate(summarizer)); // T5
        degrees.add(QuantifierImprecision.calculate(quantifier)); // T6
        degrees.add(QuantifierCardinality.calculate(quantifier.getRelativeQ())); // T7
        degrees.add(SummarizerCardinality.calculate(summarizer)); // T8
        degrees.add(1.00); // T9
        degrees.add(1.00); // T10
        degrees.add(1.00); // T11
        
        return IntStream.range(0, n).mapToDouble(el -> el).reduce(0.0, (acc, value) -> acc + (weights.get((int)value) * degrees.get((int)value)));
    }
    
    public static double calculateFirstFormAbsolute(List<TermData> summarizer, Matcher matcher, double matchingElements) {
        List<Double> weights = Weights.getWeights(n);
        List<Double> degrees = new ArrayList<>();

        degrees.add(Truth.degreeOfTruthAbsolute()); // T1
        degrees.add(Imprecision.calculate(summarizer)); // T2
        degrees.add(Covering.calculate(summarizer, matcher)); // T3
        degrees.add(Appropriateness.calculate(summarizer, degrees.get(2))); // T4
        degrees.add(Length.calculate(summarizer)); // T5
        degrees.add(QuantifierImprecision.calculate(matchingElements, summarizer.get(0).getSet().getFuzzySet().size())); // T6
        degrees.add(QuantifierCardinality.calculate(matchingElements, summarizer.get(0).getSet().getFuzzySet().size())); // T7
        degrees.add(SummarizerCardinality.calculate(summarizer)); // T8
        degrees.add(1.00); // T9
        degrees.add(1.00); // T10
        degrees.add(1.00); // T11
        
        return IntStream.range(0, n).mapToDouble(el -> el).reduce(0.0, (acc, value) -> acc + (weights.get((int)value) * degrees.get((int)value)));
    }
}
