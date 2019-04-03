package com.innowise.document.entity;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "document")
public class Document implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date dateOfCreation;

    private String title;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    private String documentDescription;

    private String customer;

    private String filename;

    private Integer contractTerm;  //count days

    private boolean expired;

}
