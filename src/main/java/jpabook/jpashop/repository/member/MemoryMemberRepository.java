package jpabook.jpashop.repository.member;

import jpabook.jpashop.domain.Member;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//@Primary
@Repository
public class MemoryMemberRepository implements MemberRepository{

    private Map<Long, Member> store = new ConcurrentHashMap<>();
    private Long seq = 0L;

    @Override
    public void save(Member member) {
        System.out.println(" ⎡⎯------------------------------------------------⎤");
        System.out.println(" ⎜⎯-------------●●●● M E M B E R●●●●---------------⎜");
        System.out.println(" ⎜⎯-----------------●●●●●●--------------------⎜");
        System.out.println(" ⎜⎯--------------------●●●-----------------------⎜");
        System.out.println(" ⎜⎯----------------------●-------------------------⎜");
        System.out.println("⎿⎯--------------●●●●●●●●●●●--------------⏌");
        Long memberId = seq++;
        member.setId(memberId);
        store.put(memberId, member);
    }

    @Override
    public Member findOne(Long id) {
        return store.get(id);
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public List<Member> findByName(String name) {
        List<Member> memberList = findAll();
        List<Member> findMemberList = new ArrayList<>();
        for (Member member : memberList) {
            if(member.getName().equals(name)){
                findMemberList.add(member);
            }
        }
        return findMemberList;
    }
}
