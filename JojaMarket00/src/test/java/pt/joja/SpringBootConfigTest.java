package pt.joja;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pt.joja.bean.Person;
import pt.joja.service.HelloService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootConfigTest {

    @Autowired
    HelloService helloService;

    @Autowired
    Person person;

    @Test
    public void testConfig() {
        System.out.println(helloService);
    }

    @Test
    public void contextLoads() {
        System.out.println(person);
    }
}
