package com.ll.medium.domain.post.post.repository;

import com.ll.medium.domain.member.member.entity.Member;
import com.ll.medium.domain.post.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
    List<Post> findTop30ByPublishStatusOrderByIdDesc(boolean isPublished);

    <T> List<T> findByPublishStatusOrderByIdDesc(boolean isPublished, Class<T> type);

    <T> List<T> findByAuthorOrderByIdDesc(Member author, Class<T> type);

    <T> Optional<T> findById(long id, Class<T> type);
}