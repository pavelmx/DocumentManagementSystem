package com.innowise.document.repository.documents;

import com.innowise.document.entity.documents.CreditContract;
import com.innowise.document.entity.documents.QCreditContract;
import com.innowise.document.repository.CustomRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CreditContractRepo extends CustomRepo<CreditContract,QCreditContract,Long> {
    Optional<CreditContract> findByFilename(String filename);
    Page<CreditContract> findAllByUser_Username(String username, Pageable pageable);
    List<CreditContract> findAllByUser_Username(String username);
}
