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
                .antMatchers("/admin/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/internships").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/internships/*/end-registry").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/internships/*/start").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/internships/*/end").hasAuthority("ADMIN")
                .antMatchers("/user/**").hasAnyAuthority("ADMIN", "USER")
                .antMatchers(HttpMethod.POST, "/internship/*/register").hasAnyAuthority("ADMIN", "USER")
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
