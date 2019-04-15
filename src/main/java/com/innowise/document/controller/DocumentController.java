package com.innowise.document.controller;

import com.innowise.document.entity.Document;
import com.innowise.document.service.DocumentService;
import com.innowise.document.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/document")
@CrossOrigin(origins = "*")
public class DocumentController {

    @Autowired
    DocumentService documentService;

    @Autowired
    UserService userService;

    @GetMapping("get/{id}")
    public ResponseEntity<Document> getById(@PathVariable("id") Long id) {
        Document document = documentService.getById(id);
        return new ResponseEntity<>(document, HttpStatus.OK);
    }

    @GetMapping("user_id/{user_id}")
    public ResponseEntity<List<Document>> findAllDocumentsByUserId(@PathVariable("user_id") Long user_id){
        List<Document> docList = new ArrayList<>();
        documentService.findAllDocumentsByUserId(user_id).forEach(docList::add);
        return new ResponseEntity<>(docList, HttpStatus.OK);
    }

    @GetMapping("username/{username}")
    public ResponseEntity<List<Document>> findAllDocumentsByUserName(@PathVariable("username") String username){
        List<Document> docList = new ArrayList<>();
        documentService.findAllDocumentsByUserName(username).forEach(docList::add);
        return new ResponseEntity<>(docList, HttpStatus.OK);
    }

    @GetMapping("title/{title}")
    public ResponseEntity<Document> findDocumentByTitle(@PathVariable("title") String title){
        Document doc = documentService.findDocumentByTitle(title);
        return new ResponseEntity<>(doc, HttpStatus.OK);
    }

    @GetMapping("expired")
    public ResponseEntity<List<Document>> findDocumentsByExpired(){
        List<Document> docList = new ArrayList<>();
        documentService.findDocumentsByExpired(true).forEach(docList::add);
        return new ResponseEntity<>(docList, HttpStatus.OK);
    }

    @GetMapping("notexpired")
    public ResponseEntity<List<Document>> findDocumentsByExpiredNot(){
        List<Document> docList = new ArrayList<>();
        documentService.findDocumentsByExpiredNot(true).forEach(docList::add);
        return new ResponseEntity<>(docList, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("getall")
    public ResponseEntity<List<Document>> getAll() {
        List<Document> docList = new ArrayList<>();
        documentService.getAll().forEach(docList::add);
        return new ResponseEntity<>(docList, HttpStatus.OK);
    }

    @PostMapping("add/{user_id}")
    public ResponseEntity<Document> addDocumentByUserId(@PathVariable("user_id") Long user_id, @RequestBody Document doc) {
        Document document = documentService.addDocumentByUserId(user_id, doc);
        return new ResponseEntity<>(document, HttpStatus.CREATED);
    }

    @PutMapping("update/{user_id}")
    public ResponseEntity<Document> updateDocumentByUserId(@PathVariable("user_id") Long user_id,@RequestBody Document doc) {
        Document document = documentService.updateDocumentByUserId(user_id, doc);
        return new ResponseEntity<>(document, HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        documentService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("deleteall")
    public ResponseEntity<Void> deleteAll() {
        documentService.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("usernamepage/{username}")
    public ResponseEntity<Page<Document>> findAllPageDocumentsByUserName(@PathVariable("username") String username,
    @RequestParam(defaultValue = "0") int page, @RequestParam int size){
        Page<Document> docList = documentService.findAllPageByUser(username, page, size);
        return new ResponseEntity<>(docList, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("getallpage")
    public ResponseEntity<Page<Document>> findAllPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam int size) {
        Page<Document> docList = documentService.findAllPage(page, size);
        return new ResponseEntity<>(docList, HttpStatus.OK);
    }
}
