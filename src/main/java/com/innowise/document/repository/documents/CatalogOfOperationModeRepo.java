package com.innowise.document.repository.documents;

import com.innowise.document.entity.documents.CatalogOfOperationMode;
import com.innowise.document.entity.documents.QCatalogOfOperationMode;
import com.innowise.document.repository.CustomRepo;
import org.springframework.stereotype.Repository;

@Repository
public interface CatalogOfOperationModeRepo extends CustomRepo<CatalogOfOperationMode, QCatalogOfOperationMode, Long> {


}
