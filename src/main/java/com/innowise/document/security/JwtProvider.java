package com.innowise.document.security;


import java.util.Date;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;


@Component
public class JwtProvider {

    @Value("${auth.jwtSecret}")
    private String jwtSecret;

    @Value("${auth.jwtExpiration}")
    private int jwtExpiration;

    public String generateJwtToken(Authentication authentication) {

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpiration*1000))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            new ResponseMessage("Invalid JWT signature -> Message: {"+e+"}");
        } catch (MalformedJwtException e) {
            new ResponseMessage("Invalid JWT token -> Message: {"+e+"}");
        } catch (ExpiredJwtException e) {
            new ResponseMessage("Expired JWT token -> Message: {"+e+"}");
        } catch (UnsupportedJwtException e) {
            new ResponseMessage("Unsupported JWT token -> Message: {"+e+"}");
        } catch (IllegalArgumentException e) {
            new ResponseMessage("JWT claims string is empty -> Message: {"+e+"}");
        }

        return false;
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody().getSubject();
    }
}