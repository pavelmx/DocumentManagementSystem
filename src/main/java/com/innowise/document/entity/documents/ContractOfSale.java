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
@Table(name = "contract_of_sale")
public class ContractOfSale extends DocumentPattern{

    @NotBlank
    private String saleObject;

    @NotBlank
    private Float salingPrice;

    @NotBlank
    private Integer warrantyPeriod;
}
