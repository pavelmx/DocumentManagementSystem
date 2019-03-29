package com.innowise.document.service;

import com.innowise.document.entity.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Component
@EnableScheduling
public class ScheduledTasks {

    @Autowired
    DocumentService documentService;

    @Scheduled(fixedRateString = "${time.in.milliseconds}")
    public void checkExpired() {
        LocalDate today = LocalDate.now();
        List<Document> docList = documentService.getAll();

        for (Document d: docList) {
            LocalDate old = LocalDate.parse(d.getDateOfCreation().toString());
            Period diff = Period.between(old, today);
            if (diff.getDays() < d.getContractTerm()){
                d.setExpired(false);
            }
            else{
                d.setExpired(true);
            }
            documentService.updateDocumentByUserId(d.getUser().getId(), d);
        }
    }
}
