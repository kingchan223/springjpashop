package jpabook.jpashop.service.member;


import jpabook.jpashop.domain.Member;

import java.util.List;

public interface MemberService {

    Long join(Member member);

    //같은 이름 멤버 검증 메소드
    void validateDuplicateMember(Member member);

    //회원전체조회
    List<Member> findMember();

    //회원 조회
    Member findOne(Long memberId);

    void update(Long id, String name);
}
