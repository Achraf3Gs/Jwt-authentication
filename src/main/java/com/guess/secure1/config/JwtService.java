package com.guess.secure1.config;


import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.guess.secure1.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
@RequiredArgsConstructor
public class JwtService {

    private static final String secret="achrafou";
    private static final String client="123456789";

    private static final String SECRET_KEY="8ba5bcf689a7b166bdbf984fb4668ecce6070d01db7e7078d2994ffffac48034";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private  User user;



    public <T> T extractClaim(String token , Function<Claims,T> claimsResolver){
        final Claims claims= extractAllClaims(token);
        return claimsResolver.apply(claims);
    }



    public String generateToken(User user) {
        Map<String,Object> claims = new HashMap<>();
        claims.put("id",user.getId()+"");
        claims.put("username", user.getFirstname());
        claims.put("role", user.getRole());
                return Jwts
                .builder()
                        .setClaims(claims)
                .setSubject(String.format("%s",user.getEmail()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*120))
                .signWith(getSignInKey(),SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username= extractUsername(token);
        return (username.equals(userDetails.getUsername()))&& !isTokenExpired(token);
    }
    public boolean isTokenAdminValid(String token, UserDetails userDetails) {
        final String username= extractUsername(token);
        return (username.equals(userDetails.getUsername()))&& !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims  extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes= Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

   boolean isValidSecretAndClientKeys(String secretKey, String clientKey) {
        // Implement your logic to validate the secret_key and client_key
        // Return true if they are valid, false if they are not
        return (secretKey != null && secretKey.equals(secret))
                && (clientKey != null && clientKey.equals(client));
    }



}

