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
        Session session = getSessionFactory().openSession();
        List<Employee> employees = session
                .createQuery("From Employee", Employee.class)
                .list();
        if (employees.isEmpty()) {
            session.close();
            throw new CustomException("Список работников пуст");
        } else {
            session.close();
            return employees;
        }
    }

    @Override
    public boolean createEmployee(Employee employee) {
        Session session = getSessionFactory().openSession();
        session.save(employee);
        session.close();
        return true;
    }

    @Override
    public Employee getEmployeeByID(Long id) {
        Session session = getSessionFactory().openSession();
        Employee employee = session.get(Employee.class, id);

        if (employee == null) {
            session.close();
            throw new CustomException("Сотрудник не найден в БД");
        } else {
            session.close();
            return employee;
        }


    }

    @Override
    public Employee updateEmployee(Employee employee, Long id) {
        Session session = getSessionFactory().openSession();
        Employee updatedEmployee = session.get(Employee.class, id);
        if (updatedEmployee == null) {
            session.close();
            throw new CustomException("Сотрудник не найден в БД");
        } else {
            Transaction transaction = session.beginTransaction();
            session.save(updatedEmployee);
            transaction.commit();
            session.close();
            return updatedEmployee;
        }
    }


    @Override
    public boolean deleteEmployeeById(Long id) {
        Session session = getSessionFactory().openSession();
        Employee deletedEmployee = session.get(Employee.class, id);
        if (deletedEmployee == null) {
            session.close();
            throw new CustomException("Сотрудник не найден в БД");
        } else {
            Transaction transaction = session.beginTransaction();
            session.remove(deletedEmployee);
            transaction.commit();
            session.close();
            return true;
        }
    }

    @Override
    public List<Employee> getEmployeesByCity(City city) {
        Session session = getSessionFactory().openSession();
            List<Employee> employees = session.createQuery("select employee from Employee employee", Employee.class)
                    .getResultList()
                    .stream()
                    .filter(o -> o.getCity().equals(city))
                    .collect(Collectors.toList());
            session.close();
            return employees;
    }
}
