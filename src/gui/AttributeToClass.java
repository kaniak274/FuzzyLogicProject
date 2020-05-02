package gui;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import db.Weather;
import fuzzy_set.FuzzySet;
import main.AirHumidity;
import main.Attribute;
import main.Insolation;
import main.PM10;
import main.PM25;
import main.Precipitation;
import main.Pressure;
import main.Temperature;
import main.Visibility;
import main.WindDirection;
import main.WindPower;
import terms.Term;

public class AttributeToClass {
    public Entry<Term, FuzzySet> getTerm(List<Weather> data, String key, String methodName) {
        if (key.equals("Temperatura")) {
            List<Entry<Date, Double>> entries = Utils.createSet(data, "getTemperature");
            Temperature temp = new Temperature(entries);
            return callMethod(temp, methodName);
        }
        
        if (key.equals("Ciœnienie atmosferyczne")) {
            List<Entry<Date, Double>> entries = Utils.createSet(data, "getPressure");
            Pressure temp = new Pressure(entries);
            return callMethod(temp, methodName);
        }
        
        if (key.equals("Widocznoœæ")) {
            List<Entry<Date, Double>> entries = Utils.createSet(data, "getVisibility");
            Visibility temp = new Visibility(entries);
            return callMethod(temp, methodName);
        }
        
        if (key.equals("Iloœæ opadów")) {
            List<Entry<Date, Double>> entries = Utils.createSet(data, "getPrecipitation");
            Precipitation temp = new Precipitation(entries);
            return callMethod(temp, methodName);
        }
        
        if (key.equals("Si³a wiatru")) {
            List<Entry<Date, Double>> entries = Utils.createSet(data, "getWindPower");
            WindPower temp = new WindPower(entries);
            return callMethod(temp, methodName);
        }
        
        if (key.equals("Kierunek wiatru")) {
            List<Entry<Date, Double>> entries = Utils.createSet(data, "getWindDirection");
            WindDirection temp = new WindDirection(entries);
            return callMethod(temp, methodName);
        }
        
        if (key.equals("Nas³onecznienie")) {
            List<Entry<Date, Double>> entries = Utils.createSet(data, "getInsolation");
            Insolation temp = new Insolation(entries);
            return callMethod(temp, methodName);
        }
        
        if (key.equals("Wilgotnoœæ powietrza")) {
            List<Entry<Date, Double>> entries = Utils.createSet(data, "getAir");
            AirHumidity temp = new AirHumidity(entries);
            return callMethod(temp, methodName);
        }
        
        if (key.equals("Stê¿enie PM2.5")) {
            List<Entry<Date, Double>> entries = Utils.createSet(data, "getPmTwo");
            PM25 temp = new PM25(entries);
            return callMethod(temp, methodName);
        }
        
        if (key.equals("Stê¿enie PM10")) {
            List<Entry<Date, Double>> entries = Utils.createSet(data, "getPmTen");
            PM10 temp = new PM10(entries);
            return callMethod(temp, methodName);
        }
        
        throw new RuntimeException("Atrybut nie znaleziony.");
    }
    
    @SuppressWarnings("unchecked")
	public Entry<Term, FuzzySet> callMethod(Attribute attr, String methodName) {
        try {
            return (Entry<Term, FuzzySet>) attr.getClass().getMethod(methodName).invoke(attr);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
            | SecurityException e) {
            e.printStackTrace();
        }
    	
        return null;
    }
}
