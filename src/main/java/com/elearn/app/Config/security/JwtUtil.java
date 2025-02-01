package com.elearn.app.Config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;


@Component
public class JwtUtil {

    private Key key= Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private long jwtExpiration=5*60*1000; //5 minutes in milliseconds

    // extract username from token

    public String extractUserName(String token){
        return extractClaim(token,Claims::getSubject);
    }

    public <T> T extractClaim(String token , Function<Claims,T> claimResolver){
      final  Claims claims=extractAllClaims(token);
      return  claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token).getBody();
    }

    public String generateToken(String username){
        return  createToken(username);
    }

    private String createToken(String username){
        return Jwts.builder()
                .setSubject(username)
//                .setPayload()
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+jwtExpiration))
                .signWith(key).compact();
    }


    public boolean validateToken(String token,String username){
        String tokenUsername=extractUserName(token);
        return (username.equals(tokenUsername))&& !isTokenExpired(token);
    }

    public  boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }


}

