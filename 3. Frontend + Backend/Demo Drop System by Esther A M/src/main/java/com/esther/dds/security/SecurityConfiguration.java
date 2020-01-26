package com.esther.dds.security;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private UserDetailsServiceImpl userDetailsService;

    public SecurityConfiguration(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //set all mapplings & their permissions
        //todo, read into this (how to make it shorter so I dont have to declare everything
        http.
                authorizeRequests()
                    .requestMatchers(EndpointRequest.to("info")).permitAll()
                    .requestMatchers(EndpointRequest.toAnyEndpoint()).hasRole("ADMIN")
                    .antMatchers("/actuator/").hasRole("ADMIN")
                    .antMatchers("/user-side/**").permitAll()
                    .antMatchers("/user-side/authorized/**").hasRole("USER")
                    .antMatchers("/user-side/login").permitAll()
                    .antMatchers("/user-side/activate/").permitAll()
                    .antMatchers("/user-side/register").permitAll()
                    .antMatchers("/user-side/authorized/dashboard").hasRole("USER")
                    .antMatchers("/user-side/authorized/dropdemo").hasRole("USER")
                    .antMatchers("/user-side/authorized/demo/{id}").hasRole("USER")
                    .antMatchers("/user-side/authorized/delete").hasRole("USER")
                    .antMatchers("/user-side/authorized/settings").hasRole("USER")
                    .antMatchers("/h2-console/**").permitAll()
                .and()
                .formLogin()
                    .loginPage("/login").permitAll()
                    .loginProcessingUrl("login")
                    .usernameParameter("email")
                .and()
                    .logout()
                    .and()
                    .rememberMe()
                .and().csrf().ignoringAntMatchers("/h2-console/**") //don't apply CSRF protection to /h2-console
                .and().headers().frameOptions().sameOrigin();//allow use of frame to same origin urls

//               .and()
    //               .csrf().disable()
    //               .headers().frameOptions().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }
}