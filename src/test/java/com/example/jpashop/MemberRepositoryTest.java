package com.example.jpashop;

import com.example.jpashop.domain.Member;
import com.example.jpashop.dto.MemberDto;
import com.example.jpashop.repository.MemberJpaRepository;
import com.example.jpashop.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

   /* @Test
    public void paging() {
        memberRepository.save(new Member("member1",10));
        memberRepository.save(new Member("member2",10));
        memberRepository.save(new Member("member3",10));
        memberRepository.save(new Member("member4",10));
        memberRepository.save(new Member("member5",10));

        int age=10;
        int offset=0;
        int limit=3;

        List<Member> members = memberRepository.findByPage(age, offset, limit);
        Long totalCount = memberRepository.totalCount(age);

        Assertions.assertThat(members.size()).isEqualTo(3);
        Assertions.assertThat(totalCount).isEqualTo(5);


    }*/

     @Test
    public void paging() {
        memberRepository.save(new Member("member1",10));
        memberRepository.save(new Member("member2",10));
        memberRepository.save(new Member("member3",10));
        memberRepository.save(new Member("member4",10));
        memberRepository.save(new Member("member5",10));

         int age=10;

         /*pageNumber는 사용할 페이지 수
                 pageSize는 한 페이지에 들어가는 객체 수*/
         PageRequest pageRequest=PageRequest.of(0,3, Sort.by(Sort.Direction.DESC,"username"));


         //이걸 그대로 반환하면 절대 안되지 디티오로 감싸야해
         Page<Member> page=memberRepository.findByAge(age,pageRequest);
         //이렇게! 매우 중요한 부분이다
         Page<MemberDto> toMap = page.map(member -> new MemberDto(member.getId(), member.getUsername(), null));



         List<Member> content = page.getContent();
         long totalElements = page.getTotalElements();

         for (Member member : content) {
             System.out.println("member = " + member);
         }


     }
}
