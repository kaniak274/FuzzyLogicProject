package subjects;

import java.util.List;

import db.Repository;
import db.Weather;

public class Summer implements Subject {
    @Override
    public List<Weather> getAllRecords(Repository repo) {
        return repo.getSummerRecords();
    }
    
    public String getLabel() {
        return " dni letnich ";
    }
}
