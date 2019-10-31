package com.example.library.Login;

import com.example.library.Login.Token.BearerConfig;
import com.example.library.Login.Token.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenProvider tokenProvider;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors()
                .and()
                .formLogin().disable()
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers("/books").permitAll()
                .antMatchers("/login/auth").permitAll()
                .antMatchers("/login/register").permitAll()
                .antMatchers("/login/update").authenticated()
                .antMatchers("/books/myBooks").hasRole(Roles.USER)
                .antMatchers("/books/return/**").hasRole(Roles.USER)
                .antMatchers("/books/rent/**").hasRole(Roles.USER)
                .antMatchers("/login/delete").hasRole(Roles.USER)
                .anyRequest().hasRole(Roles.ADMIN)
                .and()
                .apply(new BearerConfig(tokenProvider))
        ;
    }
}
