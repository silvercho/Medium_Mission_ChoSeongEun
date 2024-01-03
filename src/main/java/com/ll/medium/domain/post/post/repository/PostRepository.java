package com.ll.medium.domain.post.post.repository;

import com.ll.medium.domain.member.member.entity.Member;
import com.ll.medium.domain.post.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
    List<Post> findTop30ByPublishedOrderByIdDesc(boolean isPublished);

    <T> List<T> findByPublishedOrderByIdDesc(boolean isPublished, Class<T> type);

    <T> List<T> findByAuthorOrderByIdDesc(Member author, Class<T> type);
}