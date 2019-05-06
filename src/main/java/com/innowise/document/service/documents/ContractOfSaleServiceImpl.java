package com.innowise.document.service.documents;

import com.innowise.document.entity.documents.ContractOfSale;
import com.innowise.document.repository.UserRepo;
import com.innowise.document.repository.documents.ContractOfSaleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ContractOfSaleServiceImpl implements DocumentService<ContractOfSale>{

    @Autowired
    ContractOfSaleRepo contractOfSaleRepo;

    @Autowired
    UserRepo userRepo;

    @Override
    public Page<ContractOfSale> getAllByUsername(String username, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return contractOfSaleRepo.findAllByUser_Username(username, pageable);
    }

    @Override
    public Page<ContractOfSale> getAllPage(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return contractOfSaleRepo.findAll(pageable);
    }

    @Override
    public List<ContractOfSale> getAllByUsername(String username){
        return contractOfSaleRepo.findAllByUser_Username(username);
    }

    @Override
    public ContractOfSale addByUsername(String username, ContractOfSale contractOfSale){
        if (!userRepo.existsByUsername(username)) {
            throw new EntityNotFoundException("User  '" + username + "' not found.");
        }
        List<ContractOfSale> cooperationContractList = contractOfSaleRepo.findAllByUser_Username(username);
        if (!cooperationContractList.isEmpty()) {
            for (ContractOfSale rc : cooperationContractList) {
                if (rc.getTitle().equals(contractOfSale.getTitle()))
                    throw new EntityExistsException("ContractOfSale with title: '" + contractOfSale.getTitle() + "' exists.");
            }
        }
        contractOfSale.setKind("Contract of sale");
        contractOfSale.setDateOfCreation(Date.valueOf(LocalDate.now()));
        setActiveStatus(contractOfSale);
        contractOfSale.setUser(userRepo.findByUsername(username).get());
        return contractOfSaleRepo.save(contractOfSale);
    }

    @Override
    public ContractOfSale updateByUsername(String username, ContractOfSale contractOfSale){
        if (!existsById(contractOfSale.getId())) {
            throw new EntityExistsException("ContractOfSale with id: '" + contractOfSale.getId() + "' not found.");
        }
        if (!userRepo.existsByUsername(username)) {
            throw new EntityNotFoundException("User  '" + username + "' not found.");
        }
        contractOfSale.setLastChange(LocalDateTime.now());
        setActiveStatus(contractOfSale);
        contractOfSale.setUser(userRepo.findByUsername(username).get());
        return contractOfSaleRepo.save(contractOfSale);
    }

    @Override
    public void deleteAll(){
        contractOfSaleRepo.deleteAll();
    }

    @Override
    public List<ContractOfSale> getAll(){
        return contractOfSaleRepo.findAll();
    }

    @Override
    public ContractOfSale getById(Long id){
        return contractOfSaleRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("ContractOfSale with id: '" + id + "' not found."));
    }

    @Override
    public void deleteById(Long id){
        contractOfSaleRepo.deleteById(id);
    }

    @Override
    public boolean existsById(Long id){
        return contractOfSaleRepo.existsById(id);
    }

    private void setActiveStatus(ContractOfSale contractOfSale){
        if(checkDiffDate(contractOfSale.getDateOfCreation(), Date.valueOf(LocalDate.now())) > contractOfSale.getWarrantyPeriod()){
            contractOfSale.setActive(false);
        }
        else{
            contractOfSale.setActive(true);
        }
    }
}
