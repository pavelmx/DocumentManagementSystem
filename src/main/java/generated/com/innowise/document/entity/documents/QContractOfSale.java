package com.innowise.document.entity.documents;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QContractOfSale is a Querydsl query type for ContractOfSale
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QContractOfSale extends EntityPathBase<ContractOfSale> {

    private static final long serialVersionUID = 356291157L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QContractOfSale contractOfSale = new QContractOfSale("contractOfSale");

    public final QDocumentPattern _super;

    //inherited
    public final StringPath clientAdress;

    //inherited
    public final StringPath clientFullName;

    //inherited
    public final DatePath<java.util.Date> dateOfCreation;

    //inherited
    public final StringPath filename;

    //inherited
    public final NumberPath<Long> id;

    //inherited
    public final BooleanPath isActive;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastChange;

    //inherited
    public final StringPath otherInfo;

    public final StringPath saleObject = createString("saleObject");

    public final NumberPath<Float> salingPrice = createNumber("salingPrice", Float.class);

    //inherited
    public final StringPath title;

    // inherited
    public final com.innowise.document.entity.QUser user;

    public final NumberPath<Integer> warrantyPeriod = createNumber("warrantyPeriod", Integer.class);

    public QContractOfSale(String variable) {
        this(ContractOfSale.class, forVariable(variable), INITS);
    }

    public QContractOfSale(Path<? extends ContractOfSale> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QContractOfSale(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QContractOfSale(PathMetadata metadata, PathInits inits) {
        this(ContractOfSale.class, metadata, inits);
    }

    public QContractOfSale(Class<? extends ContractOfSale> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QDocumentPattern(type, metadata, inits);
        this.clientAdress = _super.clientAdress;
        this.clientFullName = _super.clientFullName;
        this.dateOfCreation = _super.dateOfCreation;
        this.filename = _super.filename;
        this.id = _super.id;
        this.isActive = _super.isActive;
        this.lastChange = _super.lastChange;
        this.otherInfo = _super.otherInfo;
        this.title = _super.title;
        this.user = _super.user;
    }

}

