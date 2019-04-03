package com.innowise.document.controller;

import com.innowise.document.entity.Document;
import com.innowise.document.entity.FilterEntity;
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


    @PostMapping("documents-all")
    public ResponseEntity<Page<Document>> findAllDocsByFilter(@RequestBody FilterEntity filter,
                                                          @RequestParam(defaultValue = "0") int page,
                                                          @RequestParam int size) throws ParseException{
        Page<Document> lst =  filterService.findAllDocsByFilter(filter, page, size);
        return new ResponseEntity<>(lst, HttpStatus.OK);
    }

    @PostMapping("users-all")
    public ResponseEntity<Page<User>> findAllUsersByFilter(@RequestBody FilterEntity filter,
                                                      @RequestParam(defaultValue = "0") int page,
                                                      @RequestParam int size){
        Page<User> lst =  filterService.findAllUsersByFilter(filter, page, size);
        return new ResponseEntity<>(lst, HttpStatus.OK);
    }
}
