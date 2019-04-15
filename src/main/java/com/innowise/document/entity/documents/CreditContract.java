package com.innowise.document.entity.documents;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "credit_contract")
public class CreditContract extends DocumentPattern{

    @NotBlank
    private Float creditAmount;

    @NotBlank
    private Float annualInterest;

    @NotBlank
    private Integer term;
}
