package pt.joja.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pt.joja.bean.Employee;
import pt.joja.service.EmployeeService;

@RestController
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping("/emp/{id}")
    public Employee getEmployee(@PathVariable("id") Integer id) {
        return employeeService.getEmp(id);
    }

    @GetMapping("/emp/lastname/{lastName}")
    public Employee getEmployeeByLastName(@PathVariable("lastName") String lastName) {
        return employeeService.getEmpByLastName(lastName);
    }

    @GetMapping("/emp")
    public Employee updateEmployee(Employee employee) {
        return employeeService.updateEmployee(employee);
    }

    @GetMapping("/deleteEmp/{id}")
    public String deleteEmp(@PathVariable("id") Integer id) {
        employeeService.deleteEmployee(id);
        return "success";
    }


}
