package pt.joja.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pt.joja.service.HelloService;

@Configuration
public class MyAppConfig {

    /**
     * id:方法名，val：返回值
     * @return
     */
    @Bean
    public HelloService helloService() {
        return new HelloService();
    }


}
