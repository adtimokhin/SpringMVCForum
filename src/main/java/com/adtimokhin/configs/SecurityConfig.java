package com.adtimokhin.configs;

import com.adtimokhin.enums.Role;
import com.adtimokhin.security.AuthProvider;
import com.adtimokhin.services.user.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
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
        http.authorizeRequests()
                .antMatchers("/forum", "/defaultSuccessUrl", "/logout", "/account/*").authenticated()
                .antMatchers("/sign_up").anonymous()
                .antMatchers("/admin/*").hasRole(Role.ROLE_ADMIN.getRole())
                .antMatchers("/", "/login").permitAll()
                .antMatchers("/topics" , "/topic/*" , "/add/*", "/intro").hasAnyRole(Role.ROLE_STUDENT.getRole(),
                                                Role.ROLE_PARENT.getRole(),
                                                Role.ROLE_ORGANIZATION_MEMBER.getRole())
                .and()
                .formLogin()
                    .loginPage("/login")
                        .loginProcessingUrl("/login/process")
                        .usernameParameter("email")
                        .passwordParameter("password")
                    .failureUrl("/login?error=true")
                    .defaultSuccessUrl("/defaultSuccessUrl", true)
                .and()
                    .exceptionHandling()
                        .accessDeniedPage("/error/404")
                .and()
                .logout()
                    .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                .and()
                .rememberMe();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider);
        auth.userDetailsService(userDetailsService);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
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
