package subjects;

import java.util.List;

import db.Repository;
import db.Weather;

public interface Subject {
    public List<Weather> getAllRecords(Repository repo);

    public String getLabel();
}
