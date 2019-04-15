package com.innowise.document.service.documents;

import com.innowise.document.entity.documents.WorkContract;
import com.innowise.document.repository.UserRepo;
import com.innowise.document.repository.documents.WorkContractRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class WorkContractServiceImpl implements DocumentService<WorkContract>{

    @Autowired
    WorkContractRepo workContractRepo;

    @Autowired
    UserRepo userRepo;

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
                    throw new EntityExistsException("RentalContract with title: '" + workContract.getTitle() + "' exists.");
            }
        }
        workContract.setActive(true);
        workContract.setUser(userRepo.findByUsername(username).get());
        return workContractRepo.save(workContract);
    }

    @Override
    public WorkContract updateByUsername(String username, WorkContract workContract){
        if (!existsById(workContract.getId())) {
            throw new EntityExistsException("RentalContract with id: '" + workContract.getId() + "' not found.");
        }
        if (!userRepo.existsByUsername(username)) {
            throw new EntityNotFoundException("User  '" + username + "' not found.");
        }
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
                .orElseThrow(() -> new RuntimeException("RentalContract with id: '" + id + "' not found."));
    }

    @Override
    public void deleteById(Long id){
        workContractRepo.deleteById(id);
    }

    @Override
    public boolean existsById(Long id){
        return workContractRepo.existsById(id);
    }


}
