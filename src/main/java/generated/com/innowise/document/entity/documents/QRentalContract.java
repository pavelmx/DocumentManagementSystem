package com.innowise.document.entity.documents;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRentalContract is a Querydsl query type for RentalContract
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRentalContract extends EntityPathBase<RentalContract> {

    private static final long serialVersionUID = 1463958075L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRentalContract rentalContract = new QRentalContract("rentalContract");

    public final QDocumentPattern _super;

    //inherited
    public final StringPath clientAdress;

    //inherited
    public final StringPath clientFullName;

    //inherited
    public final DatePath<java.util.Date> dateOfCreation;

    public final DatePath<java.util.Date> endRental = createDate("endRental", java.util.Date.class);

    //inherited
    public final NumberPath<Long> id;

    //inherited
    public final BooleanPath isActive;

    //inherited
    public final DateTimePath<java.util.Date> lastChange;

    //inherited
    public final StringPath otherInfo;

    public final StringPath rentalObject = createString("rentalObject");

    public final NumberPath<Integer> rentalPrice = createNumber("rentalPrice", Integer.class);

    public final DatePath<java.util.Date> startRental = createDate("startRental", java.util.Date.class);

    //inherited
    public final StringPath title;

    // inherited
    public final com.innowise.document.entity.QUser user;

    public QRentalContract(String variable) {
        this(RentalContract.class, forVariable(variable), INITS);
    }

    public QRentalContract(Path<? extends RentalContract> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRentalContract(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRentalContract(PathMetadata metadata, PathInits inits) {
        this(RentalContract.class, metadata, inits);
    }

    public QRentalContract(Class<? extends RentalContract> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QDocumentPattern(type, metadata, inits);
        this.clientAdress = _super.clientAdress;
        this.clientFullName = _super.clientFullName;
        this.dateOfCreation = _super.dateOfCreation;
        this.id = _super.id;
        this.isActive = _super.isActive;
        this.lastChange = _super.lastChange;
        this.otherInfo = _super.otherInfo;
        this.title = _super.title;
        this.user = _super.user;
    }

}

