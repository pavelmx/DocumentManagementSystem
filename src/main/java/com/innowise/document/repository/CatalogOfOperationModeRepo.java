package com.innowise.document.repository;

import com.innowise.document.entity.CatalogOfOperationMode;
import com.innowise.document.entity.QCatalogOfOperationMode;
import org.springframework.stereotype.Repository;

@Repository
public interface CatalogOfOperationModeRepo extends CustomRepo<CatalogOfOperationMode, QCatalogOfOperationMode, Long> {


}
