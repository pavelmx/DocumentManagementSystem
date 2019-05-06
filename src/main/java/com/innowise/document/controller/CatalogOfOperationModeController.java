package com.innowise.document.controller;

import com.innowise.document.entity.CatalogOfOperationMode;
import com.innowise.document.service.CatalogOfOperationModeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/catalog")
@CrossOrigin(origins = "*")
public class CatalogOfOperationModeController {

    @Autowired
    CatalogOfOperationModeService catalogService;

    @GetMapping("/all")
    public ResponseEntity<List<CatalogOfOperationMode>> getAllContracts(){
        List<CatalogOfOperationMode> listContracts = new ArrayList<>();
        listContracts.addAll(catalogService.getAll());
        return new ResponseEntity<>(listContracts, HttpStatus.OK);
    }
}
