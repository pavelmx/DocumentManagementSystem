package com.innowise.document.controller.documents;

import com.innowise.document.entity.FilterObject;
import com.innowise.document.entity.documents.CreditContract;
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
@RequestMapping("/credit-contract")
@CrossOrigin(origins = "*")
public class CreditContractController {

    @Autowired
    DocumentService<CreditContract> creditContractService;

    @Autowired
    FilterService<CreditContract> filterService;


    @GetMapping("get/{id}")
    public ResponseEntity<CreditContract> getById(@PathVariable("id") Long id) {
        CreditContract creditContract = creditContractService.getById(id);
        return new ResponseEntity<>(creditContract, HttpStatus.OK);
    }

    @GetMapping("get-username/{username}")
    public ResponseEntity<List<CreditContract>> getAllByUsername(@PathVariable("username") String username){
        List<CreditContract> creditContractList = new ArrayList<>();
        creditContractService.getAllByUsername(username).forEach(creditContractList::add);
        return new ResponseEntity<>(creditContractList, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("get-all")
    public ResponseEntity<List<CreditContract>> getAll() {
        List<CreditContract> creditContractList = new ArrayList<>();
        creditContractService.getAll().forEach(creditContractList::add);
        return new ResponseEntity<>(creditContractList, HttpStatus.OK);
    }

    @PostMapping("add/{username}")
    public ResponseEntity<CreditContract> addByUserId(@PathVariable("username") String username, @RequestBody CreditContract doc) {
        CreditContract creditContract = creditContractService.addByUsername(username, doc);
        return new ResponseEntity<>(creditContract, HttpStatus.CREATED);
    }

    @PutMapping("update/{username}")
    public ResponseEntity<CreditContract> updateByUserId(@PathVariable("username") String username,@RequestBody CreditContract doc) {
        CreditContract creditContract = creditContractService.updateByUsername(username, doc);
        return new ResponseEntity<>(creditContract, HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        creditContractService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("delete-all/{username}")
    public ResponseEntity<Void> deleteAllByUsername(@PathVariable("username") String username) {
        creditContractService.getAllByUsername(username).forEach(creditContract -> deleteById(creditContract.getId()));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("delete-all")
    public ResponseEntity<Void> deleteAll() {
        creditContractService.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("page/get")
    public ResponseEntity<Page<CreditContract>> getAllByUsername(
            @RequestBody FilterObject obj, @RequestParam int page, @RequestParam int size) throws ParseException{
        Page<CreditContract> contractList = filterService.findAllByFilter(obj, page, size);
        return new ResponseEntity<>(contractList, HttpStatus.OK);
    }
}
