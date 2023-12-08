package com.ll.medium.domain.member.memeber.repository;

import com.ll.medium.domain.member.memeber.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {
    Optional<Member> findByUsername(String username);
}
