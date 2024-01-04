package com.ll.medium.global.initData;

import com.ll.medium.domain.member.member.entity.Member;
import com.ll.medium.domain.member.member.service.MemberService;
import com.ll.medium.domain.post.post.entity.Post;
import com.ll.medium.domain.post.post.service.PostService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;

import java.util.List;
import java.util.stream.IntStream;

@Configuration
@Profile("!prod")
@Slf4j
@RequiredArgsConstructor
public class NotProd {
    @Autowired
    @Lazy
    private com.ll.medium.global.initData.NotProd self;
    private final MemberService memberService;
    private final PostService postService;

    @Bean
    @Order(3)
    public ApplicationRunner initNotProd() {
        return args -> {
            if (memberService.findByUsername("user1").isPresent()) return;

            self.work1();
        };
    }
    @Transactional
    public void work1() {
        Member memberAdmin = memberService.join("admin", "1234", "관리자","").getData();
        Member memberUser1 = memberService.join("user1", "1234", "감자","").getData();
        Member memberUser2 = memberService.join("user2", "1234","고구마","").getData();
        Member memberUser3 = memberService.join("user3", "1234","새싹","").getData();
        Member memberUser4 = memberService.join("user4", "1234","가지","").getData();

        Post post1 = postService.write(memberUser1, "제목 1", "내용 1", true);
        Post post2 = postService.write(memberUser1, "제목 2", "내용 2", true);
        Post post3 = postService.write(memberUser1, "제목 3", "내용 3", false);
        Post post4 = postService.write(memberUser1, "제목 4", "내용 4", true);
        Post post5 = postService.write(memberUser2, "제목 5", "내용 5", true);
        Post post6 = postService.write(memberUser2, "제목 6", "내용 6", false);

        IntStream.rangeClosed(7, 50).forEach(i -> {
            postService.write(memberUser3, "제목 " + i, "내용 " + i, true);
        });

        // 유료 멤버십 회원 생성
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Member paidMember = memberService.join("paid_user_" + i, "1234","머니","").getData();
            paidMember.setPaid(true);
        });

        // 유료 글 생성
        List<Member> paidMembers = memberService.findAllPaidMembers(); // 유료 멤버십 회원 리스트 가져오기

        paidMembers.forEach(member -> {
            IntStream.rangeClosed(1, 3).forEach(i -> { // 각 유료 멤버당 3개의 유료 글 생성
                postService.write(member, "유료 글 제목 " + i + " - " + member.getUsername(), "유료 글 내용 " + i, true);
            });
        });


        postService.like(memberUser2, post1);
        postService.like(memberUser3, post1);
        postService.like(memberUser4, post1);

        postService.like(memberUser2, post2);
        postService.like(memberUser3, post2);

        postService.like(memberUser2, post3);
    }
}