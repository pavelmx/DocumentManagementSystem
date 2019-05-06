package com.innowise.document.repository.documents;

import com.innowise.document.entity.documents.CooperationContract;
import com.innowise.document.entity.documents.QCooperationContract;
import com.innowise.document.repository.CustomRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CooperationContractRepo extends CustomRepo<CooperationContract,QCooperationContract,Long> {
    Optional<CooperationContract> findByFilename(String filename);
    Page<CooperationContract> findAllByUser_Username(String username, Pageable pageable);
    List<CooperationContract> findAllByUser_Username(String username);
}
