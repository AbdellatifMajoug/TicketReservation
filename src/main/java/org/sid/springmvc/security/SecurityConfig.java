package org.sid.springmvc.security;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired 
	private DataSource dataSource;
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		PasswordEncoder passwordEncoder=passwordEncoder();
		//super.configure(auth);
		auth.jdbcAuthentication().dataSource(dataSource)
		.usersByUsernameQuery("Select username as principal,password as credentials, active from users where username=?")
		.authoritiesByUsernameQuery("select username as principal,role as role from users where username=?")
		.passwordEncoder(passwordEncoder).rolePrefix("ROLE_");
		
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//super.configure(http);
		http.formLogin().loginPage("/login");
		http.authorizeHttpRequests().antMatchers("/gest**/**","/save**/**","/delete**/**","/edit**/**","/modifier**/**").hasRole("ADMIN");
		http.authorizeHttpRequests().antMatchers("/reservation").hasRole("USER");
		http.csrf();
	}
	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
} 
