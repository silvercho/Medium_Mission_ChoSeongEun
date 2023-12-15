package com.ll.medium.domain.post.post.controller;

import com.ll.medium.domain.post.post.entity.Post;
import com.ll.medium.domain.post.post.service.PostService;
import com.ll.medium.global.exceptions.GlobalException;
import com.ll.medium.global.rq.rq.Rq;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final Rq rq;

    @GetMapping("/{id}")
    public String showDetail(@PathVariable long id) {
        rq.setAttribute("post", postService.findById(id).get());

        return "domain/post/post/detail";
    }
    @GetMapping("/list")
    public String showList(
            @RequestParam(defaultValue = "") String kw,
            @RequestParam(defaultValue = "1") int page
    ) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("id"));
        Pageable pageable = PageRequest.of(page - 1, 10, Sort.by(sorts));

        Page<Post> postPage = postService.search(kw, pageable);
        rq.setAttribute("postPage", postPage);
        rq.setAttribute("page", page);

        return "domain/post/post/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/myList")
    public String showMyList(
            @RequestParam(defaultValue = "") String kw,
            @RequestParam(defaultValue = "1") int page,
            Principal principal // 현재 로그인한 사용자 정보를 얻기 위한 Principal 객체
    ) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("id"));
        Pageable pageable = PageRequest.of(page - 1, 10, Sort.by(sorts));

        Page<Post> postPage = postService.search(rq.getMember(),null, kw, pageable);
        rq.setAttribute("postPage", postPage);
        rq.setAttribute("page", page);

        return "domain/post/post/myList";
    }

    @Getter
    @Setter
    public static class WriteForm {
        @NotBlank
        private String title;
        @NotBlank
        private String body;
        private boolean isPublished;
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/write")
    public String showWrite() {
        return "domain/post/post/write";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/write")
    public String write(@Valid WriteForm writeForm) {
        Post post =postService.write(rq.getMember(), writeForm.getTitle(), writeForm.getBody(), writeForm.isPublished());

        return rq.redirect("/post/"+ post.getId(), post.getId() + "번 게시물 생성되었습니다.");
    }
    @GetMapping("/detail/{id}")
    public String showDetail(Model model, @PathVariable long id) {
        // PathVariable 을 사용하여 몇번 게시물을 보여줘야 할지 입력받음.
        Post post = postService.findById(id).get();
        model.addAttribute("post", post);
        return "domain/post/post/detail";
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}/modify")
    public String showModify(@PathVariable long id, Model model) {
        Post post = postService.findById(id).orElseThrow(() -> new GlobalException("404-1", "해당 글이 존재하지 않습니다."));

        if (!postService.canModify(rq.getMember(), post)) throw new GlobalException("403-1", "권한이 없습니다.");

        model.addAttribute("post", post);

        return "domain/post/post/modify";
    }

    @Getter
    @Setter
    public static class ModifyForm {
        @NotBlank
        private String title;
        @NotBlank
        private String body;
        private boolean isPublished;
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{id}/modify")
    public String modify(@PathVariable long id, @Valid ModifyForm form) {
        Post post = postService.findById(id).orElseThrow(() -> new GlobalException("404-1", "해당 글이 존재하지 않습니다."));

        if (!postService.canModify(rq.getMember(), post)) throw new GlobalException("403-1", "권한이 없습니다.");

        postService.modify(post, form.getTitle(), form.getBody(), form.isPublished());

        return rq.redirect("/post/" + post.getId(), post.getId() + "번 글이 수정되었습니다.");
    }
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable long id) {
        Post post = postService.findById(id).orElseThrow(() -> new GlobalException("404-1", "해당 글이 존재하지 않습니다."));

        if (!postService.canDelete(rq.getMember(), post)) throw new GlobalException("403-1", "권한이 없습니다.");

        postService.delete(post);

        return rq.redirect("/post/list", post.getId() + "번 글이 삭제되었습니다.");
    }
}