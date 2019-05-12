package com.innowise.document.security;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String username;
    private Collection<? extends GrantedAuthority> authorities;
    private Date expiration;

    public JwtResponse(String token, String username, Collection<? extends GrantedAuthority> authorities, Date expiration){
        this.token = token;
        this.username = username;
        this.authorities = authorities;
        this.expiration = expiration;
    }
}
