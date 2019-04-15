package com.innowise.document.entity.documents;

import com.innowise.document.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class DocumentPattern implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Temporal(TemporalType.DATE)
    protected Date dateOfCreation;

    protected String title;

    protected String clientFullName;

    protected String clientAdress;

    protected String otherInfo;

    protected boolean isActive;

    @Temporal(TemporalType.TIMESTAMP)
    protected Date lastChange;

    @ManyToOne
    @JoinColumn(name = "id_user")
    protected User user;
}
