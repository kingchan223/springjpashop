package jpabook.jpashop.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Entity
public class Member {

    @Id @GeneratedValue
    @Column(name ="member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy="member")
    private List<Order> orders  = new ArrayList<>();

    public Member(){}

    private void setId(Long id) {
        this.id = id;
    }

    private void setName(String name) {
        this.name = name;
    }

    private void setAddress(Address address) {
        this.address = address;
    }

    private void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public static Member createLoginMember(String name, Address address){
        Member member = new Member();
        member.setName(name);
        member.setAddress(address);
        return member;
    }
}
