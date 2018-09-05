package com.giroux.kevin.dofustuff.configuration;

import com.giroux.kevin.dofustuff.security.JwtAuthenticationProvider;
import com.giroux.kevin.dofustuff.security.JwtAuthenticationSuccessHandler;
import com.giroux.kevin.dofustuff.security.JwtAuthenticationTokenFilter;
import com.giroux.kevin.dofustuff.security.JwtAuthenticationUnauthorizedHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collections;

/**
 * Configuration de la gestion de la sécurité
 *
 * @author kgiroux
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(WebSecurityConfig.class);

    /**
     * Gestionnaire d'erreur
     */
    private final JwtAuthenticationUnauthorizedHandler unauthorizedHandler;

    /**
     * Provider de l'authorisation (check le token et fournit les informations
     * d'un utilisateur)
     */
    private final JwtAuthenticationProvider authenticationProvider;

    /**
     * Element du header dans lequel se trouve le token
     */
    private static final String tokenHeader = "Authorization";

    @Autowired
    public WebSecurityConfig(JwtAuthenticationUnauthorizedHandler unauthorizedHandler, JwtAuthenticationProvider authenticationProvider) {
        this.unauthorizedHandler = unauthorizedHandler;
        this.authenticationProvider = authenticationProvider;
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return new ProviderManager(Collections.singletonList(authenticationProvider));
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
        LOGGER.info("WebSecurity");
        web.ignoring()
                .antMatchers("/authenticate")
                .and()
                .ignoring()
                .antMatchers(HttpMethod.PUT, "/users");
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        LOGGER.info("HttpSecurity");
        httpSecurity.csrf()
                .disable()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .antMatcher("/users")
                .exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        httpSecurity.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
        httpSecurity.headers().cacheControl();
    }

}
