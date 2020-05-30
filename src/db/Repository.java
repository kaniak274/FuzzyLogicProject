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

    @SuppressWarnings("unchecked")
    public List<Weather> getWinterRecords() {
        return HibernateUtil
            .getSessionFactory()
            .openSession()
            .createQuery("From Weather WHERE to_char(date, 'MMDD') between '1221' and '1231' or to_char(date,'MMDD') between '0101' and '0320'")
            .list();
    }

    @SuppressWarnings("unchecked")
    public List<Weather> getSummerRecords() {
        return HibernateUtil
            .getSessionFactory()
            .openSession()
            .createQuery("From Weather WHERE to_char(date, 'MMDD') between '0620' and '0922'")
            .list();
    }

    @SuppressWarnings("unchecked")
    public List<Weather> getAutumnRecords() {
        return HibernateUtil
            .getSessionFactory()
            .openSession()
            .createQuery("From Weather WHERE to_char(date, 'MMDD') between '0923' and '1220'")
            .list();
    }

    @SuppressWarnings("unchecked")
    public List<Weather> getSpringRecords() {
        return HibernateUtil
            .getSessionFactory()
            .openSession()
            .createQuery("From Weather WHERE to_char(date, 'MMDD') between '0321' and '0619'")
            .list();
    }
}
