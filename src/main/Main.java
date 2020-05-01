package main;

import java.lang.reflect.InvocationTargetException;
import java.util.AbstractMap;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import db.Repository;
import db.Weather;
import fuzzy_set.FuzzySet;
import quantifiers.AbsoluteQ;
import quantifiers.Matcher;
import quantifiers.RelativeQ;
import terms.Term;

public class Main {
    public static String getSubject(boolean isTrue) {
        if (isTrue) {
            return "Dzieñ by³ ";
        }

        return "Dzieñ nie by³ ";
    }
	
    public static String getPluralSubject(boolean isTrue) {
        if (isTrue) {
            return " dni by³o ";
        }

        return " dni nie by³o ";
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
    
    public static String orLabel() {
        return " oraz ";
    }
    
    public static String andLabel() {
        return " i ";
    }
    
    public static String notLabel() {
        return " i nie ";
    }
	
    public static void main(String[] args) {
        Repository repo = new Repository();

        // List<Entry<Date, Double>> data = createSet(repo.getAllObjects(), "getTemperature");
        // Temperature tempFuzzy = new Temperature(data);
        // Entry<Term, FuzzySet> hotTerm = tempFuzzy.hotSetWithTerm();
        
        // double membership = hotTerm.getValue().getFuzzySet().get(140).getMembership();
        
        // System.out.println(getSubject(tempFuzzy.wasHot(membership)) + hotTerm.getKey().getLabel());
        
        List<Entry<Date, Double>> data2 = createSet(repo.getDaysBetweenDates(repo.formatDate("1992-01-01"), repo.formatDate("1992-01-31")), "getTemperature");
        System.out.println(data2);

        Temperature tempFuzzy2 = new Temperature(data2);
        Entry<Term, FuzzySet> hotTerm2 = tempFuzzy2.hotSetWithTerm();
        
        double membership2 = hotTerm2.getValue().getFuzzySet().get(0).getMembership();
                
        String count = AbsoluteQ.exactMatching(hotTerm2.getValue(), new Matcher() {
            @Override
            public boolean matcher(double membership) {
                return tempFuzzy2.wasHot(membership);
            }
        });
        
        System.out.println(count + getPluralSubject(true) + hotTerm2.getKey().getPluralLabe());
        
        Pressure presFuzzy = new Pressure(data2);
        Entry<Term, FuzzySet> pressureTerm = presFuzzy.highSetWithTerm();
        
        String relative = RelativeQ.quantifyOr(hotTerm2.getValue(), pressureTerm.getValue(), new Matcher() {
        	@Override
        	public boolean matcher(double membership) {
        		return membership >= 0.5;
        	}
        });
        
        System.out.println(relative + getPluralSubject(true) + hotTerm2.getKey().getPluralLabe() + orLabel() + pressureTerm.getKey().getPluralLabe());
    }
}
