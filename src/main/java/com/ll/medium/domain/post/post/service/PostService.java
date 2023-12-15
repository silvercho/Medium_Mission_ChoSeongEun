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
    public Post write(String username, String title, String body, boolean isPublished) {
        Member author = memberService.findByUsername(username).get();

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
        return postRepository.findByIsPublishedAndTitleContainingIgnoreCaseOrBodyContainingIgnoreCase(true, kw,true, kw, pageable);
    }

    public Page<Post> search(Member author, String kw, Pageable pageable) {
        return postRepository.findByAuthorAndTitleContainingIgnoreCaseOrAuthorAndBodyContainingIgnoreCase(author, kw, author, kw, pageable);
    }

    public Page<Post> findPostsByUsername(String username, String kw, Pageable pageable) {
        return postRepository.findByIsPublishedAndTitleContainingIgnoreCaseOrBodyContainingIgnoreCase(true, username,true, kw , pageable);
    }
    @Transactional
    public Post update(String username, String title, String body, boolean isPublished, Long id)  {
        Optional<Post> optionalPost = postRepository.findById(id);

        Member author = memberService.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다: " + username));
        Post post = optionalPost.get();

        post.setTitle(title);
        post.setBody(body);
        post.setPublished(isPublished);

        return postRepository.save(post);
    }
    @Transactional
    public void deleteById(Long id) {
        Optional<Post> postOptional = postRepository.findById(id);
        postOptional.ifPresent(postRepository::delete);
    }
}
