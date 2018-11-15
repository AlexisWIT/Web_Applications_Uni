package eRPapp;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import eRPapp.services.UserInfoService;

@EnableWebSecurity
@Configuration
public class Config_Security {
	
	@Autowired DataSource dataSource;
	@Autowired private UserInfoService userInfoService;
	
	@Bean
	public PasswordEncoder passwordEncoder(){
		PasswordEncoder passwordencoder = new BCryptPasswordEncoder();
		return passwordencoder;
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder authMgrBdr) throws Exception {
		authMgrBdr.userDetailsService(userInfoService).passwordEncoder(passwordEncoder());
	}
	
	public void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
			.antMatchers("/dashboard/**").hasRole("ADMIN")
			.antMatchers("/signup/**").permitAll()
			.antMatchers("/adminLoggedIn").hasRole("ADMIN")
			.antMatchers("/voterLoggedIn").permitAll()
			.antMatchers("/accessCheck").permitAll()
			.antMatchers("/home/**").hasRole("USER")
			.antMatchers("/vote/**").hasRole("USER")
				.anyRequest().authenticated()
			.and()
				.formLogin()
				.failureForwardUrl("/userLogin")
					.loginPage("/userLogin")
					.defaultSuccessUrl("/voterLoggedIn")
					.loginProcessingUrl("/accessCheck")
					.failureUrl("/accessDenied")
					.permitAll()
			.and()
				.logout()
//					.logoutUrl("/userLogout")
//					.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//					.logoutSuccessUrl("/logout")
					.invalidateHttpSession(true) 
					.permitAll()
			.and()
				.exceptionHandling().accessDeniedPage("/access-denied")
//			       	.and()
//			        	.csrf()
			.and()
			    .requiresChannel()
			    	.anyRequest()
			    	.requiresSecure();
	}

}
