package com.innowise.document.controller;

import com.innowise.document.entity.Document;
import com.innowise.document.entity.User;
import com.innowise.document.service.FilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/filter")
@CrossOrigin(origins = "*")
public class FilterController {

    @Autowired
    FilterService filterService;



    @GetMapping("documents-all")
    public ResponseEntity<Page<Document>> findAllByFilter(@RequestParam(required = false) String fromDate,
                                                          @RequestParam(required = false) String toDate,
                                                          @RequestParam(required = false) String title,
                                                          @RequestParam(required = false) String customer,
                                                          @RequestParam(required = false) String username,
                                                          @RequestParam(required = false) String exp,
                                                          @RequestParam(defaultValue = "0") int page,
                                                          @RequestParam int size,
                                                          @RequestParam String sortField,
                                                          @RequestParam String sortOrder) throws ParseException{

        Page<Document> lst =  filterService.findAllDocsByFilter(title, customer, fromDate, toDate, username, exp,
                page, size,sortField, sortOrder);
        return new ResponseEntity<>(lst, HttpStatus.OK);
    }

    @GetMapping("users-all")
    public ResponseEntity<Page<User>> findAllByFilter(@RequestParam(required = false) String username,
                                                          @RequestParam(required = false) String name,
                                                          @RequestParam(required = false) String email,
                                                          @RequestParam(required = false) String activationCode,
                                                          @RequestParam(defaultValue = "0") int page,
                                                          @RequestParam int size,
                                                          @RequestParam String sortField,
                                                          @RequestParam String sortOrder){

        Page<User> lst =  filterService.findAllUsersByFilter(name, activationCode, email, username, page, size, sortField, sortOrder);
        return new ResponseEntity<>(lst, HttpStatus.OK);
    }
}
