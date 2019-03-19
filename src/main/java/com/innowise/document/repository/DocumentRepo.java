package com.innowise.document.repository;

import com.innowise.document.entity.Document;
import com.innowise.document.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentRepo extends JpaRepository<Document, Long> {

    Optional<List<Document>> findAllByUser(User user);
    Optional<Document> findByTitle(String title);
    Optional<List<Document>> findByExpired(boolean expired);
    Optional<List<Document>> findByExpiredNot(boolean expired);
    boolean existsByTitle(String title);
}
