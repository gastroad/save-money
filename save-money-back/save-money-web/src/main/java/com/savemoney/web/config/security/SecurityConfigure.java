package com.savemoney.web.config.security;

import com.savemoney.web.config.security.service.JWTAuthenticationFilter;
import com.savemoney.web.config.security.service.JWTTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
class  SecurityConfigure extends WebSecurityConfigurerAdapter {

    private final JWTTokenProvider jwtTokenProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // 로그인 인증 미사용.
                .httpBasic().disable()
                // csrf 미사용.
                .csrf().disable()
                // session 미사용.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .authorizeRequests()
                        .antMatchers("/").permitAll()
                        .anyRequest().permitAll()
                .and()
                    .addFilterBefore(new JWTAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
    }

}
