package com.innowise.document.service;

import com.innowise.document.entity.documents.*;
import com.innowise.document.repository.documents.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
@EnableScheduling
public class ScheduledTasks {

    @Autowired
    WorkContractRepo workContractRepo;

    @Autowired
    CreditContractRepo creditContractRepo;

    @Autowired
    CooperationContractRepo cooperationContractRepo;

    @Autowired
    RentalContractRepo rentalContractRepo;

    @Autowired
    ContractOfSaleRepo contractOfSaleRepo;

    @Scheduled(cron = "* 0 1 * * *")
    public void checkStatus() {
        checkStatusForContractsOfSale();
        checkStatusForCooperationContracts();
        checkStatusForCreditContracts();
        checkStatusForRentalContracts();
        checkStatusForWorkContracts();
        System.out.println("all checked");
    }

    private void checkStatusForContractsOfSale() {
        LocalDate today = LocalDate.now();
        List<ContractOfSale> docList = contractOfSaleRepo.findAll();
        for (ContractOfSale contract: docList) {
            LocalDate old = LocalDate.parse(contract.getDateOfCreation().toString());
            long di = ChronoUnit.DAYS.between(old, today);
            if (di > contract.getWarrantyPeriod()){
                contract.setActive(false);
            }
            else{
                contract.setActive(true);
            }
            contractOfSaleRepo.save(contract);
        }
        System.out.println("sale checked");
    }

    private void checkStatusForRentalContracts() {
        LocalDate today = LocalDate.now();
        List<RentalContract> docList = rentalContractRepo.findAll();
        for (RentalContract contract: docList) {
            LocalDate start = LocalDate.parse(contract.getStartRental().toString());
            LocalDate end = LocalDate.parse(contract.getEndRental().toString());
            long di = ChronoUnit.DAYS.between(start, today);
            long di2 = ChronoUnit.DAYS.between(start, end);
            if (di > di2){
                contract.setActive(false);
            }
            else{
                contract.setActive(true);
            }
            rentalContractRepo.save(contract);
        }
        System.out.println("rental checked");
    }

    private void checkStatusForCooperationContracts() {
        LocalDate today = LocalDate.now();
        List<CooperationContract> docList = cooperationContractRepo.findAll();
        for (CooperationContract contract: docList) {
            LocalDate old = LocalDate.parse(contract.getDateOfCreation().toString());
            long di = ChronoUnit.DAYS.between(old, today);
            if (di > contract.getTerm()){
                contract.setActive(false);
            }
            else{
                contract.setActive(true);
            }
            cooperationContractRepo.save(contract);
        }
        System.out.println("cooperation checked");
    }

    private void checkStatusForCreditContracts() {
        LocalDate today = LocalDate.now();
        List<CreditContract> docList = creditContractRepo.findAll();
        for (CreditContract contract: docList) {
            LocalDate old = LocalDate.parse(contract.getDateOfCreation().toString());
            long di = ChronoUnit.DAYS.between(old, today);
            if (di > contract.getTerm()){
                contract.setActive(false);
            }
            else{
                contract.setActive(true);
            }
            creditContractRepo.save(contract);
        }
        System.out.println("credit checked");
    }

    private void checkStatusForWorkContracts() {
        LocalDate today = LocalDate.now();
        List<WorkContract> docList = workContractRepo.findAll();
        for (WorkContract contract: docList) {
            LocalDate old = LocalDate.parse(contract.getStartWork().toString());
            long di = ChronoUnit.DAYS.between(old, today);
            if (di > contract.getTerm()){
                contract.setActive(false);
            }
            else{
                contract.setActive(true);
            }
            workContractRepo.save(contract);
        }
        System.out.println("work checked");
    }
}
