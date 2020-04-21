package db;

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
}
