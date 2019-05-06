package com.innowise.document.controller.documents;

import com.innowise.document.entity.FilterObject;
import com.innowise.document.entity.documents.CooperationContract;
import com.innowise.document.service.documents.DocumentService;
import com.innowise.document.service.filters.FilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cooperation-contract")
@CrossOrigin(origins = "*")
public class CooperationContractController {

    @Autowired
    DocumentService<CooperationContract> cooperationContractService;

    @Autowired
    FilterService<CooperationContract> filterService;


    @GetMapping("get/{id}")
    public ResponseEntity<CooperationContract> getById(@PathVariable("id") Long id) {
        CooperationContract cooperationContract = cooperationContractService.getById(id);
        return new ResponseEntity<>(cooperationContract, HttpStatus.OK);
    }

    @GetMapping("get-username/{username}")
    public ResponseEntity<List<CooperationContract>> getAllByUsername(@PathVariable("username") String username){
        List<CooperationContract> cooperationContractList = new ArrayList<>();
        cooperationContractService.getAllByUsername(username).forEach(cooperationContractList::add);
        return new ResponseEntity<>(cooperationContractList, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("get-all")
    public ResponseEntity<List<CooperationContract>> getAll() {
        List<CooperationContract> cooperationContractList = new ArrayList<>();
        cooperationContractService.getAll().forEach(cooperationContractList::add);
        return new ResponseEntity<>(cooperationContractList, HttpStatus.OK);
    }

    @PostMapping("add/{username}")
    public ResponseEntity<CooperationContract> addByUserId(@PathVariable("username") String username, @RequestBody CooperationContract doc) {
        CooperationContract cooperationContract = cooperationContractService.addByUsername(username, doc);
        return new ResponseEntity<>(cooperationContract, HttpStatus.CREATED);
    }

    @PutMapping("update/{username}")
    public ResponseEntity<CooperationContract> updateByUserId(@PathVariable("username") String username,@RequestBody CooperationContract doc) {
        CooperationContract cooperationContract = cooperationContractService.updateByUsername(username, doc);
        return new ResponseEntity<>(cooperationContract, HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        cooperationContractService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("delete-all/{username}")
    public ResponseEntity<Void> deleteAllByUsername(@PathVariable("username") String username) {
        cooperationContractService.getAllByUsername(username).forEach(cooperationContract -> deleteById(cooperationContract.getId()));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("delete-all")
    public ResponseEntity<Void> deleteAll() {
        cooperationContractService.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("page/get")
    public ResponseEntity<Page<CooperationContract>> getAllByUsername(
            @RequestBody FilterObject obj, @RequestParam int page, @RequestParam int size) throws ParseException{
        Page<CooperationContract> cooperationContractList = filterService.findAllByFilter(obj, page, size);
        return new ResponseEntity<>(cooperationContractList, HttpStatus.OK);
    }
}
