package subjects;

import java.util.List;

import db.Repository;
import db.Weather;

public class Spring implements Subject {
    @Override
    public List<Weather> getAllRecords(Repository repo) {
        return repo.getSpringRecords();
    }
    
    public String getLabel() {
        return " dni wiosennych ";
    }
}
