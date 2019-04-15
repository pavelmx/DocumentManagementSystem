package com.innowise.document.service.documents;

import com.innowise.document.entity.documents.RentalContract;
import com.innowise.document.repository.UserRepo;
import com.innowise.document.repository.documents.RentalContractRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class RentalContractServiceImpl implements DocumentService<RentalContract>{

    @Autowired
    RentalContractRepo rentalContractRepo;

    @Autowired
    UserRepo userRepo;

    @Override
    public List<RentalContract> getAllByUsername(String username){
        return rentalContractRepo.findAllByUser_Username(username);
    }

    @Override
    public RentalContract addByUsername(String username, RentalContract rentalContract){
        if (!userRepo.existsByUsername(username)) {
            throw new EntityNotFoundException("User  '" + username + "' not found.");
        }
        List<RentalContract> rentalContractList = rentalContractRepo.findAllByUser_Username(username);
        if (!rentalContractList.isEmpty()) {
            for (RentalContract rc : rentalContractList) {
                if (rc.getTitle().equals(rentalContract.getTitle()))
                    throw new EntityExistsException("RentalContract with title: '" + rentalContract.getTitle() + "' exists.");
            }
        }
        setActiveStatus(rentalContract);
        rentalContract.setUser(userRepo.findByUsername(username).get());
        return rentalContractRepo.save(rentalContract);
    }

    @Override
    public RentalContract updateByUsername(String username, RentalContract rentalContract){
        if (!existsById(rentalContract.getId())) {
            throw new EntityExistsException("RentalContract with id: '" + rentalContract.getId() + "' not found.");
        }
        if (!userRepo.existsByUsername(username)) {
            throw new EntityNotFoundException("User  '" + username + "' not found.");
        }
        setActiveStatus(rentalContract);
        rentalContract.setUser(userRepo.findByUsername(username).get());
        return rentalContractRepo.save(rentalContract);
    }

    @Override
    public void deleteAll(){
        rentalContractRepo.deleteAll();
    }

    @Override
    public List<RentalContract> getAll(){
        return rentalContractRepo.findAll();
    }

    @Override
    public RentalContract getById(Long id){
        return rentalContractRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("RentalContract with id: '" + id + "' not found."));
    }

    @Override
    public void deleteById(Long id){
        rentalContractRepo.deleteById(id);
    }

    @Override
    public boolean existsById(Long id){
        return rentalContractRepo.existsById(id);
    }

    private void setActiveStatus(RentalContract rentalContract){
        if(checkDiffDate(rentalContract.getStartRental(), rentalContract.getEndRental())
                < checkDiffDate(rentalContract.getStartRental(), Date.valueOf(LocalDate.now()))){
            rentalContract.setActive(false);
        }
        else{
            rentalContract.setActive(true);
        }
    }
}
