package com.elearn.app.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain  securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity.authorizeHttpRequests(e->
//                e.requestMatchers(HttpMethod.GET,"/api/v1/categories","/api/v1/videos").permitAll().anyRequest().authenticated());
        httpSecurity.authorizeHttpRequests(auth->{
            auth.requestMatchers(HttpMethod.GET,"/api/v1/categories","/api/v1/videos").permitAll()
                    .requestMatchers(HttpMethod.GET,"/api/v1/courses").permitAll().anyRequest().authenticated();
        });
        httpSecurity.formLogin(Customizer.withDefaults());
        httpSecurity.httpBasic(Customizer.withDefaults());
        return  httpSecurity.build();
    }

}
