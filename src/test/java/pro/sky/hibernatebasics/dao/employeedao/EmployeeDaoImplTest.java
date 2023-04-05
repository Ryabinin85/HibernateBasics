package pro.sky.hibernatebasics.dao.employeedao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.Test;
import pro.sky.hibernatebasics.exceptions.CustomException;
import pro.sky.hibernatebasics.model.City;
import pro.sky.hibernatebasics.model.Employee;

import static org.junit.jupiter.api.Assertions.*;
import static pro.sky.hibernatebasics.connection.HibernateConnectionFactory.getSessionFactory;

class EmployeeDaoImplTest {

    private final Long ID = 1L;
    private final Long NEW_USER_ID = 1000L;
    private final Long WRONG_ID = 100000000L;
    private final City MOSCOW = new City(1L, "Moscow");
    private final City SAMARA = new City(2L, "Samara");
    private final Employee user = new Employee(ID, "user", "user", "M", 20, MOSCOW);
    private final Employee newUser = new Employee(NEW_USER_ID, "newUser", "newUser", "M", 20, SAMARA);
    private static final EmployeeDaoImpl out = new EmployeeDaoImpl();

    @Test
    void shouldFindEmployeeById() {
        assertEquals(out.getEmployeeByID(ID), user);

    }

    @Test
    void shouldThrowExceptionWhenNotFoundEmployeeById() {
        assertThrows(CustomException.class, () -> out.getEmployeeByID(WRONG_ID));
    }

    @Test
    void shouldUpdateEmployeeById() {
        assertEquals(out.updateEmployee(user, ID), user);
    }

    @Test
    void shouldThrowExceptionWhenNotFoundEmployeeByIdWhenTryToUpdate() {
        assertThrows(CustomException.class, () -> out.updateEmployee(user, WRONG_ID));
    }

    @Test
    void ShouldReadAllEmployeesFromDataBase() {
        assertNotEquals(out.readAll(), null);
    }

    @Test
    void shouldDeleteEmployeeFromDataBase() {
        out.createEmployee(newUser);
        Long id = findEmployee(newUser).getId();
        assertTrue(out.deleteEmployeeById(id));
    }

    @Test
    void shouldThrowExceptionWhenNotFoundEmployeeByIdWhenTryToDelete() {
        assertThrows(CustomException.class, () -> out.deleteEmployeeById(WRONG_ID));
    }

    @Test
    void createEmployee() {
        assertTrue(out.createEmployee(newUser));
        deleteEmployee(newUser);
    }

    @Test
    void shouldGetEmployeesByCity() {
        int size = out.readAll()
                .stream()
                .filter(o -> o.getCity().equals(MOSCOW))
                .toList()
                .size();
        assertEquals(out.getEmployeesByCity(MOSCOW).size(), size);
    }

    private Employee findEmployee(Employee employee) {
        Session session = getSessionFactory().openSession();
        return session.createQuery("select employee from Employee employee", Employee.class)
                .getResultList()
                .stream()
                .filter(o -> o.getFirstName().equals(employee.getFirstName()))
                .findFirst().orElse(null);
    }

    private void deleteEmployee(Employee employee) {
        Session session = getSessionFactory().openSession();
        if (employee == null) {
            throw new CustomException("Сотрудник не найден в БД");
        } else {
            Transaction transaction = session.beginTransaction();
            session.remove(employee);
            transaction.commit();
            session.close();
        }
    }
}