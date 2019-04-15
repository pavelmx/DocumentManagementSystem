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
@Table(name = "cooperation_contract")
public class CooperationContract extends DocumentPattern{ //совместная деятельность

    @NotBlank
    private Integer term;

    @NotBlank
    private String kindOfActivity;

    @NotBlank
    private String clientResponsibility;

    @NotBlank
    private String creatorResponsibility;

    @NotBlank
    private String terminationConditions;
}
