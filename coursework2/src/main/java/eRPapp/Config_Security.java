package eRPapp;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import eRPapp.services.UserInfoService;

@EnableWebSecurity
@Configuration
public class Config_Security extends WebSecurityConfigurerAdapter{
	
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
	
	protected void configure(HttpSecurity http) throws Exception {
		http
		//.httpBasic().disable()
		.csrf().disable()
		.authorizeRequests()
			.antMatchers("/dashboard/**").hasRole("ADMIN")
			.antMatchers("/adminLoggedIn").hasRole("ADMIN")
			.antMatchers("/voterLoggedIn").hasRole("VOTER")
			.antMatchers("/accessCheck").hasAnyRole("ADMIN", "VOTER")
			.antMatchers("/home/**").hasRole("VOTER")
			.antMatchers("/vote/**").hasRole("VOTER")
				.anyRequest().authenticated()
				
			.and()
				.formLogin()
				.failureForwardUrl("/userLogin")
					.loginPage("/")
					.usernameParameter("email")// use email as username
					.defaultSuccessUrl("/accessCheck")
					.loginProcessingUrl("/accessCheck")
					.failureUrl("/accessDenied")
					.permitAll()
					
			.and()
				.logout()
					.logoutUrl("/userLogout")
//					.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
// 					.logoutSuccessUrl("/userLogin")
					.invalidateHttpSession(true) 
					.permitAll()
			.and()
				.rememberMe()
			.and()
				.authorizeRequests()
				.antMatchers("/").permitAll()
				.antMatchers("/signup/**").permitAll()
				.antMatchers("/scripts/**").permitAll()
				.antMatchers("/views/**").permitAll()
				.antMatchers("/resources/**").permitAll().anyRequest().permitAll()
			.and()
				.exceptionHandling().accessDeniedPage("/accessDenied");
//				     .and()
//				        .csrf()
			
	}

}
