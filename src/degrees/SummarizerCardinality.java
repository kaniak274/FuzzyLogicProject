package degrees;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import terms.TermData;

public class SummarizerCardinality {
    public static double calculate(List<TermData> sets, double records) {
        List<TermData> sumarizer = IntStream.range(0, sets.size())
            .filter(i -> i != 0)
            .mapToObj(i -> sets.get(i))
            .collect(Collectors.toList());
        
        List<Double> sumarizersCardinality = new ArrayList<>();
        
        for (TermData set : sumarizer) {
            sumarizersCardinality.add(set.getSet().getFuzzySet().size() / Math.abs(set.getUniverse().get(1) - set.getUniverse().get(0)));
        }
        
        double product = sumarizersCardinality.stream().reduce(1.00, (acc, value) -> acc * value);
        return 1 - Math.pow(product, 1 / sumarizer.size());
    }
}
