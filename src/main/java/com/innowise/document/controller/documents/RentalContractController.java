package com.innowise.document.controller.documents;

import com.innowise.document.entity.documents.RentalContract;
import com.innowise.document.service.documents.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/rental-contract")
@CrossOrigin(origins = "*")
public class RentalContractController {

    @Autowired
    DocumentService<RentalContract> rentalContractService;


    @GetMapping("get/{id}")
    public ResponseEntity<RentalContract> getById(@PathVariable("id") Long id) {
        RentalContract rentalContract = rentalContractService.getById(id);
        return new ResponseEntity<>(rentalContract, HttpStatus.OK);
    }

    @GetMapping("get-username/{username}")
    public ResponseEntity<List<RentalContract>> getAllByUsername(@PathVariable("username") String username){
        List<RentalContract> rentalContractList = new ArrayList<>();
        rentalContractService.getAllByUsername(username).forEach(rentalContractList::add);
        return new ResponseEntity<>(rentalContractList, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("get-all")
    public ResponseEntity<List<RentalContract>> getAll() {
        List<RentalContract> rentalContractList = new ArrayList<>();
        rentalContractService.getAll().forEach(rentalContractList::add);
        return new ResponseEntity<>(rentalContractList, HttpStatus.OK);
    }

    @PostMapping("add/{username}")
    public ResponseEntity<RentalContract> addByUserId(@PathVariable("username") String username, @RequestBody RentalContract doc) {
        RentalContract rentalContract = rentalContractService.addByUsername(username, doc);
        return new ResponseEntity<>(rentalContract, HttpStatus.CREATED);
    }

    @PutMapping("update/{username}")
    public ResponseEntity<RentalContract> updateByUserId(@PathVariable("username") String username,@RequestBody RentalContract doc) {
        RentalContract rentalContract = rentalContractService.updateByUsername(username, doc);
        return new ResponseEntity<>(rentalContract, HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        rentalContractService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("delete-all/{username}")
    public ResponseEntity<Void> deleteAllByUsername(@PathVariable("username") String username) {
        rentalContractService.getAllByUsername(username).forEach(rentalContract -> deleteById(rentalContract.getId()));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("delete-all")
    public ResponseEntity<Void> deleteAll() {
        rentalContractService.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
