package com.adtimokhin.configs;

import com.adtimokhin.models.Roles;
import com.adtimokhin.security.AuthProvider;
import com.adtimokhin.services.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author adtimokhin
 * 10.04.2021
 **/

@Configuration
@EnableWebSecurity
@ComponentScan("com.adtimokhin.security")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthProvider authProvider;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //TODO: I want custom error pages (404, 500)
        //TODO: Add an automatic login after the sign up.
        http.authorizeRequests()
                .antMatchers("/forum" , "/defaultSuccessUrl").authenticated()
                .antMatchers("/login", "/sign_up").anonymous()
                .antMatchers("/admin/*").hasRole(Roles.ROLE_ADMIN.getRole())
                .antMatchers("/student/*").hasRole(Roles.ROLE_STUDENT.getRole())
                .antMatchers("/parent/*").hasRole(Roles.ROLE_PARENT.getRole())
                .antMatchers("/*" , "/").permitAll()
                .and().csrf().disable()
                .formLogin().loginPage("/login").loginProcessingUrl("/login/process")
                .usernameParameter("email")
                .passwordParameter("password")
                .failureUrl("/login?error=true")
                .defaultSuccessUrl("/defaultSuccessUrl" , true)
                .and()
                .exceptionHandling()
                .accessDeniedPage("/error/404")
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/login");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authProvider);
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(getPasswordEncoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
