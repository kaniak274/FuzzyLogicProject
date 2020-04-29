package db;

import java.util.Date;
import java.util.List;

public class Repository {
    @SuppressWarnings("unchecked")
    public List<Weather> getAllObjects() {
        return HibernateUtil
            .getSessionFactory()
            .openSession()
            .createQuery("From Weather")
            .list();
    }
    
    @SuppressWarnings("unchecked")
    public List<Weather> getDaysBetweenDates(Date startDay, Date endDay) {
    	return HibernateUtil
            .getSessionFactory()
            .openSession()
            .createQuery("From Weather WHERE date BETWEEN ?0 AND ?1")
            .setParameter(0, startDay)
            .setParameter(1, endDay)
            .list();
    }
}
