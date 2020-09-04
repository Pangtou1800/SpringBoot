package pt.joja.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DruidConfig {

    @ConfigurationProperties(prefix = "spring.datasource.druid")
    @Bean
    public DataSource druid() {
        return new DruidDataSource();
    }

    //配置一个Druid的监控
    //1.配置一个管理后台的Servlet
    @Bean
    public ServletRegistrationBean<StatViewServlet> statViewServlet() {
        ServletRegistrationBean<StatViewServlet> servletRegistrationBean = new ServletRegistrationBean<>(new StatViewServlet());
        Map<String, String> parameters = new HashMap<>();
        parameters.put("loginUsername", "admin");
        parameters.put("loginPassword", "123456");
        parameters.put("allow", "");
        parameters.put("deny", "192.168.229.129");
        servletRegistrationBean.setInitParameters(parameters);
        servletRegistrationBean.addUrlMappings("/druid/*");
        return servletRegistrationBean;
    }
    //2.配置一个监控的filter
    @Bean
    public FilterRegistrationBean<WebStatFilter> webStatFilter(){
        FilterRegistrationBean<WebStatFilter> filterRegistrationBean = new FilterRegistrationBean<>(new WebStatFilter());
        Map<String, String> parameters = new HashMap<>();
        parameters.put("exclusions","*.js,*.css,/druid/*");
        filterRegistrationBean.setInitParameters(parameters);
        filterRegistrationBean.setUrlPatterns(Arrays.asList("/*"));

        return filterRegistrationBean;
    }
}
