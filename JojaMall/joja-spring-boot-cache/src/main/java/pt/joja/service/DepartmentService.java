package pt.joja.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pt.joja.bean.Department;
import pt.joja.mapper.DepartmentMapper;

@Service
public class DepartmentService {

    @Autowired
    DepartmentMapper departmentMapper;

    @Cacheable(cacheNames = "dept")
    public Department getDepartmentById(Integer id) {
        Department department = departmentMapper.getDepartmentById(id);
        return department;
    }
}
