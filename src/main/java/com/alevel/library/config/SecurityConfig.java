package com.alevel.library.config;

import com.alevel.library.security.jwt.JwtConfigurer;
import com.alevel.library.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String ADMIN_ENDPOINT = "/admin/**";
    private static final String SIGN_IN_ENDPOINT = "/auth/login";
    private static final String SIGN_UP_ENDPOINT = "/auth/register";
    private final JwtTokenProvider jwtTokenProvider;
    private final RestAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    public SecurityConfig(JwtTokenProvider jwtTokenProvider, RestAuthenticationEntryPoint authenticationEntryPoint) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(SIGN_IN_ENDPOINT).permitAll()
                .antMatchers(SIGN_UP_ENDPOINT).permitAll()
                .antMatchers("/").permitAll()
                .antMatchers(ADMIN_ENDPOINT).hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider, authenticationEntryPoint));
    }
}
