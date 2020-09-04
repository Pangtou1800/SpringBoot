package pt.joja.config;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@MapperScan("pt.joja.mapper")
@Configuration
public class MyBatisConfig {

    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> {
            System.out.println("Running MyBatis configurationCustomizer...");
            configuration.setMapUnderscoreToCamelCase(true);
        };
    }

}
