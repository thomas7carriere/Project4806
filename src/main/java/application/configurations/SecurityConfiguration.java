package application.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService(){
        UserDetails surveyor1 = User.withUsername("surveyor1").password(BCrypt.hashpw("password", BCrypt.gensalt())).roles("SURVEYOR").build();
        UserDetails surveyor2 = User.withUsername("surveyor2").password(BCrypt.hashpw("password", BCrypt.gensalt())).roles("SURVEYOR").build();
        UserDetails user1 = User.withUsername("user1").password(BCrypt.hashpw("password", BCrypt.gensalt())).roles("USER").build();
        UserDetails user2 = User.withUsername("user2").password(BCrypt.hashpw("password", BCrypt.gensalt())).roles("USER").build();
        return new InMemoryUserDetailsManager(surveyor1, surveyor2, user1, user2);
    }

    /**
     * Configures the
     * @param http
     * @throws Exception
     */
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/survey/create").hasRole("SURVEYOR")
                .antMatchers("/mysurveys").hasRole(("SURVEYOR"))
                .antMatchers("/cheat").hasRole("SURVEYOR")
                .antMatchers("/export").hasRole("SURVEYOR")
                .antMatchers("/log_**").hasRole("SURVEYOR")
                .antMatchers("/mysurveys/edit/**").hasAnyRole("SURVEYOR")
                .antMatchers("/mysurveys/export/**").hasRole("SURVEYOR")
                .antMatchers("/mysurveys/view/**").hasAnyRole("SURVEYOR")
                .antMatchers("/survey/view/**").hasAnyRole("SURVEYOR", "USER")
                .antMatchers("/survey/delete/**").hasAnyRole("SURVEYOR")
                .antMatchers("/").hasAnyRole("SURVEYOR", "USER")
                .antMatchers("/h2-console/**").permitAll() //Allow access to h2-console
                .and().formLogin().permitAll().and()
                .csrf().disable();
                http.headers().frameOptions().disable(); //Needs to be disabled for h2-console
    }
}
