package com.innowise.document.repository.documents;

import com.innowise.document.entity.documents.CreditContract;
import com.innowise.document.entity.documents.QRentalContract;
import com.innowise.document.entity.documents.RentalContract;
import com.innowise.document.repository.CustomRepo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentalContractRepo extends CustomRepo<RentalContract, QRentalContract, Long>{

    List<RentalContract> findAllByUser_Username(String user);
}
