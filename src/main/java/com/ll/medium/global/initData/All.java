package com.ll.medium.global.initData;

import com.ll.medium.domain.member.member.service.MemberService;
import com.ll.medium.global.app.AppConfig;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.io.File;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class All {
    private final MemberService memberService;

    @Bean
    @Order(2)
    public ApplicationRunner initAll() {
        return args -> {
            if (memberService.findByUsername("system").isPresent()) return;

            memberService.join("system", "1234");
            memberService.join("admin", "1234");
        };
    }
    @Transactional
    public void work1() {
        new File(AppConfig.getTempDirPath()).mkdirs();
    }
}
