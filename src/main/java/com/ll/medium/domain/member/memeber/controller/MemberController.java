package com.ll.medium.domain.member.memeber.controller;

import com.ll.medium.domain.member.memeber.entity.Member;
import com.ll.medium.domain.member.memeber.service.MemberService;
import com.ll.medium.global.rq.rq.Rq;
import com.ll.medium.global.rsData.RsData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final Rq rq;

    @GetMapping("/join")
    public String showJoin() {
        return "domain/member/member/join";
    }

    @Getter
    @Setter
    public static class JoinForm {
        @NotBlank
        private String username;
        @NotBlank
        private String password;
    }
    @PostMapping("/join")
    public String signup(@Valid JoinForm joinForm) {
        RsData<Member> joinRs = memberService.join(joinForm.getUsername(), joinForm.getPassword());

        return rq.redirectOrBack(joinRs, "/member/login");
    }
    @GetMapping("/login")
    public String showLogin() {
        return "domain/member/member/login";
    }
}
