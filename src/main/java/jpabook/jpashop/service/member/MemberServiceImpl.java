package jpabook.jpashop.service.member;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.member.MemberRepository;
import jpabook.jpashop.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)//readOnly=true로 하면 JPA가 조회시 성능을 최적화한다.
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    //회원가입
    @Transactional//따로 선언한 @Transactional은 우선권이 더 높다.
    public Long join(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    //같은 이름 멤버 검증 메소드
    public void validateDuplicateMember(Member member) {
        //EXCEPTION
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){//실제로 이 제약조건은 문제가 될 수 있으니 member.name에 유니크 제약조건을 걸어두자.
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원전체조회
    public List<Member> findMember(){
        return memberRepository.findAll();
    }

    //회원 조회
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }
}
