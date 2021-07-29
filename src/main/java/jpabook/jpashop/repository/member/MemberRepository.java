package jpabook.jpashop.repository.member;

import jpabook.jpashop.domain.Member;

import java.util.List;

public interface MemberRepository {

    void save(Member member);

    //조회
    Member findOne(Long id);
    //모든 멤버 조회
    List<Member> findAll();

    //name으로 이름 조회
    List<Member> findByName(String name);
}
