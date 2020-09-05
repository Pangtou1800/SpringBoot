package pt.joja;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pt.joja.mapper.EmployeeMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Test01 {

    @Autowired
    EmployeeMapper employeeMapper;

    @Test
    public void contextLoads() {
        System.out.println(employeeMapper.getEmployeeById(1));
    }

}
