package com.innowise.document.repository;

import com.innowise.document.entity.Document;
import com.innowise.document.entity.QDocument;
import com.innowise.document.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Repository
public interface DocumentRepo extends CustomRepo<Document, QDocument, Long>{

    Optional<Document> findByFilename(String filename);
    Page<Document> findAllByUser(User user, Pageable pageable);
    List<Document> findAllByUser(User user);
    Optional<Document> findByTitle(String title);
    List<Document> findByExpired(boolean expired);
    List<Document> findByExpiredNot(boolean expired);
    boolean existsByTitle(String title);
}


