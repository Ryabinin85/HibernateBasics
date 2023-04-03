package pro.sky.hibernatebasics.dao.citydao;

import org.hibernate.Session;
import pro.sky.hibernatebasics.exceptions.CustomException;
import pro.sky.hibernatebasics.model.City;

import static pro.sky.hibernatebasics.connection.HibernateConnectionFactory.getSessionFactory;

public class CityDAOImpl implements CityDAO {

    @Override
    public City getCityById(Long id) {
        Session session = getSessionFactory().openSession();
        City city = session.get(City.class, id);
        if (city == null) {
            session.close();
            throw new CustomException("Город не найден в БД");
        } else {
            session.close();
            return city;
        }
    }
}
