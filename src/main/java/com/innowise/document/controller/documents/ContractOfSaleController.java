package com.innowise.document.controller.documents;

import com.innowise.document.entity.FilterObject;
import com.innowise.document.entity.documents.ContractOfSale;
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
@RequestMapping("/sale-contract")
@CrossOrigin(origins = "*")
public class ContractOfSaleController {

    @Autowired
    DocumentService<ContractOfSale> contractOfSaleService;

    @Autowired
    FilterService<ContractOfSale> filterService;


    @GetMapping("get/{id}")
    public ResponseEntity<ContractOfSale> getById(@PathVariable("id") Long id) {
        ContractOfSale contractOfSale = contractOfSaleService.getById(id);
        return new ResponseEntity<>(contractOfSale, HttpStatus.OK);
    }

    @GetMapping("get-username/{username}")
    public ResponseEntity<List<ContractOfSale>> getAllByUsername(@PathVariable("username") String username){
        List<ContractOfSale> contractOfSaleList = new ArrayList<>();
        contractOfSaleService.getAllByUsername(username).forEach(contractOfSaleList::add);
        return new ResponseEntity<>(contractOfSaleList, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("get-all")
    public ResponseEntity<List<ContractOfSale>> getAll() {
        List<ContractOfSale> contractOfSaleList = new ArrayList<>();
        contractOfSaleService.getAll().forEach(contractOfSaleList::add);
        return new ResponseEntity<>(contractOfSaleList, HttpStatus.OK);
    }

    @PostMapping("add/{username}")
    public ResponseEntity<ContractOfSale> addByUserId(@PathVariable("username") String username, @RequestBody ContractOfSale doc) {
        ContractOfSale contractOfSale = contractOfSaleService.addByUsername(username, doc);
        return new ResponseEntity<>(contractOfSale, HttpStatus.CREATED);
    }

    @PutMapping("update/{username}")
    public ResponseEntity<ContractOfSale> updateByUserId(@PathVariable("username") String username,@RequestBody ContractOfSale doc) {
        ContractOfSale contractOfSale = contractOfSaleService.updateByUsername(username, doc);
        return new ResponseEntity<>(contractOfSale, HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        contractOfSaleService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("delete-all/{username}")
    public ResponseEntity<Void> deleteAllByUsername(@PathVariable("username") String username) {
        contractOfSaleService.getAllByUsername(username).forEach(contractOfSale -> deleteById(contractOfSale.getId()));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("delete-all")
    public ResponseEntity<Void> deleteAll() {
        contractOfSaleService.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("page/get")
    public ResponseEntity<Page<ContractOfSale>> getAllByUsername(
            @RequestBody FilterObject obj, @RequestParam int page, @RequestParam int size) throws ParseException{
        Page<ContractOfSale> contractList = filterService.findAllByFilter(obj, page, size);
        return new ResponseEntity<>(contractList, HttpStatus.OK);
    }


}
