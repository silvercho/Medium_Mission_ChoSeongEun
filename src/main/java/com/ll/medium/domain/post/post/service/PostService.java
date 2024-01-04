package com.ll.medium.domain.post.post.service;

import com.ll.medium.domain.member.member.entity.Member;
import com.ll.medium.domain.post.post.entity.Post;
import com.ll.medium.domain.post.post.repository.PostRepository;
import com.ll.medium.domain.post.postComment.entity.PostComment;
import com.ll.medium.domain.post.postComment.repository.PostCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
    private final PostRepository postRepository;
    private final PostCommentRepository postCommentRepository;

    @Transactional
    public Post write(Member author, String title, String body, boolean isPublished) {
        Post post = Post.builder()
                .author(author)
                .title(title)
                .body(body)
                .publishStatus(isPublished)
                .build();

        return postRepository.save(post);
    }

    public Object findTop30ByPublishedOrderByIdDesc(boolean isPublished) {
        return postRepository.findTop30ByPublishStatusOrderByIdDesc(isPublished);
    }

    public <T> List<T> findByPublished(boolean isPublished, Class<T> type) {
        return postRepository.findByPublishStatusOrderByIdDesc(isPublished, type);
    }

    public <T> List<T> findByAuthor(Member author, Class<T> type) {
        return postRepository.findByAuthorOrderByIdDesc(author, type);
    }
    public <T> Optional<T> findById(long id, Class<T> type) {
        return postRepository.findById(id, type);
    }
    public Optional<Post> findById(long id) {
       return postRepository.findById(id);
    }

    public Page<Post> search(String kw, Pageable pageable) {
        return postRepository.search(true, kw, pageable);
    }

    public Page<Post> search(Member author, Boolean isPublished, String kw, Pageable pageable) {
        return postRepository.search(author, isPublished, kw, pageable);
    }

    public boolean canLike(Member actor, Post post) {
        if (actor == null) return false;

        return !post.hasLike(actor);
    }

    public boolean canCancelLike(Member actor, Post post) {
        if (actor == null) return false;

        return post.hasLike(actor);
    }

    public boolean canModify(Member actor, Post post) {
        if (actor == null) return false;

        return actor.equals(post.getAuthor());
    }

    public boolean canDelete(Member actor, Post post) {
        if (actor == null) return false;

        if (actor.isAdmin()) return true;

        return actor.equals(post.getAuthor());
    }

    @Transactional
    public void modify(Post post, String title, String body, boolean published) {
        post.setTitle(title);
        post.setBody(body);
        post.setPublishStatus(published);
    }

    @Transactional
    public void delete(Post post) {
        postRepository.delete(post);
    }

    @Transactional
    public void increaseHit(Post post) {
        post.increaseHit();
    }

    @Transactional
    public void like(Member actor, Post post) {
        post.addLike(actor);
    }

    @Transactional
    public void cancelLike(Member actor, Post post) {
        post.deleteLike(actor);
    }

    @Transactional
    public PostComment writeComment(Member actor, Post post, String body) {
        return post.writeComment(actor, body);
    }

    public boolean canModifyComment(Member actor, PostComment comment) {
        if (actor == null) return false;

        return actor.equals(comment.getAuthor());
    }

    public boolean canDeleteComment(Member actor, PostComment comment) {
        if (actor == null) return false;

        if (actor.isAdmin()) return true;

        return actor.equals(comment.getAuthor());
    }

    public Optional<PostComment> findCommentById(long id) {
        return postCommentRepository.findCommentById(id);
    }

    @Transactional
    public void modifyComment(PostComment postComment, String body) {
        postComment.setBody(body);
    }

    @Transactional
    public void deleteComment(PostComment postComment) {
        postCommentRepository.delete(postComment);
    }
}