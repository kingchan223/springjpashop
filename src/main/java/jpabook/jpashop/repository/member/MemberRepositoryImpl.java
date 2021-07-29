package jpabook.jpashop.repository.member;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Primary
@RequiredArgsConstructor
@Repository
public class MemberRepositoryImpl implements MemberRepository {

    @PersistenceContext//엔티티매니저 주입. 생략가능
    private final EntityManager em;

    //저장
    public void save(Member member){
        em.persist(member);
    }

    //조회
    public Member findOne(Long id){
        return em.find(Member.class, id);
    }

    //모든 멤버 조회
    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

    //name으로 이름 조회
    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name=:name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}







