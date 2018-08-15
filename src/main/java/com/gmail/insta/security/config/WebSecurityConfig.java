package com.gmail.insta.security.config;

import com.gmail.insta.security.filters.TokenAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthenticationProvider authenticationProvider;

    private final TokenAuthFilter tokenAuthFilter;

    @Autowired
    public WebSecurityConfig(AuthenticationProvider authenticationProvider, TokenAuthFilter tokenAuthFilter) {
        this.authenticationProvider = authenticationProvider;
        this.tokenAuthFilter = tokenAuthFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(tokenAuthFilter, BasicAuthenticationFilter.class)
                .antMatcher("/**")
                .authenticationProvider(authenticationProvider)
                .authorizeRequests()
                .antMatchers("/like").hasAuthority("USER")
                .antMatchers("/likes").hasAuthority("USER")
                .antMatchers("/like/**").hasAuthority("USER")
                .antMatchers("/additem").hasAuthority("USER")
                .antMatchers("/addphoto/**").hasAuthority("USER")
                .antMatchers("/login").permitAll();
        http.csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }
}
