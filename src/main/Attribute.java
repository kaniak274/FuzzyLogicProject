package main;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

public abstract class Attribute {
    public ArrayList<Entry<Date, Double>> data = new ArrayList<>();

    public Attribute() {}
    
    public void setData(List<Entry<Date, Double>> data) {
        this.data = new ArrayList<>(data);
    }
}
