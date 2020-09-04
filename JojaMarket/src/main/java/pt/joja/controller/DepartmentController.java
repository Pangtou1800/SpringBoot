package pt.joja.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.joja.entities.Department;
import pt.joja.entities.Employee;
import pt.joja.mapper.DepartmentMapper;
import pt.joja.mapper.EmployeeMapper;

@RestController
public class DepartmentController {

    @Autowired
    DepartmentMapper departmentMapper;

    @Autowired
    EmployeeMapper employeeMapper;


    @GetMapping("/dept/{id}")
    public Department getDepartment(@PathVariable("id")int id) {
        return departmentMapper.getDepartmentById(id);
    }

    @GetMapping("/dept")
    public Department insertDepartment(Department department) {
        departmentMapper.insertDepartment(department);
        return department;
    }

    @GetMapping("/mybatis/emp/{id}")
    public Employee getEmployee(@PathVariable("id")int id) {
        return employeeMapper.getEmployeeById(id);
    }

    @GetMapping("/mybatis/emp")
    public Employee insertEmployee(Employee employee) {
        employeeMapper.insertEmployee(employee);
        return employee;
    }
}
