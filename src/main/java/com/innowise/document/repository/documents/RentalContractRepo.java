package com.innowise.document.repository.documents;

import com.innowise.document.entity.documents.QRentalContract;
import com.innowise.document.entity.documents.RentalContract;
import com.innowise.document.repository.CustomRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RentalContractRepo extends CustomRepo<RentalContract,QRentalContract,Long>{
    Optional<RentalContract> findByFilename(String filename);
    Page<RentalContract> findAllByUser_Username(String username, Pageable pageable);
    List<RentalContract> findAllByUser_Username(String username);
}
