package com.innowise.document.entity.documents;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "credit_contract")
public class CreditContract extends DocumentPattern {

    private Float creditAmount;

    private Float annualInterest;

    private Integer term;
}
