package com.innowise.document.service;

import com.innowise.document.entity.Document;
import com.innowise.document.entity.User;
import com.innowise.document.repository.DocumentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    DocumentRepo documentRepo;

    @Autowired
    UserService userService;

    @Override
    public Document getById(Long id){
        return documentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Document with id: '" + id + "' not found."));
    }

    @Override
    public List<Document> findAllDocumentsByUserId(Long user_id){
        User user = userService.getById(user_id);
        return documentRepo.findAllByUser(user);
    }

    @Override
    public Document findDocumentByTitle(String title){
        return documentRepo.findByTitle(title)
                .orElseThrow(() -> new RuntimeException("Document with title: '" + title + "' not found."));
    }

    @Override
    public List<Document> findDocumentsByExpired(boolean expired){
        return documentRepo.findByExpired(expired);
    }

    @Override
    public List<Document> findDocumentsByExpiredNot(boolean expired){
        return documentRepo.findByExpiredNot(expired);
    }

    @Override
    public List<Document> findAllDocumentsByUserName(String username){
        User user = userService.findUserByUsername(username);
        return documentRepo.findAllByUser(user);
    }

    @Override
    public List<Document> getAll(){
        List<Document> docList = new ArrayList<>();
        documentRepo.findAll().forEach(docList::add);
        return docList;
    }

    @Override
    public Document addDocumentByUserId(Long user_id, Document document){
        User user = userService.getById(user_id);
        List<Document> docsThisUser = documentRepo.findAllByUser(user);
        if (!docsThisUser.isEmpty()) {
            for (Document doc : docsThisUser) {
                if (doc.getTitle().equals(document.getTitle()))
                    throw new EntityExistsException("Document with title: '" + document.getTitle() + "' exists.");
            }
        }
        if (!userService.existsById(user_id)) {
            throw new EntityNotFoundException("User with id: '" + user_id + "' not found.");
        }
        document.setUser(user);
        return documentRepo.save(document);
    }

    @Override
    public Document updateDocumentByUserId(Long user_id, Document document){
        if (!existsById(document.getId())) {
            throw new EntityExistsException("Document with id: '" + document.getId() + "' not found.");
        }
        if (!userService.existsById(user_id)) {
            throw new EntityNotFoundException("User with id: '" + user_id + "' not found.");
        }
        User user = userService.getById(user_id);
        document.setUser(user);
        return documentRepo.save(document);
    }

    @Override
    public void deleteAll(){
        documentRepo.deleteAll();
    }

    @Override
    public void deleteById(Long id){
        documentRepo.deleteById(id);
    }

    @Override
    public boolean existsById(Long id){
        return documentRepo.existsById(id);
    }

    @Override
    public boolean existsByTitle(String title){
        return documentRepo.existsByTitle(title);
    }

    @Override
    public Page<Document> findAllPageByUser(String username, int page, int size){
        Pageable pageable = new PageRequest(page, size);
        User user = userService.findUserByUsername(username);
        return documentRepo.findAllByUser(user, pageable);
    }

    @Override
    public Page<Document> findAllPage(  int page, int size){
        Pageable pageable = new PageRequest(page, size);
        return documentRepo.findAll(pageable);
    }
}