package pt.joja;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pt.joja.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DubboTest {

    @Autowired
    UserService userService;

    @Test
    public void test01() {
        userService.hello();
    }


}
