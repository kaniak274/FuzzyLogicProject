package degrees;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import quantifiers.Matcher;
import quantifiers.Truth;
import terms.TermData;

public class OptimalSummary {
	private static int n = 5;
	
    public static double calculateFirstFormRelative(List<TermData> summarizer, Matcher matcher) {
        List<Double> weights = Weights.getWeights(n);
        List<Double> degrees = new ArrayList<>();

        degrees.add(Truth.degreeOfTruthRelative(summarizer, matcher)); // T1
        degrees.add(Imprecision.calculate(summarizer)); // T2
        degrees.add(Covering.calculate(summarizer, matcher)); // T3
        degrees.add(Appropriateness.calculate(summarizer, degrees.get(2))); // T4
        degrees.add(Length.calculate(summarizer)); // T5
        
        return IntStream.range(0, n).mapToDouble(el -> el).reduce(0.0, (acc, value) -> acc + (weights.get((int)value) * degrees.get((int)value)));
    }
    
    public static double calculateFirstFormAbsolute(List<TermData> summarizer, Matcher matcher) {
        List<Double> weights = Weights.getWeights(n);
        List<Double> degrees = new ArrayList<>();

        degrees.add(Truth.degreeOfTruthAbsolute()); // T1
        degrees.add(Imprecision.calculate(summarizer)); // T2
        degrees.add(Covering.calculate(summarizer, matcher)); // T3
        degrees.add(Appropriateness.calculate(summarizer, degrees.get(2))); // T4
        degrees.add(Length.calculate(summarizer)); // T5
        
        return IntStream.range(0, n).mapToDouble(el -> el).reduce(0.0, (acc, value) -> acc + (weights.get((int)value) * degrees.get((int)value)));
    }
}
