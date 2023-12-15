package com.ll.medium.domain.post.post.service;

import com.ll.medium.domain.member.member.entity.Member;
import com.ll.medium.domain.member.member.service.MemberService;
import com.ll.medium.domain.post.post.entity.Post;
import com.ll.medium.domain.post.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberService memberService;

    @Transactional
    public Post write(Member author, String title, String body, boolean isPublished) {

        Post post = Post.builder()
                .author(author)
                .title(title)
                .body(body)
                .isPublished(isPublished)
                .build();

        return postRepository.save(post);
    }
    public Object findTop30ByIsPublishedOrderByIdDesc(boolean isPublished) {
        return
                postRepository.findTop30ByIsPublishedOrderByIdDesc(isPublished);
    }

    public Optional<Post> findById(long id) {
        return postRepository.findById(id);
    }

    public Page<Post> search(String kw, Pageable pageable) {
        return postRepository.search(true, kw, pageable);
    }

    public Page<Post> search(Member author, Boolean isPublished, String kw, Pageable pageable) {
        return postRepository.search(author,isPublished, kw, pageable);
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

    @Transactional
    public void modify(Post post, String title, String body, boolean published) {
        post.setTitle(title);
        post.setBody(body);
        post.setPublished(published);
    }

    public boolean canDelete(Member actor, Post post) {
        if (actor == null) return false;
        if ( actor.isAdmin() ) return true;

        return actor.equals(post.getAuthor());
    }

    @Transactional
    public void delete(Post post) {
        postRepository.delete(post);
    }
    @Transactional
    public void increaseHit(Post post){
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
}
