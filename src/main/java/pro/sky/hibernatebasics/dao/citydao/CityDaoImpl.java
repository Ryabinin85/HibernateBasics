package pro.sky.hibernatebasics.dao.citydao;

import org.hibernate.Session;
import pro.sky.hibernatebasics.exceptions.CustomException;
import pro.sky.hibernatebasics.model.City;
import pro.sky.hibernatebasics.model.Employee;

import static pro.sky.hibernatebasics.connection.HibernateConnectionFactory.getSessionFactory;

public class CityDaoImpl implements CityDao {

    @Override
    public City getCityById(Long id) {
        try (Session session = getSessionFactory().openSession()) {
            City city = session.get(City.class, id);

            if (city == null) {
                throw new CustomException("Город не найден в БД");
            } else {
                return city;
            }
        }
    }

    @Override
    public City getCityByEmployeeId(Long id) {
        try (Session session = getSessionFactory().openSession()) {
            Employee employee = session.get(Employee.class, id);

            if (employee == null) {
                throw new CustomException("Сотрудник не найден в БД");
            } else {
                return session.createQuery("select city from City city", City.class)
                        .getResultList()
                        .stream()
                        .filter(o -> o.equals(employee.getCity()))
                        .findFirst()
                        .orElseThrow(() -> new CustomException("Город не найден в БД"));
            }
        }
    }
}
