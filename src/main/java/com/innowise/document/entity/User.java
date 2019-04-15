package com.innowise.document.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Proxy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user",
       uniqueConstraints = { @UniqueConstraint(columnNames = { "username" }),
                             @UniqueConstraint(columnNames = { "email" }) })
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 5, max = 20, message = "Username size between 5 and 20")
    private String username;

    @NotBlank
    @Size(min = 5, max = 50, message = "Full name size between 5 and 50")
    private String name;

    @NotBlank
    @Size(min = 6, max = 500)
    private String password;

    @Email(message = "Email is incorrect")
    @NotBlank
    @Size(min = 5, max = 50, message = "Email size between 5 and 50")
    private String email;

    private String activationCode;

    private String adress;

    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    @ManyToMany
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_role"))
    private Set<Role> roles = new HashSet<>();

    public User(String username,  String name, String password, String email){
        this.username = username;
        this.name = name;
        this.password = password;
        this.email = email;
    }
}
