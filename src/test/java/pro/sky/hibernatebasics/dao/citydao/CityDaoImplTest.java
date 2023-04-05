package pro.sky.hibernatebasics.dao.citydao;

import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import pro.sky.hibernatebasics.exceptions.CustomException;
import pro.sky.hibernatebasics.model.City;
import pro.sky.hibernatebasics.model.Employee;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static pro.sky.hibernatebasics.connection.HibernateConnectionFactory.getSessionFactory;

class CityDaoImplTest {

    private final CityDaoImpl out = new CityDaoImpl();
    private final Long ID = 1L;
    private final Long WRONG_ID = 100000000L;
    private final City MOSCOW = new City(1L, "Moscow");
    private final Employee user = new Employee(ID, "user", "user", "M", 20, MOSCOW);

    @Test
    void shouldFindCityById() {
        assertEquals(out.getCityById(ID), MOSCOW);
    }

    @Test
    void shouldThrowExceptionWhenNotFoundCityById() {
        assertThrows(CustomException.class, () -> out.getCityById(WRONG_ID));
    }

    @Test
    void shouldGetCityByEmployeeIdOrElseThrowException() {
        Employee employee = findEmployee(user);
        assertEquals(out.getCityByEmployeeId(employee.getId()), MOSCOW);
        assertThrows(CustomException.class, () -> out.getCityByEmployeeId(WRONG_ID));

    }


    private Employee findEmployee(Employee employee) {
        Session session = getSessionFactory().openSession();
        return session.createQuery("select employee from Employee employee", Employee.class)
                .getResultList()
                .stream()
                .filter(o -> o.getFirstName().equals(employee.getFirstName()))
                .findFirst().orElse(null);
    }
}