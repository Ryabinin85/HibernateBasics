package pro.sky.hibernatebasics.dao.employeedao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.Test;
import pro.sky.hibernatebasics.exceptions.CustomException;
import pro.sky.hibernatebasics.model.Employee;

import static org.junit.jupiter.api.Assertions.*;
import static pro.sky.hibernatebasics.connection.HibernateConnectionFactory.getSessionFactory;

class EmployeeDaoImplTest {

    private final Long ID = 1L;
    private final Long NEW_USER_ID = 1000L;
    private final Long WRONG_ID = 100000000L;
    private final Long CITY_ID = 1L;
    private final Employee user = new Employee(ID, "user", "user", "M", 20, CITY_ID);
    private final Employee newUser = new Employee(NEW_USER_ID, "newUser", "newUser", "M", 20, CITY_ID);
    private static final EmployeeDaoImpl out = new EmployeeDaoImpl();

    @Test
    void ShouldFindEmployeeById() {
        assertEquals(out.getEmployeeByID(ID), user);

    }

    @Test
    void ShouldThrowExceptionWhenNotFoundEmployeeById() {
        assertThrows(CustomException.class, () -> out.getEmployeeByID(WRONG_ID));
    }

    @Test
    void ShouldUpdateEmployeeById() {
        assertEquals(out.updateEmployee(user, ID), user);
    }

    @Test
    void ShouldThrowExceptionWhenNotFoundEmployeeByIdWhenTryToUpdate() {
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
    void ShouldThrowExceptionWhenNotFoundEmployeeByIdWhenTryToDelete() {
        assertThrows(CustomException.class, () -> out.deleteEmployeeById(WRONG_ID));
    }

    @Test
    void createEmployee() {
        assertTrue(out.createEmployee(newUser));
        deleteEmployee(newUser);
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