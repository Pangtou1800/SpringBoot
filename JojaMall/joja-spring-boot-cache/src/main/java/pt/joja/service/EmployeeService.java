package pt.joja.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;
import pt.joja.bean.Employee;
import pt.joja.mapper.EmployeeMapper;

@CacheConfig(cacheNames = "emp")
@Service
public class EmployeeService {

    @Autowired
    EmployeeMapper employeeMapper;

    @Cacheable(key = "'emp.id=' + #id", condition = "#id<2")
    public Employee getEmp(Integer id) {
        System.out.println("查询" + id + "号员工");
        return employeeMapper.getEmployeeById(id);
    }

    @CachePut(key = "'emp.id=' + #employee.id")
    public Employee updateEmployee(Employee employee) {
        System.out.println("更新" + employee.getId() + "号员工：" + employee.toString());
        employeeMapper.updateEmployee(employee);
        return employee;
    }

    @CacheEvict(key = "'emp.id=' + #id")
    public void deleteEmployee(Integer id) {
        System.out.println("删除" + id + "号员工");
        //employeeMapper.getEmployeeById(id);
    }

    @Caching(
            cacheable = {
                    @Cacheable(key = "'emp.lastName=' + #lastName")
            },
            put = {
                    @CachePut(key = "'emp.id=' + #result.id"),
                    @CachePut(key = "'emp.email=' + #result.email")
            }
    )
    public Employee getEmpByLastName(String lastName) {
        return employeeMapper.getEmployeeByLastName(lastName);
    }

}
