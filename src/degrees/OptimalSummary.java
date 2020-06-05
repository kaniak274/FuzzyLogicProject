package degrees;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import com.google.common.collect.Lists;

import db.Weather;
import quantifiers.ManyRelativeQ;
import quantifiers.Matcher;
import quantifiers.RelativeQ;
import quantifiers.Truth;
import terms.Term;
import terms.TermData;

public class OptimalSummary {
    private static int n = 11;

    public static double calculateFirstFormRelative(List<TermData> summarizer, Matcher matcher, Term quantifier) {
        List<Double> weights = Weights.getWeights(n);
        List<Double> degrees = new ArrayList<>();

        degrees.add(RelativeQ.matchTruth(Truth.degreeOfTruthRelativeFirstForm(summarizer, matcher))); // T1
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
        
        System.out.println(degrees);
        
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
        
        System.out.println(degrees);
        
        return IntStream.range(0, n).mapToDouble(el -> el).reduce(0.0, (acc, value) -> acc + (weights.get((int)value) * degrees.get((int)value)));
    }
    
    public static double calculateSecondForm(List<TermData> summarizer, Matcher matcher, Term quantifier, TermData qualifier) {
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
        degrees.add(QualifierImprecision.calculate(qualifier)); // T9
        degrees.add(QualifierCardinality.calculate(qualifier)); // T10
        degrees.add(QualifierLength.calculate()); // T11
        
        System.out.println(degrees);
        
        return IntStream.range(0, n).mapToDouble(el -> el).reduce(0.0, (acc, value) -> acc + (weights.get((int)value) * degrees.get((int)value)));
    }
    
    public static double calculateManyFirstForm(List<TermData> summarizer, Matcher matcher, Term quantifier, List<Weather> sub1Records, List<Weather> sub2Records) {
        List<Double> weights = Weights.getWeights(n);
        List<Double> degrees = new ArrayList<>();
        List<List<TermData>> sets = Lists.partition(summarizer, summarizer.size() / 2);

        degrees.add(ManyRelativeQ.matchTruth(Truth.degreeOfTruthManyFirst(sets.get(0), sets.get(1), sub1Records, sub2Records, matcher))); // T1
        degrees.add(Imprecision.calculate(sets.get(0))); // T2
        degrees.add(Covering.calculate(sets.get(0), matcher)); // T3
        degrees.add(Appropriateness.calculate(sets.get(0), degrees.get(2))); // T4
        degrees.add(Length.calculate(sets.get(0))); // T5
        degrees.add(QuantifierImprecision.calculate(quantifier)); // T6
        degrees.add(QuantifierCardinality.calculate(quantifier.getRelativeQ())); // T7
        degrees.add(SummarizerCardinality.calculate(sets.get(0))); // T8
        degrees.add(1.00); // T9
        degrees.add(1.00); // T10
        degrees.add(1.00); // T11
        
        System.out.println(degrees);

        return IntStream.range(0, n).mapToDouble(el -> el).reduce(0.0, (acc, value) -> acc + (weights.get((int)value) * degrees.get((int)value)));
    }
    
    public static double calculateManySecondForm(List<TermData> data, Matcher matcher,
        Term quantifier, List<Weather> sub1Records, List<Weather> sub2Records, TermData qualifier) {
        List<Double> weights = Weights.getWeights(n);
        List<Double> degrees = new ArrayList<>();
        List<List<TermData>> sets = Lists.partition(data, data.size() / 2);
        List<TermData> summarizer = sets.get(0);

        degrees.add(ManyRelativeQ.matchTruth(Truth.degreeOfTruthManySecond(sets.get(0), sets.get(1), sub1Records, sub2Records, matcher, qualifier))); // T1
        degrees.add(Imprecision.calculate(summarizer)); // T2
        degrees.add(Covering.calculate(summarizer, matcher)); // T3
        degrees.add(Appropriateness.calculate(summarizer, degrees.get(2))); // T4
        degrees.add(Length.calculate(summarizer)); // T5
        degrees.add(QuantifierImprecision.calculate(quantifier)); // T6
        degrees.add(QuantifierCardinality.calculate(quantifier.getRelativeQ())); // T7
        degrees.add(SummarizerCardinality.calculate(summarizer)); // T8
        degrees.add(QualifierImprecision.calculate(qualifier)); // T9
        degrees.add(QualifierCardinality.calculate(qualifier)); // T10
        degrees.add(QualifierLength.calculate()); // T11
        
        System.out.println(degrees);

        return IntStream.range(0, n).mapToDouble(el -> el).reduce(0.0, (acc, value) -> acc + (weights.get((int)value) * degrees.get((int)value)));
    }
    
    public static double calculateManyThirdForm(List<TermData> data, Matcher matcher,
        Term quantifier, List<Weather> sub1Records, List<Weather> sub2Records, TermData qualifier) {
        List<Double> weights = Weights.getWeights(n);
        List<Double> degrees = new ArrayList<>();
        List<List<TermData>> sets = Lists.partition(data, data.size() / 2);
        List<TermData> summarizer = sets.get(0);

        degrees.add(ManyRelativeQ.matchTruth(Truth.degreeOfTruthManyThird(sets.get(0), sets.get(1), sub1Records, sub2Records, matcher, qualifier))); // T1
        degrees.add(Imprecision.calculate(summarizer)); // T2
        degrees.add(Covering.calculate(summarizer, matcher)); // T3
        degrees.add(Appropriateness.calculate(summarizer, degrees.get(2))); // T4
        degrees.add(Length.calculate(summarizer)); // T5
        degrees.add(QuantifierImprecision.calculate(quantifier)); // T6
        degrees.add(QuantifierCardinality.calculate(quantifier.getRelativeQ())); // T7
        degrees.add(SummarizerCardinality.calculate(summarizer)); // T8
        degrees.add(QualifierImprecision.calculate(qualifier)); // T9
        degrees.add(QualifierCardinality.calculate(qualifier)); // T10
        degrees.add(QualifierLength.calculate()); // T11
        
        System.out.println(degrees);

        return IntStream.range(0, n).mapToDouble(el -> el).reduce(0.0, (acc, value) -> acc + (weights.get((int)value) * degrees.get((int)value)));
    }
    
    public static double calculateManyFourthForm(List<TermData> data, Matcher matcher) {
        List<Double> weights = Weights.getWeights(n);
        List<Double> degrees = new ArrayList<>();
        List<List<TermData>> sets = Lists.partition(data, data.size() / 2);
        List<TermData> summarizer = sets.get(0);

        degrees.add(Truth.degreeOfTruthManyFourth(sets.get(0), sets.get(1), matcher)); // T1
        degrees.add(Imprecision.calculate(summarizer)); // T2
        degrees.add(Covering.calculate(summarizer, matcher)); // T3
        degrees.add(Appropriateness.calculate(summarizer, degrees.get(2))); // T4
        degrees.add(Length.calculate(summarizer)); // T5
        degrees.add(1.00); // T6
        degrees.add(1.00); // T7
        degrees.add(SummarizerCardinality.calculate(summarizer)); // T8
        degrees.add(1.00); // T9
        degrees.add(1.00); // T10
        degrees.add(1.00); // T11
        
        System.out.println(degrees);

        return IntStream.range(0, n).mapToDouble(el -> el).reduce(0.0, (acc, value) -> acc + (weights.get((int)value) * degrees.get((int)value)));
    }
}
