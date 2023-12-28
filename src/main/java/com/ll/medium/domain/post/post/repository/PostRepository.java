package com.ll.medium.domain.post.post.repository;

import com.ll.medium.domain.member.member.entity.Member;
import com.ll.medium.domain.post.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
    List<Post> findTop30ByIsPublishedOrderByIdDesc(boolean isPublished);

    List<Post> findByIsPublishedOrderByIdDesc(boolean isPublished);

    List<Post> findByAuthorOrderByIdDesc(Member author);
}