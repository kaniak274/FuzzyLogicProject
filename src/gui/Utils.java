package gui;

import java.lang.reflect.InvocationTargetException;
import java.util.AbstractMap;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import db.Weather;

public class Utils {
    public static String getSubject(boolean isTrue) {
        if (isTrue) {
            return "Dzie� by� ";
        }

        return "Dzie� nie by� ";
    }
	
    public static String getPluralSubject(boolean isTrue) {
        if (isTrue) {
            return " dni by�o ";
        }

        return " dni nie by�o ";
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
}
