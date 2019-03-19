package com.innowise.document.service;

import com.innowise.document.entity.User;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface RestService<T> {

    List<T> getAll();

    T getById(Long id);

    void deleteById(Long id);

    boolean existsById(Long id);
}
