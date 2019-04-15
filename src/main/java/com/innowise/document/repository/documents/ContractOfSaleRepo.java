package com.innowise.document.repository.documents;

import com.innowise.document.entity.documents.ContractOfSale;
import com.innowise.document.entity.documents.QContractOfSale;
import com.innowise.document.repository.CustomRepo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractOfSaleRepo extends CustomRepo<ContractOfSale, QContractOfSale, Long> {

    List<ContractOfSale> findAllByUser_Username(String user);
}
