package com.innowise.document.service;

import com.innowise.document.entity.Document;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DocumentService extends RestService<Document>{

    List<Document> findAllDocumentsByUserId(Long user_id);

    Document findDocumentByTitle(String title);

    List<Document> findDocumentsByExpired(boolean expired);

    List<Document> findDocumentsByExpiredNot(boolean expired);

    boolean existsByTitle(String title);

    Document addDocumentByUserId(Long user_id, Document document);

    Document updateDocumentByUserId(Long user_id, Document document);

    void deleteAll();

    List<Document> findAllDocumentsByUserName(String username);

    Page<Document> findAllPageByUser(String username, int page, int size);

    Page<Document> findAllPage(  int page, int size);
}
