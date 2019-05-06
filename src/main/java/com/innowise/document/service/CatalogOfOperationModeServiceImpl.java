package com.innowise.document.service;

import com.innowise.document.entity.CatalogOfOperationMode;
import com.innowise.document.repository.CatalogOfOperationModeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalogOfOperationModeServiceImpl implements CatalogOfOperationModeService{

    @Autowired
    CatalogOfOperationModeRepo catalogOfOperationModeRepo;

    @Override
    public List<CatalogOfOperationMode> getAll(){
        return catalogOfOperationModeRepo.findAll();
    }
}
