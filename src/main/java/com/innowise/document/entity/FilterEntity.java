package com.innowise.document.entity;

import lombok.Data;

@Data
public class FilterEntity {

    private String title;
    private String customer;
    private String fromDate;
    private String toDate;
    private String username;
    private String expired;
    private String name;
    private String email;
    private String activationCode;
    private String sortField;
    private String sortOrder;
}
