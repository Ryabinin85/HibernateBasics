package pro.sky.hibernatebasics.dao.employeedao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import pro.sky.hibernatebasics.exceptions.CustomException;
import pro.sky.hibernatebasics.model.City;
import pro.sky.hibernatebasics.model.Employee;

import java.util.List;
import java.util.stream.Collectors;

import static pro.sky.hibernatebasics.connection.HibernateConnectionFactory.getSessionFactory;

public class EmployeeDaoImpl implements EmployeeDao {

    @Override
    public List<Employee> readAll() {
        try (Session session = getSessionFactory().openSession()) {
            List<Employee> employees = session
                    .createQuery("From Employee", Employee.class)
                    .list();
            if (employees.isEmpty()) {
                throw new CustomException("Список работников пуст");
            } else {
                return employees;
            }
        }
    }

    @Override
    public boolean createEmployee(Employee employee) {
        try (Session session = getSessionFactory().openSession()) {
            session.save(employee);
            return true;
        }
    }

    @Override
    public Employee getEmployeeByID(Long id) {
        try (Session session = getSessionFactory().openSession()) {
            Employee employee = session.get(Employee.class, id);

            if (employee == null) {
                throw new CustomException("Сотрудник не найден в БД");
            } else {
                return employee;
            }
        }
    }

    @Override
    public Employee updateEmployee(Employee employee, Long id) {
        try (Session session = getSessionFactory().openSession()) {
            Employee updatedEmployee = session.get(Employee.class, id);

            if (updatedEmployee == null) {
                throw new CustomException("Сотрудник не найден в БД");
            } else {
                Transaction transaction = session.beginTransaction();
                session.save(updatedEmployee);
                transaction.commit();
                return updatedEmployee;
            }
        }

    }

    @Override
    public boolean deleteEmployeeById(Long id) {
        try (Session session = getSessionFactory().openSession()) {
            Employee deletedEmployee = session.get(Employee.class, id);

            if (deletedEmployee == null) {
                throw new CustomException("Сотрудник не найден в БД");
            } else {
                Transaction transaction = session.beginTransaction();
                session.remove(deletedEmployee);
                transaction.commit();
                return true;
            }
        }
    }

    @Override
    public List<Employee> getEmployeesByCity(City city) {
        try (Session session = getSessionFactory().openSession()) {
            return session.createQuery("select employee from Employee employee", Employee.class)
                    .getResultList()
                    .stream()
                    .filter(o -> o.getCity().equals(city))
                    .collect(Collectors.toList());
        }
    }
}
