package com.innowise.document.entity.documents;

import com.innowise.document.entity.CatalogOfOperationMode;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "work_contract")
public class WorkContract extends DocumentPattern {

    @Temporal(TemporalType.DATE)
    private Date startWork;

    private Integer term;

    @NotBlank
    private String position;

    @ManyToOne
    @JoinColumn(name = "id_operationMode")
    private CatalogOfOperationMode operationMode;

    private Integer workingHours;

    private Integer holiday;

    private Integer salary;


}
