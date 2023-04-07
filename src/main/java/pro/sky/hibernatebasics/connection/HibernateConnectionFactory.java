package pro.sky.hibernatebasics.connection;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import pro.sky.hibernatebasics.model.City;
import pro.sky.hibernatebasics.model.Employee;

public class HibernateConnectionFactory {

    private static SessionFactory sessionFactory;

    private HibernateConnectionFactory() {
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().configure();
                configuration.addAnnotatedClass(City.class);
                configuration.addAnnotatedClass(Employee.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}
