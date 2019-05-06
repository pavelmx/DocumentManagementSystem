package com.innowise.document.entity.documents;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCooperationContract is a Querydsl query type for CooperationContract
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCooperationContract extends EntityPathBase<CooperationContract> {

    private static final long serialVersionUID = -1021917208L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCooperationContract cooperationContract = new QCooperationContract("cooperationContract");

    public final QDocumentPattern _super;

    //inherited
    public final StringPath clientAdress;

    //inherited
    public final StringPath clientFullName;

    public final StringPath clientResponsibility = createString("clientResponsibility");

    public final StringPath creatorResponsibility = createString("creatorResponsibility");

    //inherited
    public final DatePath<java.util.Date> dateOfCreation;

    //inherited
    public final StringPath filename;

    //inherited
    public final NumberPath<Long> id;

    //inherited
    public final BooleanPath isActive;

    //inherited
    public final StringPath kind;

    public final StringPath kindOfActivity = createString("kindOfActivity");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastChange;

    //inherited
    public final StringPath otherInfo;

    public final NumberPath<Integer> term = createNumber("term", Integer.class);

    public final StringPath terminationConditions = createString("terminationConditions");

    //inherited
    public final StringPath title;

    // inherited
    public final com.innowise.document.entity.QUser user;

    public QCooperationContract(String variable) {
        this(CooperationContract.class, forVariable(variable), INITS);
    }

    public QCooperationContract(Path<? extends CooperationContract> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCooperationContract(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCooperationContract(PathMetadata metadata, PathInits inits) {
        this(CooperationContract.class, metadata, inits);
    }

    public QCooperationContract(Class<? extends CooperationContract> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QDocumentPattern(type, metadata, inits);
        this.clientAdress = _super.clientAdress;
        this.clientFullName = _super.clientFullName;
        this.dateOfCreation = _super.dateOfCreation;
        this.filename = _super.filename;
        this.id = _super.id;
        this.isActive = _super.isActive;
        this.kind = _super.kind;
        this.lastChange = _super.lastChange;
        this.otherInfo = _super.otherInfo;
        this.title = _super.title;
        this.user = _super.user;
    }

}

