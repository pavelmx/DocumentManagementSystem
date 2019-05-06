package com.innowise.document.repository.documents;

import com.innowise.document.entity.documents.ContractOfSale;
import com.innowise.document.entity.documents.QContractOfSale;
import com.innowise.document.repository.CustomRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContractOfSaleRepo extends CustomRepo<ContractOfSale,QContractOfSale,Long> {
    Optional<ContractOfSale> findByFilename(String filename);
    Page<ContractOfSale> findAllByUser_Username(String username, Pageable pageable);
    List<ContractOfSale> findAllByUser_Username(String username);
}
