package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
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

    /*@JsonIgnore*/ //얘를 넣으면 회원 api로 회원정보 조회시 회원 orders가 빠진다. 사용하지 않는 것이 좋음
    @OneToMany(mappedBy="member")
    private List<Order> orders  = new ArrayList<>();

    public Member(){}

    private void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
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
