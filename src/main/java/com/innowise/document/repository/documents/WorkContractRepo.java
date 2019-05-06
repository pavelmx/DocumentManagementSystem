package com.innowise.document.repository.documents;

import com.innowise.document.entity.documents.QWorkContract;
import com.innowise.document.entity.documents.WorkContract;
import com.innowise.document.repository.CustomRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkContractRepo extends CustomRepo<WorkContract,QWorkContract,Long> {
    Optional<WorkContract> findByFilename(String filename);
    Page<WorkContract> findAllByUser_Username(String username, Pageable pageable);
    List<WorkContract> findAllByUser_Username(String username);
}
