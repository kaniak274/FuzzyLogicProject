package generators;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import attributes.AirHumidity;
import attributes.Attribute;
import attributes.Insolation;
import attributes.PM10;
import attributes.PM25;
import attributes.Precipitation;
import attributes.Pressure;
import attributes.Temperature;
import attributes.Visibility;
import attributes.WindDirection;
import attributes.WindPower;
import db.Weather;
import fuzzy_set.FuzzySet;
import gui.summary.AttributeToClass;
import gui.summary.Utils;
import hedges.PowerHedge;
import memberships.Gauss;
import memberships.Membership;
import memberships.Trapezoid;
import memberships.Triangle;
import terms.LinguisticVariable;
import terms.Term;
import terms.TermData;

public class CreateTerm {
    public static TermData create(HashMap<String, List<String>> data, String attr, String term, List<Weather> records) {
        if (data.containsKey(term)) {
            List<String> values = data.get(term);
            Attribute field = getAttribute(values.get(0), records);
            Entry<Term, Membership> fieldMembership = generateMembership(values);
            FuzzySet set = new LinguisticVariable("temperatura", null, field.getUniverse()).getSetForTerm(field.data, fieldMembership.getValue());

            return new TermData(new AbstractMap.SimpleEntry<Term, FuzzySet>(fieldMembership.getKey(), set), fieldMembership.getValue(), field.getUniverse());
        }

        return getTermForAttribute(records, attr, term);
    }
    
    private static TermData getTermForAttribute(List<Weather> records, String attribute, String term) {
        AttributeToClass attr = new AttributeToClass();
        return attr.getTerm(records, attribute, term); 
    }
    
    private static Entry<Term, Membership> generateMembership(List<String> values) {
        if (values.get(1).equals("Trapezoid")) {
            ArrayList<Double> scope = new ArrayList<>();

            scope.add(Double.parseDouble(values.get(2)));
            scope.add(Double.parseDouble(values.get(3)));
            scope.add(Double.parseDouble(values.get(4)));
            scope.add(Double.parseDouble(values.get(5)));
            
            Term term = new Term(values.get(6), scope, values.get(7), values.get(8));
            return new AbstractMap.SimpleEntry<Term, Membership>(term, new Trapezoid(term));
        }
        
        if (values.get(1).equals("Trójk¹tna")) {
            ArrayList<Double> scope = new ArrayList<>();

            scope.add(Double.parseDouble(values.get(2)));
            scope.add(Double.parseDouble(values.get(3)));
            scope.add(Double.parseDouble(values.get(4)));
            
            Term term = new Term(values.get(5), scope, values.get(6), values.get(7));
            return new AbstractMap.SimpleEntry<Term, Membership>(term, new Triangle(term));
        }
        
        ArrayList<Double> scope = new ArrayList<>();

        scope.add(Double.parseDouble(values.get(2)));
        scope.add(Double.parseDouble(values.get(3)));
        scope.add(Double.parseDouble(values.get(4)));
        
        Term term = new Term(values.get(5), scope, values.get(6), values.get(7));
        return new AbstractMap.SimpleEntry<Term, Membership>(term, new Gauss(term));
    }
    
    private static Attribute getAttribute(String key, List<Weather> records) {
        if (key.equals("Temperatura")) {
            List<Entry<Date, Double>> entries = Utils.createSet(records, "getTemperature");
            return new Temperature(entries);
        }
        
        if (key.equals("Ciœnienie atmosferyczne")) {
            List<Entry<Date, Double>> entries = Utils.createSet(records, "getPressure");
            return new Pressure(entries);
        }
        
        if (key.equals("Widocznoœæ")) {
            List<Entry<Date, Double>> entries = Utils.createSet(records, "getVisibility");
            return new Visibility(entries);
        }
        
        if (key.equals("Iloœæ opadów")) {
            List<Entry<Date, Double>> entries = Utils.createSet(records, "getPrecipitation");
            return new Precipitation(entries);
        }
        
        if (key.equals("Si³a wiatru")) {
            List<Entry<Date, Double>> entries = Utils.createSet(records, "getWindPower");
            return new WindPower(entries);
        }
        
        if (key.equals("Kierunek wiatru")) {
            List<Entry<Date, Double>> entries = Utils.createSet(records, "getWindDirection");
            return new WindDirection(entries);
        }
        
        if (key.equals("Nas³onecznienie")) {
            List<Entry<Date, Double>> entries = Utils.createSet(records, "getInsolation");
            return new Insolation(entries);
        }
        
        if (key.equals("Wilgotnoœæ powietrza")) {
            List<Entry<Date, Double>> entries = Utils.createSet(records, "getAir");
            return new AirHumidity(entries);
        }
        
        if (key.equals("Stê¿enie PM2.5")) {
            List<Entry<Date, Double>> entries = Utils.createSet(records, "getPmTwo");
            return new PM25(entries);
        }
        
        if (key.equals("Stê¿enie PM10")) {
            List<Entry<Date, Double>> entries = Utils.createSet(records, "getPmTen");
            return new PM10(entries);
        }
        
        throw new RuntimeException("Atrybut nie znaleziony.");
    }
}
