package com.NewCodeTeam.Comercializadora.security;

import com.NewCodeTeam.Comercializadora.handler.CustomSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Configuration;


import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Autowired
    private DataSource dataSource;
    @Autowired
    CustomSuccessHandler customSuccessHandler;

    @Autowired
    public void configAuhentication(AuthenticationManagerBuilder auth) throws Exception{
        auth.jdbcAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .dataSource(dataSource)
                .usersByUsernameQuery("select  email,password , true from employee where email=?" )
                .authoritiesByUsernameQuery("select email, role from employee where email=?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/api/enterprises/**").hasRole("ADMIN")
                .antMatchers("/api/employees/**").hasRole("USER")
                .and().formLogin().successHandler(customSuccessHandler)
                .and().exceptionHandling().accessDeniedPage("/api/Denegado")
                .and().logout().permitAll();
    }
}
