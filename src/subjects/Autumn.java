package subjects;

import java.util.List;

import db.Repository;
import db.Weather;

public class Autumn implements Subject {
    @Override
    public List<Weather> getAllRecords(Repository repo) {
        return repo.getAutumnRecords();
    }
    
    public String getLabel() {
        return " dni jesienne ";
    }
}
