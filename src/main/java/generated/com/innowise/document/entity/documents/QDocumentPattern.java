package com.innowise.document.entity.documents;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDocumentPattern is a Querydsl query type for DocumentPattern
 */
@Generated("com.querydsl.codegen.SupertypeSerializer")
public class QDocumentPattern extends EntityPathBase<DocumentPattern> {

    private static final long serialVersionUID = 706223792L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDocumentPattern documentPattern = new QDocumentPattern("documentPattern");

    public final StringPath clientAdress = createString("clientAdress");

    public final StringPath clientFullName = createString("clientFullName");

    public final DatePath<java.util.Date> dateOfCreation = createDate("dateOfCreation", java.util.Date.class);

    public final StringPath filename = createString("filename");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isActive = createBoolean("isActive");

    public final DateTimePath<java.time.LocalDateTime> lastChange = createDateTime("lastChange", java.time.LocalDateTime.class);

    public final StringPath otherInfo = createString("otherInfo");

    public final StringPath title = createString("title");

    public final com.innowise.document.entity.QUser user;

    public QDocumentPattern(String variable) {
        this(DocumentPattern.class, forVariable(variable), INITS);
    }

    public QDocumentPattern(Path<? extends DocumentPattern> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDocumentPattern(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDocumentPattern(PathMetadata metadata, PathInits inits) {
        this(DocumentPattern.class, metadata, inits);
    }

    public QDocumentPattern(Class<? extends DocumentPattern> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.innowise.document.entity.QUser(forProperty("user")) : null;
    }

}

