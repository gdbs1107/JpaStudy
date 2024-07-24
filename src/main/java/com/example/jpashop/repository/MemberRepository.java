package com.example.jpashop.repository;

import com.example.jpashop.domain.Member;
import com.example.jpashop.dto.MemberDto;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {

    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    /*프로퍼티에만 맞춰서 사용한다면 많은 메서드를 직접 만들 수 있음
    공식 홈페이지에서 쿼리를 만들 수 있는 방법을 알 수 있다*/


    //파라미터가 길어지거나 복잡한 로직시에 자주 사용
    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findByUser(@Param("username") String username, @Param("age") Integer age);

    @Query("select m.username from Member m")
    List<String> findUsernameList();


    /*dto로 반환하기
            이러면 서비스에 이런 로직이 필요가 없어지는건가..?*/
    @Query("select new com.example.jpashop.dto.MemberDto(m.id,m.username, t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto();

    List<Member> findListByUsername(String username); //리스트로 반환, 리스트에 값이 없어도 널이 아니라 빈 리스트를 반환한다!
    Member findMemberByUsername(String username); //단건 반환, 값이 없으면 널을 반환, 중복값 조회시 예외반환
    Optional<Member> findOptionalMemberByUsername(String username); //옵셔널로 반환


    @Query(value = "select m from Member m left join m.team t",
            countQuery = "select count(m) from Member m")
    Page<Member> findByAge(int age, Pageable pageable);

    @Modifying(clearAutomatically = true) //excuteUpdate를 호출해주기 위한 어노테이션, 영속성 컨텍스트 클리어를 해줘야함
    @Query("update Member m set m.age= m.age+1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);








    //프록시 객체가 아니라 진짜 객체를 당김
    @Query("select m from Member m left join fetch m.team")
    List<Member> findMemberFetch();

    //JPQL 생략 할 수 있음!
    @Override
    @EntityGraph(attributePaths = {"team"})
    List<Member> findAll();

    //회원조회시, 다른 로직에서 거의 팀을 활용한다면 회원조회 시 기본으로 팀 조회하는 방법도 있음
    @EntityGraph(attributePaths = {"team"})
    List<Member> findByUsername(String username);




    //JPA의 변경감지를 무시하게 해줌, 쓸 일 별로 없음
    @QueryHints(value = @QueryHint(name="org.hibernate.readOnly",value = "true"))
    Member findReaOnly(String username);

    //lock기능 제공, 굉장히 수준이 높은 개념임
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Member> findLockByUsername(String username);

}
