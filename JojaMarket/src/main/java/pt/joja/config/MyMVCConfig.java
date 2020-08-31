package pt.joja.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import pt.joja.component.MyLocaleResolver;

//@EnableWebMvc
@Configuration
public class MyMVCConfig extends WebMvcConfigurerAdapter {

    @Bean
    public WebMvcConfigurerAdapter webMvcConfigurerAdapter() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                super.addViewControllers(registry);
                registry.addViewController("/").setViewName("login");
                registry.addViewController("/index.html").setViewName("login");
            }
        };
    }

    @Bean
    public LocaleResolver localeResolver() {
        return new MyLocaleResolver();
    }
}
