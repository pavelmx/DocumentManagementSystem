package com.innowise.document.entity.documents;

import lombok.*;

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


    private Float salingPrice;


    private Integer warrantyPeriod;
}
