package com.innowise.document.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Entity
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

    private Integer contractTerm;  //count month

    private byte[] file;

    private boolean expired;


    public Document() {
    }

    public Document( Date dateOfCreation,  String title,  User user,  String documentDescription,
                      String customer,  Integer contractTerm,  byte[] file,  boolean expired){
        this.dateOfCreation = dateOfCreation;
        this.title = title;
        this.user = user;
        this.documentDescription = documentDescription;
        this.customer = customer;
        this.contractTerm = contractTerm;
        this.file = file;
        this.expired = expired;
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User documentOwner) {
        this.user = documentOwner;
    }

    public String getDocumentDescription() {
        return documentDescription;
    }

    public void setDocumentDescription(String documentDescription) {
        this.documentDescription = documentDescription;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Integer getContractTerm() {
        return contractTerm;
    }

    public void setContractTerm(Integer contractTerm) {
        this.contractTerm = contractTerm;
    }

    public byte[] getFile(){
        return file;
    }

    public void setFile(byte[] file){
        this.file = file;
    }

    public boolean isExpired(){
        return expired;
    }

    public void setExpired(boolean expired){
        this.expired = expired;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }
}
