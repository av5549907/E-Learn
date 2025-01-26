package com.elearn.app.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
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
                 //1-header                  |                2.Payload                                                 |            3. Signature
    //   eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c

    @Bean
    public SecurityFilterChain  securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity.authorizeHttpRequests(e->
//                e.requestMatchers(HttpMethod.GET,"/api/v1/categories","/api/v1/videos").permitAll().anyRequest().authenticated());

          httpSecurity.cors(AbstractHttpConfigurer::disable);
          httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.authorizeHttpRequests(auth-> auth.requestMatchers(HttpMethod.GET,"api/v1/**").hasRole(AppConstants.GUEST)
                   .requestMatchers(HttpMethod.POST,"api/v1/**").hasRole(AppConstants.ADMIN)
                   .requestMatchers(HttpMethod.PUT,"api/v1/**").hasRole((AppConstants.ADMIN))
                   .requestMatchers(HttpMethod.DELETE,"api/v1/**").hasRole((AppConstants.ADMIN))
//                   .requestMatchers("/all").permitAll()
                   .anyRequest()
                   .authenticated()
        );
        //As per request we are disabling

//        httpSecurity.authorizeHttpRequests(auth->{
//            auth.requestMatchers(HttpMethod.GET,"/api/v1/categories","/api/v1/videos").permitAll()
//                    .requestMatchers("/client-login","/client-login-process","/api/v1/courses").permitAll()
//                    .requestMatchers(HttpMethod.POST,"/api/v1/users").permitAll()
//                    .anyRequest()
//                    .authenticated();
//        });
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

        httpSecurity.logout(logout->{
           logout.logoutUrl("/logout");
        });
        httpSecurity.cors(Customizer.withDefaults());
        httpSecurity.httpBasic(Customizer.withDefaults());


        return  httpSecurity.build();
    }


}
