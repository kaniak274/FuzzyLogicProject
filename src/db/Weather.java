package db;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Immutable;

@Entity
@Immutable
public class Weather {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false)
    public long id;
    
    @Column
    @Temporal(TemporalType.DATE)
    public Date date;
    
    @Column
    public int temperature;
    
    @Column
    public int pressure;
    
    @Column
    public double precipitation;
    
    @Column(name = "wind_direction")
    public int windDirection;
    
    @Column(name = "wind_power")
    public double windPower;
    
    @Column(name = "air_humidity")
    public int air;
    
    @Column
    public double insolation;
    
    @Column
    public int visibility;
    
    @Column(name = "pm_two")
    public int pmTwo;
    
    @Column(name = "pm_ten")
    public int pmTen;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public double getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(double precipitation) {
        this.precipitation = precipitation;
    }

    public int getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(int windDirection) {
        this.windDirection = windDirection;
    }

    public double getWindPower() {
        return windPower;
    }

    public void setWindPower(double windPower) {
        this.windPower = windPower;
    }

    public int getAir() {
        return air;
    }

    public void setAir(int air) {
        this.air = air;
    }

    public double getInsolation() {
        return insolation;
    }

    public void setInsolation(double insolation) {
        this.insolation = insolation;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public int getPmTwo() {
        return pmTwo;
    }

    public void setPmTwo(int pmTwo) {
        this.pmTwo = pmTwo;
    }

    public int getPmTen() {
        return pmTen;
    }

    public void setPmTen(int pmTen) {
        this.pmTen = pmTen;
    }
}
