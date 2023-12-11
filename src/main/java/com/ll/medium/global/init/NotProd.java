package com.ll.medium.global.init;

import com.ll.medium.domain.member.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class NotProd {
    private final MemberService memberService;
    @Bean
    public ApplicationRunner initNotProd() {
        return args -> log.debug("NotProd 실행됨");
    }
}
