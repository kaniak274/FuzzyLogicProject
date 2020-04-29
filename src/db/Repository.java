package db;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Repository {
    public SimpleDateFormat formatter() {
        return new SimpleDateFormat("yyyy-MM-dd");
    }
	
    public Date formatDate(String date) {
        try {
            return formatter().parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
	
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
            .createQuery("From Weather WHERE date BETWEEN :startDay AND :endDay")
            .setParameter("startDay", startDay)
            .setParameter("endDay", endDay)
            .list();
    }
}
