package gui;

import main.AirHumidity;
import main.Insolation;
import main.PM10;
import main.PM25;
import main.Precipitation;
import main.Pressure;
import main.Temperature;
import main.Visibility;
import main.WindDirection;
import main.WindPower;

public class Belongs {
    public static boolean belongsToTerm(String attribute, String key, double membership) {
        if (attribute.equals("Temperatura")) {
            return temperatureB(key, membership);
        }
        
        if (attribute.equals("Ciœnienie atmosferyczne")) {
            return pressureB(key, membership);
        }
        
        if (attribute.equals("Widocznoœæ")) {
            return visibilityB(key, membership);
        }
        
        if (attribute.equals("Iloœæ opadów")) {
            return precipitationB(key, membership);
        }
        
        if (attribute.equals("Si³a wiatru")) {
            return windPowerB(key, membership);
        }
        
        if (attribute.equals("Kierunek wiatru")) {
            return windDirectionB(key, membership);
        }
        
        if (attribute.equals("Nas³onecznienie")) {
            return insolationB(key, membership);
        }
        
        if (attribute.equals("Wilgotnoœæ powietrza")) {
            return airHumidityB(key, membership);
        }
        
        if (attribute.equals("Stê¿enie PM2.5")) {
            return pm25B(key, membership);
        }
        
        return pm10B(key, membership);
    }
	
    private static boolean temperatureB(String key, double membership) {
        if (key.startsWith("cold")) {
            return new Temperature().wasCold(membership);
        }
        
        if (key.startsWith("hot")) {
            return new Temperature().wasHot(membership);
        }
        
        return new Temperature().wasModerate(membership);
    }
    
    private static boolean pressureB(String key, double membership) {
        if (key.startsWith("low")) {
            return new Pressure().wasLow(membership);
        }
        
        if (key.startsWith("high")) {
            return new Pressure().wasHigh(membership);
        }
        
        return new Pressure().wasMedium(membership);
    }
    
    private static boolean visibilityB(String key, double membership) {
        if (key.startsWith("low")) {
            return new Visibility().wasLow(membership);
        }
        
        if (key.startsWith("high")) {
            return new Visibility().wasHigh(membership);
        }
        
        return new Visibility().wasMedium(membership);
    }
    
    private static boolean windPowerB(String key, double membership) {
        if (key.startsWith("slow")) {
            return new WindPower().wasSlow(membership);
        }
        
        if (key.startsWith("fast")) {
            return new WindPower().wasFast(membership);
        }
        
        return new WindPower().wasModerate(membership);
    }
    
    private static boolean windDirectionB(String key, double membership) {
        if (key.startsWith("North")) {
            return new WindDirection().wasNorth(membership) || new WindDirection().wasNorth2(membership);
        }
        
        if (key.startsWith("East")) {
            return new WindDirection().wasEast(membership);
        }
        
        if (key.startsWith("South")) {
            return new WindDirection().wasSouth(membership);
        }
        
        return new WindDirection().wasWest(membership);
    }
    
    private static boolean airHumidityB(String key, double membership) {
        if (key.startsWith("low")) {
            return new AirHumidity().wasLow(membership);
        }
        
        if (key.startsWith("high")) {
            return new AirHumidity().wasHigh(membership);
        }
        
        return new AirHumidity().wasMedium(membership);
    }
    
    private static boolean insolationB(String key, double membership) {
        if (key.startsWith("low")) {
            return new Insolation().wasLow(membership);
        }
        
        if (key.startsWith("high")) {
            return new Insolation().wasHigh(membership);
        }
        
        return new Insolation().wasMedium(membership);
    }
    
    private static boolean pm25B(String key, double membership) {
        if (key.startsWith("low")) {
            return new PM25().wasLow(membership);
        }
        
        if (key.startsWith("high")) {
            return new PM25().wasHigh(membership);
        }
        
        return new PM25().wasMedium(membership);
    }
    
    private static boolean pm10B(String key, double membership) {
        if (key.startsWith("low")) {
            return new PM10().wasLow(membership);
        }
        
        if (key.startsWith("high")) {
            return new PM10().wasHigh(membership);
        }
        
        return new PM10().wasMedium(membership);
    }
    
    private static boolean precipitationB(String key, double membership) {
        if (key.startsWith("low")) {
            return new Precipitation().wasLow(membership);
        }
        
        if (key.startsWith("high")) {
            return new Precipitation().wasHigh(membership);
        }
        
        return new Precipitation().wasMedium(membership);
    }
}
