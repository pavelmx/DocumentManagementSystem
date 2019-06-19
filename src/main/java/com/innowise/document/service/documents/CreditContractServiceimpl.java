package com.innowise.document.service.documents;

import com.innowise.document.entity.documents.CreditContract;
import com.innowise.document.repository.documents.CreditContractRepo;
import com.innowise.document.repository.UserRepo;
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
public class CreditContractServiceimpl implements DocumentService<CreditContract> {

    @Autowired
    CreditContractRepo creditContractRepo;

    @Autowired
    UserRepo userRepo;

    @Override
    public Page<CreditContract> getAllByUsername(String username, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return creditContractRepo.findAllByUser_Username(username, pageable);
    }

    @Override
    public Page<CreditContract> getAllPage(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return creditContractRepo.findAll(pageable);
    }

    @Override
    public List<CreditContract> getAllByUsername(String username){
        return creditContractRepo.findAllByUser_Username(username);
    }

    @Override
    public CreditContract addByUsername(String username, CreditContract creditContract){
        if (!userRepo.existsByUsername(username)) {
            throw new EntityNotFoundException("User  '" + username + "' not found.");
        }
        List<CreditContract> creditContractList = creditContractRepo.findAllByUser_Username(username);
        if (!creditContractList.isEmpty()) {
            for (CreditContract rc : creditContractList) {
                if (rc.getTitle().equals(creditContract.getTitle()))
                    throw new EntityExistsException("CreditContract with title: '" + creditContract.getTitle() + "' exists.");
            }
        }

        creditContract.setDateOfCreation(Date.valueOf(LocalDate.now()));
        setActiveStatus(creditContract);
        creditContract.setUser(userRepo.findByUsername(username).get());
        return creditContractRepo.save(creditContract);
    }

    @Override
    public CreditContract updateByUsername(String username, CreditContract creditContract){
        if (!existsById(creditContract.getId())) {
            throw new EntityExistsException("CreditContract with id: '" + creditContract.getId() + "' not found.");
        }
        if (!userRepo.existsByUsername(username)) {
            throw new EntityNotFoundException("User  '" + username + "' not found.");
        }
        creditContract.setLastChange(LocalDateTime.now());
        setActiveStatus(creditContract);
        creditContract.setUser(userRepo.findByUsername(username).get());
        return creditContractRepo.save(creditContract);
    }

    @Override
    public void deleteAll(){
        creditContractRepo.deleteAll();
    }

    @Override
    public List<CreditContract> getAll(){
        return creditContractRepo.findAll();
    }

    @Override
    public CreditContract getById(Long id){
        return creditContractRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("CreditContract with id: '" + id + "' not found."));
    }

    @Override
    public void deleteById(Long id){
        creditContractRepo.deleteById(id);
    }

    @Override
    public boolean existsById(Long id){
        return creditContractRepo.existsById(id);
    }

    private void setActiveStatus(CreditContract creditContract){
        if(checkDiffDate(creditContract.getDateOfCreation(), Date.valueOf(LocalDate.now())) > creditContract.getTerm()){
            creditContract.setActive(false);
        }
        else{
            creditContract.setActive(true);
        }
    }
}
