package main;

import java.lang.reflect.InvocationTargetException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import db.Repository;
import db.Weather;
import fuzzy_set.FuzzySet;
import terms.Term;

public class Main {
    public static String getSubject(double data, boolean isTrue) {
        if (isTrue) {
            return "Dzieñ by³ ";
        }

        return "Dzieñ nie by³ ";
    }
	
    public static String getSubject(ArrayList<Double> data, boolean isTrue) {
        if (isTrue) {
            return "Dni by³y ";
        }

        return "Dni nie by³y ";
    }
    
    public static List<Entry<Date, Double>> createSet(List<Weather> data, String name) {
        return data
            .stream()
            .map(element -> {
                try {
                    return new AbstractMap.SimpleEntry<>(element.date, Double.parseDouble(element.getClass().getMethod(name).invoke(element).toString()));
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
                    | NoSuchMethodException | SecurityException e) {
                    e.printStackTrace();
                }

                return null;
            })
            .collect(Collectors.toList());
    }
	
    public static void main(String[] args) {
        Repository repo = new Repository();

        List<Entry<Date, Double>> data = createSet(repo.getAllObjects(), "getTemperature");
        Temperature tempFuzzy = new Temperature(data);
        Entry<Term, FuzzySet> hotTerm = tempFuzzy.hotSetWithTerm();
        
        double membership = hotTerm.getValue().getFuzzySet().get(140).getMembership();
        
        System.out.println(getSubject(membership, tempFuzzy.wasHot(membership)) + hotTerm.getKey().getLabel());
    }
}
