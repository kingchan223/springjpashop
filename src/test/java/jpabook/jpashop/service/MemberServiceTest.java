package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.member.MemberService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)//스프링 통합 테스트로 하겠습니다.
@SpringBootTest//스프링을 띄운상태로 테스트하겠습니다.(없으면 @Autowired같은 어노테이션 사용 불가)
@Transactional//디폴트는 rollback을 한다.
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
//    @Autowired MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception{
        //given
        Member member = new Member();
        member.setName("lee");
        //when
        Long savedId = memberService.join(member);
        //then
        Assert.assertEquals(member, memberService.findOne(savedId));
    }

    @Test(expected=IllegalStateException.class)
    public void 중복_회원_예외() throws Exception{
        //given
        Member member1 = new Member();
        member1.setName("lee");
        Member member2 = new Member();
        member2.setName("lee");
        //when
        memberService.join(member1);
        memberService.join(member2);//여기서 예외 발생
        //then
        Assert.fail("예외가 발생해야 한다.");
    }
}


