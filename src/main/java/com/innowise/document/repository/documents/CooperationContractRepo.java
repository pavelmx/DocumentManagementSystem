package com.innowise.document.repository.documents;

import com.innowise.document.entity.documents.ContractOfSale;
import com.innowise.document.entity.documents.CooperationContract;
import com.innowise.document.entity.documents.QCooperationContract;
import com.innowise.document.repository.CustomRepo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CooperationContractRepo extends CustomRepo<CooperationContract, QCooperationContract, Long> {

    List<CooperationContract> findAllByUser_Username(String user);
}
