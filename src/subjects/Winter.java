package subjects;

import java.util.List;

import db.Repository;
import db.Weather;

public class Winter implements Subject {
    @Override
    public List<Weather> getAllRecords(Repository repo) {
        return repo.getWinterRecords();
    }
    
    public String getLabel() {
        return " dni zimowych ";
    }
}
