package pt.joja.config;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class MyCacheConfig {

    @Bean("myKeyGenerator")
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            String key = method.getName() + "[" + Arrays.toString(params) + "]";
            System.out.println("myKeyGenerator: " + key);
            return key;
        };
    }

}
