package com.innowise.document.service;

import com.innowise.document.entity.Document;
import com.innowise.document.entity.User;
import com.innowise.document.file.FileStorage;
import com.innowise.document.repository.DocumentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    FileStorage fileStorage;

    @Autowired
    DocumentRepo documentRepo;

    @Autowired
    UserService userService;

    @Autowired
    FileStorageService fileStorageService;

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
    public Document findByFilename(String filename){
        return documentRepo.findByFilename(filename)
                .orElseThrow(() -> new RuntimeException("Document with filename: '" + filename + "' not found."));
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
        if(checkDiffDate( document) < document.getContractTerm()){
            document.setExpired(false);
        }
        else{
            document.setExpired(true);
        }
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
        if(checkDiffDate(document) < document.getContractTerm()){
            document.setExpired(false);
        } else{
            document.setExpired(true);
        }
       return documentRepo.save(document);
    }

    @Override
    public void deleteAll(){
        documentRepo.deleteAll();
    }

    @Override
    public void deleteById(Long id){
        fileStorageService.deleteFile(getById(id));
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
        Pageable pageable = PageRequest.of(page, size);
        User user = userService.findUserByUsername(username);
        return documentRepo.findAllByUser(user, pageable);
    }

    @Override
    public Page<Document> findAllPage(  int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return documentRepo.findAll(pageable);
    }


    private long checkDiffDate(Document document) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        LocalDate today = LocalDate.now();
        LocalDate old = LocalDate.parse(sdf.format(document.getDateOfCreation()));
        long di = ChronoUnit.DAYS.between(old, today);
        return di;
    }
}