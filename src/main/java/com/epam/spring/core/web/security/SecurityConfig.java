package com.epam.spring.core.web.security;

import com.epam.spring.core.web.security.model.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@ComponentScan("com.epam.spring.core.security.model")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final DataSource dataSource;
    private final UserDetailsService splittingRolesUserDetails;
    private final AccessDeniedHandler accessDeniedHandler;

    @Autowired
    public SecurityConfig(DataSource dataSource, UserDetailsService splittingRolesUserDetails,
                          AccessDeniedHandler accessDeniedHandler) {
        this.dataSource = dataSource;
        this.splittingRolesUserDetails = splittingRolesUserDetails;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    // overrides role prefix in case .access() in httpSecurity configuration
    // just because it is needed in the task. hasRole() won't work
    // as there are used different voters in AffirmativeBased.
    // link to the related issue on GitHub:
    // https://github.com/spring-projects/spring-security/issues/3701
    @Bean
    GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
            .authenticationProvider(authenticationProvider())
            .jdbcAuthentication()
            .dataSource(dataSource)
            .usersByUsernameQuery("select user_name, password, true from user where username=?");
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(splittingRolesUserDetails);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .authorizeRequests()
                .antMatchers("/login/**").permitAll()
                .antMatchers("/api/csrf").permitAll()
                .antMatchers("/api/ticket/event**").access("hasRole('" + Role.BOOKING_MANAGER.toString() + "')")
                .anyRequest().access("hasRole('" + Role.REGISTERED_USER.toString() + "')")
            .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/event")
                .permitAll()
            .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .accessDeniedPage("/403")
            .and()
                .rememberMe()
                .userDetailsService(splittingRolesUserDetails)
            .and()
                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
    }
}
