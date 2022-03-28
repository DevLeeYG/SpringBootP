package com.example.demo.config;

import com.example.demo.security.JwtAuthenticationFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@Slf4j

public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        //http 시큐리티 빌더
        http.cors()//webMvcConfig에서 설정해서 기본 cors설정
                .and()
                .csrf().disable()
                .httpBasic().disable()//토큰을 사용하므로 basic 인증 disable.
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()//세션 기반이 아님
                .authorizeRequests()// /와 /auth/** 경로는 인증안해도 됨
                .antMatchers("/","/auth/**").permitAll()// 이외는 인증해야됨
                .anyRequest().authenticated();
        //filter등록
        //매 요청마다
        //CorsFilter 실행한후에 -> jwtAuthenticationfilter 를 실행한다
        http.addFilterAfter(
                jwtAuthenticationFilter,CorsFilter.class
        );
    }


}
