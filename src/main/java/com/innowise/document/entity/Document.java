package com.innowise.document.entity;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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

    @NotBlank
    private String title;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @NotBlank
    private String documentDescription;

    @NotBlank
    private String customer;

    private String filename;

    private Integer contractTerm;  //count days

    private boolean expired;
}
