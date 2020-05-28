package degrees;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import terms.TermData;

// T4
public class Appropriateness {
    public static double calculate(List<TermData> sets, double t3) {
        List<Integer> supports = new ArrayList<>();

        // TODO
        
        // List<TermData> sumarizer = IntStream.range(0, sets.size())
        //    .filter(i -> i != 0)
        //    .mapToObj(i -> sets.get(i))
        //    .collect(Collectors.toList());
        
        for (TermData set : sets) {
        	supports.add((int) set.getSet().getSupport().count());
        }
        
        double product = supports.stream().reduce(1, (acc, value) -> acc * value);
        return Math.abs(product - t3);
    }
}
