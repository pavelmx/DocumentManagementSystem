package com.innowise.document.service;

import java.util.List;

public interface RestService<T> {

    List<T> getAll();

    T getById(Long id);

    void deleteById(Long id);

    boolean existsById(Long id);
}
