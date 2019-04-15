package com.innowise.document.repository.documents;

import com.innowise.document.entity.documents.CooperationContract;
import com.innowise.document.entity.documents.CreditContract;
import com.innowise.document.entity.documents.QCreditContract;
import com.innowise.document.repository.CustomRepo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditContractRepo extends CustomRepo<CreditContract, QCreditContract, Long> {

    List<CreditContract> findAllByUser_Username(String user);
}
