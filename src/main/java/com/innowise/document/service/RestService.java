package com.innowise.document.service;

import org.springframework.data.domain.Page;

import java.util.List;

public interface RestService<T> {

    List<T> getAll();

    T getById(Long id);

    void deleteById(Long id);

    boolean existsById(Long id);
}
