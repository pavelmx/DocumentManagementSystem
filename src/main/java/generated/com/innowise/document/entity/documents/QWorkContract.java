package com.innowise.document.entity.documents;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWorkContract is a Querydsl query type for WorkContract
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QWorkContract extends EntityPathBase<WorkContract> {

    private static final long serialVersionUID = 851704936L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWorkContract workContract = new QWorkContract("workContract");

    public final QDocumentPattern _super;

    //inherited
    public final StringPath clientAdress;

    //inherited
    public final StringPath clientFullName;

    //inherited
    public final DatePath<java.util.Date> dateOfCreation;

    //inherited
    public final StringPath filename;

    public final NumberPath<Integer> holiday = createNumber("holiday", Integer.class);

    //inherited
    public final NumberPath<Long> id;

    //inherited
    public final BooleanPath isActive;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastChange;

    public final com.innowise.document.entity.QCatalogOfOperationMode operationMode;

    //inherited
    public final StringPath otherInfo;

    public final StringPath placeOfWork = createString("placeOfWork");

    public final StringPath position = createString("position");

    public final NumberPath<Integer> salary = createNumber("salary", Integer.class);

    public final DatePath<java.util.Date> startWork = createDate("startWork", java.util.Date.class);

    public final NumberPath<Integer> term = createNumber("term", Integer.class);

    //inherited
    public final StringPath title;

    // inherited
    public final com.innowise.document.entity.QUser user;

    public final NumberPath<Integer> workingHours = createNumber("workingHours", Integer.class);

    public QWorkContract(String variable) {
        this(WorkContract.class, forVariable(variable), INITS);
    }

    public QWorkContract(Path<? extends WorkContract> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWorkContract(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWorkContract(PathMetadata metadata, PathInits inits) {
        this(WorkContract.class, metadata, inits);
    }

    public QWorkContract(Class<? extends WorkContract> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QDocumentPattern(type, metadata, inits);
        this.clientAdress = _super.clientAdress;
        this.clientFullName = _super.clientFullName;
        this.dateOfCreation = _super.dateOfCreation;
        this.filename = _super.filename;
        this.id = _super.id;
        this.isActive = _super.isActive;
        this.lastChange = _super.lastChange;
        this.operationMode = inits.isInitialized("operationMode") ? new com.innowise.document.entity.QCatalogOfOperationMode(forProperty("operationMode")) : null;
        this.otherInfo = _super.otherInfo;
        this.title = _super.title;
        this.user = _super.user;
    }

}

