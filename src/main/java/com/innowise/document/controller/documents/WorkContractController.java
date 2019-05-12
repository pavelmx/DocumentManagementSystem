package com.innowise.document.controller.documents;

import com.innowise.document.entity.FilterObject;
import com.innowise.document.entity.documents.WorkContract;
import com.innowise.document.service.documents.DocumentService;
import com.innowise.document.service.documents.WorkContractServiceImpl;
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
@RequestMapping("/work-contract")
@CrossOrigin(origins = "*")
public class WorkContractController {

    @Autowired
    DocumentService<WorkContract> workContractService;

    @Autowired
    WorkContractServiceImpl workContractServiceImpl;

    @Autowired
    FilterService<WorkContract> filterService;


    @GetMapping("get/{id}")
    public ResponseEntity<WorkContract> getById(@PathVariable("id") Long id) {
        WorkContract workContract = workContractService.getById(id);
        return new ResponseEntity<>(workContract, HttpStatus.OK);
    }

    @GetMapping("get-username/{username}")
    public ResponseEntity<List<WorkContract>> getAllByUsername(@PathVariable("username") String username){
        List<WorkContract> workContractList = new ArrayList<>();
        workContractService.getAllByUsername(username).forEach(workContractList::add);
        return new ResponseEntity<>(workContractList, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("get-all")
    public ResponseEntity<List<WorkContract>> getAll() {
        List<WorkContract> workContractList = new ArrayList<>();
        workContractService.getAll().forEach(workContractList::add);
        return new ResponseEntity<>(workContractList, HttpStatus.OK);
    }

    @PostMapping("add/{username}")
    public ResponseEntity<WorkContract> addByUserId(@PathVariable("username") String username, @RequestBody WorkContract doc) {
        WorkContract workContract = workContractServiceImpl.addByUsername(username, doc);
        return new ResponseEntity<>(workContract, HttpStatus.CREATED);
    }

    @PutMapping("update/{username}")
    public ResponseEntity<WorkContract> updateByUserId(@PathVariable("username") String username,@RequestBody WorkContract doc) {
        WorkContract workContract = workContractService.updateByUsername(username, doc);
        return new ResponseEntity<>(workContract, HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        workContractService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("delete-all/{username}")
    public ResponseEntity<Void> deleteAllByUsername(@PathVariable("username") String username) {
        workContractService.getAllByUsername(username).forEach(workContract -> deleteById(workContract.getId()));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("delete-all")
    public ResponseEntity<Void> deleteAll() {
        workContractService.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("page/get")
    public ResponseEntity<Page<WorkContract>> getAllByUsername(
            @RequestBody FilterObject obj, @RequestParam int page, @RequestParam int size) throws ParseException{
        Page<WorkContract> contractList = filterService.findAllByFilter(obj, page, size);
        return new ResponseEntity<>(contractList, HttpStatus.OK);
    }
}
