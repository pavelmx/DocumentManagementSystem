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
}
