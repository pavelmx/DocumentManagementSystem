package com.innowise.document.service;

import com.innowise.document.entity.Document;
import com.innowise.document.repository.DocumentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.TimeZone;

@Component
@EnableScheduling
public class ScheduledTasks {

    @Autowired
    DocumentRepo documentRepo;

    @Scheduled(cron = "0 0 1 * * *")
    public void checkExpired() {
        LocalDate today = LocalDate.now();
        List<Document> docList = documentRepo.findAll();
        for (Document d: docList) {
            LocalDate old = LocalDate.parse(d.getDateOfCreation().toString());
            System.out.println("now is " + today);
            System.out.println("old is " + old);
            long di = ChronoUnit.DAYS.between(old, today);
            System.out.println("diff is " + di);
            System.out.println("term is " + d.getContractTerm());
            if (di < d.getContractTerm()){
                d.setExpired(false);System.out.println("set false");
            }
            else{
                d.setExpired(true);System.out.println("set true");
            }
            documentRepo.save(d);
            System.out.println("-------------- " + di);
        }
    }
}
