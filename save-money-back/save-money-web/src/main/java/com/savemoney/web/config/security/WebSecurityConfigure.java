package com.savemoney.web.config.security;

import com.savemoney.web.config.security.service.CustomPasswordEncoder;
import com.savemoney.web.config.security.service.JwtAuthenticationFilter;
import com.savemoney.web.config.security.service.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfigure extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    public WebSecurityConfigure(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .authorizeRequests()
                        .antMatchers(HttpMethod.GET, "/api/").permitAll()
                        .antMatchers(HttpMethod.GET, "/api/steam/game/global/**").permitAll()
                        .antMatchers(HttpMethod.POST, "/api/token/").permitAll()
                        .antMatchers(HttpMethod.POST, "/api/user/").permitAll()
                        .antMatchers(HttpMethod.GET, "/api/test/**/").permitAll()
                        .antMatchers(HttpMethod.POST, "/api/test/**").permitAll()
                        .antMatchers(HttpMethod.PUT, "/api/test/**").permitAll()
                        .antMatchers(HttpMethod.DELETE, "/api/test/**").permitAll()
                        .anyRequest().hasAnyRole("USER")
                .and()
                    .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * 제외 항목
     * @param web
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new CustomPasswordEncoder();
    }

}
