package com.ll.medium.domain.post.post.controller;

import com.ll.medium.domain.post.post.entity.Post;
import com.ll.medium.domain.post.post.service.PostService;
import com.ll.medium.global.exceptions.GlobalException;
import com.ll.medium.global.rq.Rq.Rq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
@Tag(name = "PostController", description = "글 CRUD 컨트롤러")
public class PostController {
    private final PostService postService;
    private final Rq rq;

    @GetMapping("/{id}")
    @Operation(summary = "글 상세")
    public String showDetail(@PathVariable long id, Model model) {
        Post post = postService.findById(id).orElseThrow(() -> new GlobalException("404-1", "해당 글이 존재하지 않습니다."));

        boolean isUserPaid = isUserPaidMember();

        if (post.isPaid() && !isUserPaid) {
            model.addAttribute("isPaidMember", false); // 유료멤버십 확인 정보 전달
        } else {
            model.addAttribute("isPaidMember", true); // 유료멤버십 확인 정보 전달
            postService.increaseHit(post);
            model.addAttribute("post", post);
        }
        return "domain/post/post/detail";
    }
    private boolean isUserPaidMember() {
        // 실제로는 유료 멤버십 여부를 확인하는 로직이 필요합니다.
        // 여기서는 간단하게 ROLE_PAID 권한을 가진 사용자라고 가정합니다.
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_PAID"));
    }

    @GetMapping("/list")
    @Operation(summary = "글 목록")
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
            @RequestParam(defaultValue = "1") int page
    ) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("id"));
        Pageable pageable = PageRequest.of(page - 1, 10, Sort.by(sorts));

        Page<Post> postPage = postService.search(rq.getMember(), null, kw, pageable);
        rq.setAttribute("postPage", postPage);
        rq.setAttribute("page", page);

        return "domain/post/post/myList";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/write")
    public String showWrite() {
        return "domain/post/post/write";
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
    @PostMapping("/write")
    public String write(@Valid WriteForm form) {
        Post post = postService.write(rq.getMember(), form.getTitle(), form.getBody(), form.isPublished());

        return rq.redirect("/post/" + post.getId(), post.getId() + "번 글이 작성되었습니다.");
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

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{id}/like")
    public String like(@PathVariable long id) {
        Post post = postService.findById(id).orElseThrow(() -> new GlobalException("404-1", "해당 글이 존재하지 않습니다."));

        if (!postService.canLike(rq.getMember(), post)) throw new GlobalException("403-1", "권한이 없습니다.");

        postService.like(rq.getMember(), post);

        return rq.redirect("/post/" + post.getId(), post.getId() + "번 글을 추천하였습니다.");
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}/cancelLike")
    public String cancelLike(@PathVariable long id) {
        Post post = postService.findById(id).orElseThrow(() -> new GlobalException("404-1", "해당 글이 존재하지 않습니다."));

        if (!postService.canCancelLike(rq.getMember(), post)) throw new GlobalException("403-1", "권한이 없습니다.");

        postService.cancelLike(rq.getMember(), post);

        return rq.redirect("/post/" + post.getId(), post.getId() + "번 글을 추천취소하였습니다.");
    }
}