package com.innowise.document.entity.documents;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "rental_contract")
public class RentalContract extends DocumentPattern{ //аренда

    private String rentalObject;

    @Temporal(TemporalType.DATE)
    private Date startRental;

    @Temporal(TemporalType.DATE)
    private Date endRental;

    private Integer rentalPrice;
}
