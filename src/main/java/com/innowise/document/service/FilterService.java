package com.innowise.document.service;

import com.innowise.document.entity.Document;
import com.innowise.document.entity.User;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;

import java.text.ParseException;

public interface FilterService {

    Page<Document> findAllDocsByFilter(String title, String customer, String fromDate, String toDate,
                                              String username, String expired, int page, int size, String sortField,
                                              String sortOrder) throws ParseException;

    Page<User> findAllUsersByFilter(String name, String activationCode, String email, String username, int page, int size,
                                    String sortField, String sortOrder);

    Predicate createPredicateForDocument(String title, String customer, String fromDate, String toDate,
                                                String username, String expired) throws ParseException;

    Predicate createPredicateForUser(String name, String email, String username, String activationCode);
}