package com.partymanager.finalproject.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.partymanager.finalproject.service.UserDetailsServiceImpl;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration{

	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	@Autowired
	private PasswordEncoder passwordEncoder;

	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http) 
	  throws Exception {
	    return http.getSharedObject(AuthenticationManagerBuilder.class)
	      .userDetailsService(userDetailsServiceImpl)
	      .passwordEncoder(passwordEncoder)
	      .and()
	      .build();
	}
	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
     
        http.csrf().disable()
        
        /* Login configuration */
        .formLogin()
        	.loginPage("/login")
        	.defaultSuccessUrl("/player") // user's home page, it can be any URL
        	.permitAll() // Anyone can go to the login page
        /* Logout configuration */
        .and()
        	.logout()
        	.logoutSuccessUrl("/login")
        	.permitAll()
        .and()
        	.authorizeHttpRequests()
        	.antMatchers("/","/css/**", "/js/**", "/images/**").permitAll()
        	.antMatchers("/index","/register").permitAll()
        	.antMatchers("/player").permitAll()
        	.anyRequest()
        .authenticated();
 
        return http.build();
    }
	
	
}
