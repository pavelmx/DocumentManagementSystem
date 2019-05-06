package com.innowise.document.service.documents;

import com.innowise.document.entity.documents.CooperationContract;
import com.innowise.document.repository.UserRepo;
import com.innowise.document.repository.documents.CooperationContractRepo;
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
public class CooperationContractServiceImpl implements DocumentService<CooperationContract>{

    @Autowired
    CooperationContractRepo cooperationContractRepo;

    @Autowired
    UserRepo userRepo;

    @Override
    public Page<CooperationContract> getAllByUsername(String username, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return cooperationContractRepo.findAllByUser_Username(username, pageable);
    }

    @Override
    public Page<CooperationContract> getAllPage(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return cooperationContractRepo.findAll(pageable);
    }

    @Override
    public List<CooperationContract> getAllByUsername(String username){
        return cooperationContractRepo.findAllByUser_Username(username);
    }

    @Override
    public CooperationContract addByUsername(String username, CooperationContract cooperationContract){
        if (!userRepo.existsByUsername(username)) {
            throw new EntityNotFoundException("User  '" + username + "' not found.");
        }
        List<CooperationContract> cooperationContractList = cooperationContractRepo.findAllByUser_Username(username);
        if (!cooperationContractList.isEmpty()) {
            for (CooperationContract rc : cooperationContractList) {
                if (rc.getTitle().equals(cooperationContract.getTitle()))
                    throw new EntityExistsException("CooperationContract with title: '" + cooperationContract.getTitle() + "' exists.");
            }
        }
        cooperationContract.setKind("Cooperation contract");
        cooperationContract.setDateOfCreation(Date.valueOf(LocalDate.now()));
        setActiveStatus(cooperationContract);
        cooperationContract.setUser(userRepo.findByUsername(username).get());
        return cooperationContractRepo.save(cooperationContract);
    }

    @Override
    public CooperationContract updateByUsername(String username, CooperationContract cooperationContract){
        if (!existsById(cooperationContract.getId())) {
            throw new EntityExistsException("CooperationContract with id: '" + cooperationContract.getId() + "' not found.");
        }
        if (!userRepo.existsByUsername(username)) {
            throw new EntityNotFoundException("User  '" + username + "' not found.");
        }
        cooperationContract.setLastChange(LocalDateTime.now());
        setActiveStatus(cooperationContract);
        cooperationContract.setUser(userRepo.findByUsername(username).get());
        return cooperationContractRepo.save(cooperationContract);
    }

    @Override
    public void deleteAll(){
        cooperationContractRepo.deleteAll();
    }

    @Override
    public List<CooperationContract> getAll(){
        return cooperationContractRepo.findAll();
    }

    @Override
    public CooperationContract getById(Long id){
        return cooperationContractRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("CooperationContract with id: '" + id + "' not found."));
    }

    @Override
    public void deleteById(Long id){
        cooperationContractRepo.deleteById(id);
    }

    @Override
    public boolean existsById(Long id){
        return cooperationContractRepo.existsById(id);
    }

    private void setActiveStatus(CooperationContract cooperationContract){
        if(checkDiffDate(cooperationContract.getDateOfCreation(), Date.valueOf(LocalDate.now())) > cooperationContract.getTerm()){
            cooperationContract.setActive(false);
        }
        else{
            cooperationContract.setActive(true);
        }
    }
}
