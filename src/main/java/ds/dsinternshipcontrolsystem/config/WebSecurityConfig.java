package ds.dsinternshipcontrolsystem.config;

import ds.dsinternshipcontrolsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/register").not().authenticated()
                .antMatchers(HttpMethod.POST,
                        "/internships",
                        "/internships/*/end-registry",
                        "/internships/*/start",
                        "/internships/*/end",
                        "/internships/*/lessons",
                        "/internships/*/lessons/*/unchecked-commits/**",
                        "/internships/*/lessons/*/tasks",
                        "/internships/*/lessons/*/tasks/*/task-forks/**")
                .hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET,
                        "/internships/*/report",
                        "/internships/*/lessons/*/unchecked-commits/**",
                        "/internships/*/lessons/*/tasks/*/task-forks/**")
                .hasAuthority("ADMIN")
                .antMatchers("/user/**", "/internship/*/register", "/internship/*/leave")
                .hasAnyAuthority("ADMIN", "USER")
                .and().formLogin()
                .and().httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
