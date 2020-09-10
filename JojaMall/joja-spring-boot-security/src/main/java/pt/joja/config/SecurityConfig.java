package pt.joja.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/").permitAll()
                .antMatchers("/level1/**").hasRole("VIP1")
                .antMatchers("/level2/**").hasRole("VIP2")
                .antMatchers("/level3/**").hasRole("VIP3");
        http.formLogin().loginPage("/userlogin").usernameParameter("joja_username").passwordParameter("joja_password");
        //1. /login请求来到登录页
        //2. 登录错误重定向到/login?error
        //3. 更多详细规则

        http.logout().logoutSuccessUrl("/");
        //1. /logout表示注销并清空Session

        http.rememberMe().rememberMeParameter("joja.remember");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder passwordEncoder= new BCryptPasswordEncoder();
        auth.inMemoryAuthentication().passwordEncoder(passwordEncoder)
                .withUser("Mary").password(passwordEncoder.encode("1234")).roles("VIP1", "VIP2")
                .and()
                .withUser("Lily").password(passwordEncoder.encode("1234")).roles("VIP2", "VIP3")
                .and()
                .withUser("Laura").password(passwordEncoder.encode("1234")).roles("VIP1", "VIP3");

    }
}
