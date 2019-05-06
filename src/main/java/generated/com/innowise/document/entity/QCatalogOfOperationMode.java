package com.innowise.document.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QCatalogOfOperationMode is a Querydsl query type for CatalogOfOperationMode
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCatalogOfOperationMode extends EntityPathBase<CatalogOfOperationMode> {

    private static final long serialVersionUID = -950483095L;

    public static final QCatalogOfOperationMode catalogOfOperationMode = new QCatalogOfOperationMode("catalogOfOperationMode");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath operationMode = createString("operationMode");

    public QCatalogOfOperationMode(String variable) {
        super(CatalogOfOperationMode.class, forVariable(variable));
    }

    public QCatalogOfOperationMode(Path<? extends CatalogOfOperationMode> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCatalogOfOperationMode(PathMetadata metadata) {
        super(CatalogOfOperationMode.class, metadata);
    }

}

