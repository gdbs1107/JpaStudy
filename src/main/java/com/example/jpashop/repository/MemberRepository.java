package com.example.jpashop.repository;

import com.example.jpashop.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member,Long> {

    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    /*프로퍼티에만 맞춰서 사용한다면 많은 메서드를 직접 만들 수 있음
    공식 홈페이지에서 쿼리를 만들 수 있는 방법을 알 수 있다*/


}
