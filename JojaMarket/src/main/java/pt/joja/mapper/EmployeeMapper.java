package pt.joja.mapper;

import pt.joja.entities.Employee;

public interface EmployeeMapper {

    Employee getEmployeeById(int id);

    boolean insertEmployee(Employee employee);

}
