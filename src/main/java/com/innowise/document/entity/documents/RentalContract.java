package com.innowise.document.entity.documents;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "rental_contract")
public class RentalContract extends DocumentPattern { //аренда

    @NotBlank
    private String rentalObject;

    @Temporal(TemporalType.DATE)
    private Date startRental;

    @Temporal(TemporalType.DATE)
    private Date endRental;

    private Integer rentalPrice;
}
