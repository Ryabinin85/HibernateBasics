package pro.sky.hibernatebasics.dao.employeedao;

import pro.sky.hibernatebasics.model.City;
import pro.sky.hibernatebasics.model.Employee;

import java.util.List;

public interface EmployeeDao {
    List<Employee> readAll();

    boolean createEmployee(Employee employee);

    Employee getEmployeeByID(Long id);

    Employee updateEmployee(Employee employee, Long id);

    boolean deleteEmployeeById(Long id);

    List<Employee> getEmployeesByCity(City city);
}
