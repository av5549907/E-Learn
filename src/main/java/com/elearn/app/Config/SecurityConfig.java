package com.elearn.app.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig {


    @Bean
    public PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }


//    @Bean
//    public UserDetailsService userDetailsService(){
//        InMemoryUserDetailsManager userDetailsManager=new InMemoryUserDetailsManager();
//        userDetailsManager.createUser(
//                User.withDefaultPasswordEncoder()
//                        .username("adarsh")
//                        .password("adarsh")
//                        .roles("ADMIN").build());
//        userDetailsManager.createUser(
//                User.withDefaultPasswordEncoder()
//                        .username("priya")
//                        .password("priya")
//                        .roles("USER")
//                        .build());
//        return userDetailsManager;
//    }

    @Bean
    public SecurityFilterChain  securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity.authorizeHttpRequests(e->
//                e.requestMatchers(HttpMethod.GET,"/api/v1/categories","/api/v1/videos").permitAll().anyRequest().authenticated());
        httpSecurity.authorizeHttpRequests(auth->{
            auth.requestMatchers(HttpMethod.GET,"/api/v1/categories","/api/v1/videos","/client-login","/client-login-process","/success").permitAll()
                    .requestMatchers(HttpMethod.GET,"/api/v1/courses").permitAll().anyRequest().authenticated();
        });
//        httpSecurity.formLogin(Customizer.withDefaults());
        httpSecurity.formLogin(form->{
            form.loginPage("/client-login");
            form.usernameParameter("username");
            System.out.println(form.usernameParameter("username"));
            form.passwordParameter("userpassword");
            System.out.println(form.passwordParameter("userpassword"));
            form.loginProcessingUrl("/client-login-process");
            form.successForwardUrl("/success");
        });
        httpSecurity.httpBasic(Customizer.withDefaults());
        httpSecurity.logout(logout->{
           logout.logoutUrl("/logout");
        });
        return  httpSecurity.build();
    }

}
