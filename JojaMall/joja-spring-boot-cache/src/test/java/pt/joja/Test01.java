package pt.joja;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import pt.joja.bean.Employee;
import pt.joja.mapper.EmployeeMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Test01 {

    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    RedisTemplate<Object, Employee> redisTemplateEmployee;

    @Test
    public void contextLoads() {
        System.out.println(employeeMapper.getEmployeeById(1));
    }

    /**
     * Redis常见的五大数据类型
     * String, List, Set, Hash, ZSet(有序集合)
     */
    @Test
    public void test01() {
        // String
        stringRedisTemplate.opsForValue().append("msg", " very good");
        System.out.println(stringRedisTemplate.opsForValue().get("msg"));

        stringRedisTemplate.opsForList().leftPushAll("employees", "Mary", "Lily", "Nozomi", "Hikari");
        stringRedisTemplate.opsForList().range("employees", 0, 5);
    }

    @Test
    public void test02() {
        Employee employee = employeeMapper.getEmployeeById(1);
        redisTemplate.opsForValue().set("emp-01", employee);
    }

    @Test
    public void test03() {
        System.out.println(redisTemplate.opsForValue().get("emp-01"));
    }

    @Test
    public void test04() {
        Employee employee = employeeMapper.getEmployeeById(1);
        redisTemplateEmployee.opsForValue().set("emp-01#", employee);
        System.out.println(redisTemplateEmployee.opsForValue().get("emp-01#"));
    }

    @Test
    public void test05() {
    }

}
