package com.innowise.document.repository.documents;

import com.innowise.document.entity.documents.QWorkContract;
import com.innowise.document.entity.documents.RentalContract;
import com.innowise.document.entity.documents.WorkContract;
import com.innowise.document.repository.CustomRepo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkContractRepo extends CustomRepo<WorkContract, QWorkContract, Long> {

    List<WorkContract> findAllByUser_Username(String user);
}
