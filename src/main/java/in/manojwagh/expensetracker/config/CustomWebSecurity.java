package in.manojwagh.expensetracker.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class CustomWebSecurity  {
	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	private PasswordEncoder bCryptpasswordEncoder;

	@Autowired
	private CustomJwtFilter customJwtFilter;
	


	/*
	 * @Override protected void configure(HttpSecurity http) throws Exception {
	 * 
	 * http.csrf().disable().authorizeRequests().antMatchers("/logins",
	 * "/register").permitAll().anyRequest()
	 * .authenticated().and().httpBasic().and().formLogin().and().sessionManagement(
	 * ) .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	 * http.addFilterBefore(customJwtFilter,
	 * UsernamePasswordAuthenticationFilter.class);
	 * 
	 * }
	 * 
	 * @Override protected void configure(AuthenticationManagerBuilder auth) throws
	 * Exception { auth.userDetailsService(userDetailsService).passwordEncoder(
	 * bCryptpasswordEncoder); }
	 * 
	 * @Bean public AuthenticationManager authenticationManagerBean() throws
	 * Exception {
	 * 
	 * return super.authenticationManagerBean(); }
	 */
	
	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http) 
	  throws Exception {
	    return http.getSharedObject(AuthenticationManagerBuilder.class)
	      .userDetailsService(userDetailsService)
	      .passwordEncoder(bCryptpasswordEncoder)
	      .and()
	      .build();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	
		http.csrf().disable().authorizeRequests().antMatchers("/logins", "/register").permitAll().anyRequest()
		.authenticated().and().httpBasic().and().formLogin().and().sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(customJwtFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();

	}
}
