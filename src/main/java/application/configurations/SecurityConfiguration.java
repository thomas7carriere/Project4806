package application.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.inMemoryAuthentication()
                .withUser("surveyor1")
                .password("password")
                .roles("SURVEYOR")
                .and()
                .withUser("user1")
                .password("password")
                .roles("USER");
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        //return new BCryptPasswordEncoder();
        //passwords stored as plain text
        return NoOpPasswordEncoder.getInstance();
    }

    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/survey/create").hasRole("SURVEYOR")
                .antMatchers("/survey/view/**").hasAnyRole("SURVEYOR", "USER")
                .antMatchers("/").hasAnyRole("SURVEYOR", "USER")
                .and().formLogin().and()
                .csrf().disable();
    }
}
