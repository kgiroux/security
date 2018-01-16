package com.giroux.kevin.dofustuff.configuration;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.giroux.kevin.dofustuff.security.JwtAuthenticationProvider;
import com.giroux.kevin.dofustuff.security.JwtAuthenticationSuccessHandler;
import com.giroux.kevin.dofustuff.security.JwtAuthenticationTokenFilter;
import com.giroux.kevin.dofustuff.security.JwtAuthenticationUnauthorizedHandler;

/**
 * Configuration de la gestion de la sécurité
 * 
 * @author scadot
 *
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	/**
	 * Gestionnaire d'erreur
	 */
	@Autowired
	private JwtAuthenticationUnauthorizedHandler unauthorizedHandler;

	/**
	 * Provider de l'authorisation (check le token et fournit les informations
	 * d'un utilisateur)
	 */
	@Autowired
	private JwtAuthenticationProvider authenticationProvider;

	/**
	 * Element du header dans lequel se trouve le token
	 */
	private static final String tokenHeader = "Authorization";

	@Bean
	@Override
	public AuthenticationManager authenticationManager() throws Exception {
		return new ProviderManager(Arrays.asList(authenticationProvider));
	}

	/**
	 * Init du filter du token
	 * 
	 * @return
	 * @throws Exception
	 */
	private JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
		JwtAuthenticationTokenFilter authenticationTokenFilter = new JwtAuthenticationTokenFilter(tokenHeader);
		authenticationTokenFilter.setAuthenticationManager(authenticationManager());
		authenticationTokenFilter.setAuthenticationSuccessHandler(new JwtAuthenticationSuccessHandler());
		return authenticationTokenFilter;
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/authenticate");
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf().disable().authorizeRequests().anyRequest().authenticated().and().exceptionHandling()
				.authenticationEntryPoint(unauthorizedHandler).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		httpSecurity.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

		httpSecurity.headers().cacheControl();
	}

}
