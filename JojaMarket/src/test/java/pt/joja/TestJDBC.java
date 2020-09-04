package pt.joja;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.SQLException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestJDBC {

    @Autowired
    DataSource dataSource;

    @Test
    public void test01() throws SQLException {
        System.out.println(dataSource.getClass().getName());
        System.out.println(dataSource.getConnection());
    }

}
