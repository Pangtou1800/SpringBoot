package pt.joja;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@MapperScan("pt.joja.mapper")
@SpringBootApplication
public class Bootstrap {
    public static void main(String[] args) {
        SpringApplication.run(pt.joja.Bootstrap.class);
    }
}
