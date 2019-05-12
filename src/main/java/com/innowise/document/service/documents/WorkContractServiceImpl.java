package com.innowise.document.service.documents;

import com.innowise.document.entity.documents.RentalContract;
import com.innowise.document.entity.documents.WorkContract;
import com.innowise.document.repository.UserRepo;
import com.innowise.document.repository.CatalogOfOperationModeRepo;
import com.innowise.document.repository.documents.WorkContractRepo;
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
public class WorkContractServiceImpl implements DocumentService<WorkContract>{

    @Autowired
    WorkContractRepo workContractRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    CatalogOfOperationModeRepo catalogOfOperationModeRepo;


    @Override
    public Page<WorkContract> getAllByUsername(String username, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return workContractRepo.findAllByUser_Username(username, pageable);
    }

    @Override
    public Page<WorkContract> getAllPage(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return workContractRepo.findAll(pageable);
    }

    @Override
    public List<WorkContract> getAllByUsername(String username){
        return workContractRepo.findAllByUser_Username(username);
    }

    @Override
    public WorkContract addByUsername(String username, WorkContract workContract){
        if (!userRepo.existsByUsername(username)) {
            throw new EntityNotFoundException("User  '" + username + "' not found.");
        }
        List<WorkContract> rentalContractList = workContractRepo.findAllByUser_Username(username);
        if (!rentalContractList.isEmpty()) {
            for (WorkContract rc : rentalContractList) {
                if (rc.getTitle().equals(workContract.getTitle()))
                    throw new EntityExistsException("WorkContract with title: '" + workContract.getTitle() + "' exists.");
            }
        }
        workContract.setDateOfCreation(Date.valueOf(LocalDate.now()));
        workContract.setActive(true);
        workContract.setUser(userRepo.findByUsername(username).get());
        return workContractRepo.save(workContract);
    }

    @Override
    public WorkContract updateByUsername(String username, WorkContract workContract){
        if (!existsById(workContract.getId())) {
            throw new EntityExistsException("WorkContract with id: '" + workContract.getId() + "' not found.");
        }
        if (!userRepo.existsByUsername(username)) {
            throw new EntityNotFoundException("User  '" + username + "' not found.");
        }
        workContract.setLastChange(LocalDateTime.now());
        workContract.setActive(true);
        workContract.setUser(userRepo.findByUsername(username).get());
        return workContractRepo.save(workContract);
    }

    @Override
    public void deleteAll(){
        workContractRepo.deleteAll();
    }

    @Override
    public List<WorkContract> getAll(){
        return workContractRepo.findAll();
    }

    @Override
    public WorkContract getById(Long id){
        return workContractRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("WorkContract with id: '" + id + "' not found."));
    }

    @Override
    public void deleteById(Long id){
        workContractRepo.deleteById(id);
    }

    @Override
    public boolean existsById(Long id){
        return workContractRepo.existsById(id);
    }

    private void setActiveStatus(WorkContract workContract){
        if(checkDiffDate(workContract.getStartWork(), Date.valueOf(LocalDate.now())) > workContract.getTerm()){
            workContract.setActive(false);
        }
        else{
            workContract.setActive(true);
        }
    }
}
