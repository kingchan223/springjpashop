package jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name="orders")
@Getter
@Entity
public class Order {

    @Id @GeneratedValue
    @Column(name ="order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;

    @OneToOne
    @JoinColumn(name="delivery_id")
    private Delivery delivery;

    @OneToMany(mappedBy="order")
    private List<OrderItem> orderItems = new ArrayList<>();

    private LocalDateTime orderDate; //주문 시간

    @Enumerated
    private OrderStatus status; // 주문 상태: ORDER, CANCEL

    private void setId(Long id) {
        this.id = id;
    }
    private void setMember(Member member) {
        this.member = member;
    }
    private void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }
    private void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
    private void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }
    private void setStatus(OrderStatus status) {
        this.status = status;
    }
}
