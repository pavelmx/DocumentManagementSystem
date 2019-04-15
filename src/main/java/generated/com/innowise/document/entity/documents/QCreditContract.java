package com.innowise.document.entity.documents;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCreditContract is a Querydsl query type for CreditContract
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCreditContract extends EntityPathBase<CreditContract> {

    private static final long serialVersionUID = -389037296L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCreditContract creditContract = new QCreditContract("creditContract");

    public final QDocumentPattern _super;

    public final NumberPath<Float> annualInterest = createNumber("annualInterest", Float.class);

    //inherited
    public final StringPath clientAdress;

    //inherited
    public final StringPath clientFullName;

    public final NumberPath<Float> creditAmount = createNumber("creditAmount", Float.class);

    //inherited
    public final DatePath<java.util.Date> dateOfCreation;

    //inherited
    public final NumberPath<Long> id;

    //inherited
    public final DateTimePath<java.util.Date> lastChange;

    //inherited
    public final StringPath otherInfo;

    public final NumberPath<Integer> term = createNumber("term", Integer.class);

    // inherited
    public final com.innowise.document.entity.QUser user;

    public QCreditContract(String variable) {
        this(CreditContract.class, forVariable(variable), INITS);
    }

    public QCreditContract(Path<? extends CreditContract> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCreditContract(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCreditContract(PathMetadata metadata, PathInits inits) {
        this(CreditContract.class, metadata, inits);
    }

    public QCreditContract(Class<? extends CreditContract> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QDocumentPattern(type, metadata, inits);
        this.clientAdress = _super.clientAdress;
        this.clientFullName = _super.clientFullName;
        this.dateOfCreation = _super.dateOfCreation;
        this.id = _super.id;
        this.lastChange = _super.lastChange;
        this.otherInfo = _super.otherInfo;
        this.user = _super.user;
    }

}

