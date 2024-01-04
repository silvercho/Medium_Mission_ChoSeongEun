package com.ll.medium.domain.post.postLike.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPostLike is a Querydsl query type for PostLike
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPostLike extends EntityPathBase<PostLike> {

    private static final long serialVersionUID = -1275368473L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPostLike postLike = new QPostLike("postLike");

    public final com.ll.medium.global.jpa.QIdEntity _super = new com.ll.medium.global.jpa.QIdEntity(this);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final com.ll.medium.domain.member.member.entity.QMember member;

    public final com.ll.medium.domain.post.post.entity.QPost post;

    public QPostLike(String variable) {
        this(PostLike.class, forVariable(variable), INITS);
    }

    public QPostLike(Path<? extends PostLike> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPostLike(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPostLike(PathMetadata metadata, PathInits inits) {
        this(PostLike.class, metadata, inits);
    }

    public QPostLike(Class<? extends PostLike> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.ll.medium.domain.member.member.entity.QMember(forProperty("member")) : null;
        this.post = inits.isInitialized("post") ? new com.ll.medium.domain.post.post.entity.QPost(forProperty("post"), inits.get("post")) : null;
    }

}

