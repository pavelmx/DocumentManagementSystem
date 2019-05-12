package com.innowise.document.entity;

import lombok.Data;

@Data
public class FilterObject {

    private String title;
    private String clientFullname;
    private String dateOfCreation;
    private String lastChange;
    private String isActive;
    private String sortField;
    private String sortOrder;
    private String fromDate;
    private String toDate;

    private String username;
    private String name;
    private String email;
    private String activationCode;
    ///work
    private Integer fromSalary;
    private Integer toSalary;
    private String position;
    private String placeOfWork;
    ///rental
    private String fromRental;
    private String toRental;
    private Integer fromRentalPrice;
    private Integer toRentalPrice;
    private String rentalObject;
    ///credit
    private Float toCreditAmount;
    private Float toAnnualInterest;
    private Integer toTerm;
    private Float fromCreditAmount;
    private Float fromAnnualInterest;
    private Integer fromTerm;
    //sale
    private String saleObject;
    private Float fromSalingPrice;
    private Integer fromWarrantyPeriod;
    private Float toSalingPrice;
    private Integer toWarrantyPeriod;
    ///cooperation
    private String kindOfActivity;
    private Integer toCooperationTerm;
    private Integer fromCooperationTerm;
}
