package com.elearn.app.Config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.security.sasl.AuthenticationException;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

//        Authorization : Bearer 235wffaghfafhal01
        String authorizationHeader= request.getHeader("Authorization");
        System.out.println("Authorization Header "+authorizationHeader);
        String username=null;
        String jwtToken=null;
        if(authorizationHeader!=null && authorizationHeader.startsWith("Bearer")){
            jwtToken=authorizationHeader.substring(7);
            username=jwtUtil.extractUserName(jwtToken);
            if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
                   // validation
                UserDetails userDetails= userDetailsService.loadUserByUsername(username);
                if(jwtToken!=null && jwtUtil.validateToken(jwtToken,userDetails.getUsername())){
                    UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }else{
//            throw new AuthenticationException("Invalid Exception");
            System.out.println("Invalid token");
        }
        filterChain.doFilter(request,response);

    }
}
