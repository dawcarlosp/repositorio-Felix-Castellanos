package com.prueba.api.jwt;

import com.prueba.api.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${key}")
    private String KEY;

    private SecretKey signingKey;

    @PostConstruct
    public void init() {
        try {
            byte[] keyBytes = Decoders.BASE64.decode(KEY);
            if (keyBytes.length >= 32) {
                this.signingKey = Keys.hmacShaKeyFor(keyBytes);
                return;
            }
        } catch (Exception ignored) {
        }
        this.signingKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    public String getToken(User user) {
        return getToken(new HashMap<>(), user);
    }

    private <K, V> String getToken(Map<String,Object> extraClaims, User user) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .claim("idUser", user.getId())
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*24*60))
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUsernameToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public boolean isValid(String token, UserDetails userDetails) {
        final String username = getUsernameToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public Claims getClaims(String token){
        return  Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T getClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = getClaims(token);
        return claimsResolver.apply(claims);
    }

    private Date getExpiration(String token){
        return getClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token){
        return getExpiration(token).before(new Date());
    }
}
