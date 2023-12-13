package com.ll.medium.domain.post.post.service;

import com.ll.medium.domain.post.post.entity.Post;
import com.ll.medium.domain.post.post.repository.PostRepository;
import jakarta.validation.constraints.NotBlank;
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

    @Transactional
    public Post write(String title, String body) {
        Post post = Post.builder()
                .title(title)
                .body(body)
                .build();

        postRepository.save(post);
        return post;
    }
    public Object findTop30ByIsPublishedOrderByIdDesc(boolean isPublished) {
        return
                postRepository.findTop30ByIsPublishedOrderByIdDesc(isPublished);
    }

    public Optional<Post> findById(long id) {
        return postRepository.findById(id);
    }

    public Page<Post> search(String kw, Pageable pageable) {
        return postRepository.findByTitleContainingIgnoreCaseOrBodyContainingIgnoreCase(kw, kw, pageable);
    }

    public Page<Post> findPostsByUsername(String username, String kw, Pageable pageable) {
        return postRepository.findByTitleContainingIgnoreCaseOrBodyContainingIgnoreCase(username, kw , pageable);
    }
    @Transactional
    public Optional<Post> update(Post post, @NotBlank String title, @NotBlank String body){
        postRepository.save(post);
        return findById(post.getId());
    }
    @Transactional
    public void delete(Long id) {
        postRepository.deleteById(id);
    }

}
