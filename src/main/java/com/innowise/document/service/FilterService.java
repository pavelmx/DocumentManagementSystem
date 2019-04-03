package com.innowise.document.service;

import com.innowise.document.entity.Document;
import com.innowise.document.entity.FilterEntity;
import com.innowise.document.entity.User;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;

import java.text.ParseException;

public interface FilterService {

    Page<Document> findAllDocsByFilter(FilterEntity filterEntity, int page, int size) throws ParseException;

    Predicate createPredicateForDocument(FilterEntity filterEntity) throws ParseException;

    Page<User> findAllUsersByFilter(FilterEntity filterEntity, int page, int size);

    Predicate createPredicateForUser(FilterEntity filterEntity);
}