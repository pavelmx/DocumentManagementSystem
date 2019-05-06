package com.innowise.document.service.filters;

import com.innowise.document.entity.FilterObject;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;

import java.text.ParseException;

public interface FilterService<T> {

    Page<T> findAllByFilter(FilterObject obj, int page, int size) throws ParseException;

    Predicate createPredicate(FilterObject obj) throws ParseException;

}
