package com.sistemacitas.core.security;

import com.sistemacitas.core.service.JpaUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	private final JpaUserDetailsService jpaUserDetailsService;

	@Bean
	PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public SecurityConfig(JpaUserDetailsService jpaUserDetailsService) {
		this.jpaUserDetailsService = jpaUserDetailsService;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		auth.userDetailsService(jpaUserDetailsService)
		.passwordEncoder(getPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		http.authorizeRequests()
				.antMatchers("/user/").hasRole("ADMIN")
				.antMatchers("/user/crear").hasRole("ADMIN")
				.antMatchers("/user/eliminar/**").hasRole("ADMIN")
				.antMatchers("/citas/").hasAnyRole("GESTOR_CITAS","ADMIN")
				.antMatchers("/citas/nuevo").hasAnyRole("GESTOR_CITAS","ADMIN")
				.antMatchers("/citas/eliminar/**").hasAnyRole("GESTOR_CITAS","ADMIN")
				.antMatchers("/citas/update/**").hasAnyRole("GESTOR_CITAS","ADMIN")
				.antMatchers("/index").hasAnyRole("GESTOR_CITAS","ADMIN")
				.antMatchers("/").permitAll()
				.antMatchers("/css/**").permitAll()
				.antMatchers("/js/**").permitAll()
				.antMatchers("/img/**").permitAll()
				.and().formLogin()
				.loginPage("/login")
				.defaultSuccessUrl("/index")
				.permitAll();
		
	}
	
	
	
}
