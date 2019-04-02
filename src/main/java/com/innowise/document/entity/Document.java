package com.innowise.document.entity;



import javax.persistence.*;
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

    private String filename;

    private Integer contractTerm;  //count days

    private boolean expired;

    public Document() {
    }

    public Document(Date dateOfCreation, String title, User user, String documentDescription,
                    String customer, String filename, Integer contractTerm, boolean expired){
        this.dateOfCreation = dateOfCreation;
        this.title = title;
        this.user = user;
        this.documentDescription = documentDescription;
        this.customer = customer;
        this.filename = filename;
        this.contractTerm = contractTerm;
        this.expired = expired;
    }

    public Document(Date dateOfCreation, String title, User user, String documentDescription,
                    String customer, Integer contractTerm, boolean expired){
        this.dateOfCreation = dateOfCreation;
        this.title = title;
        this.user = user;
        this.documentDescription = documentDescription;
        this.customer = customer;
        this.contractTerm = contractTerm;

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

    public boolean getExpired(){
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

    public String getFilename(){
        return filename;
    }

    public void setFilename(String filename){
        this.filename = filename;
    }
}
