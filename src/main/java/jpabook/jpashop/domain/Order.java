package jpabook.jpashop.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access= AccessLevel.PROTECTED)// == protected Order(){} 같은거임
@Table(name="orders")/*order예약어를 피라기 우해 관례상 orders를 테이블명으로 한다.*/
@Getter
@Entity
public class Order {

    @Id @GeneratedValue
    @Column(name ="order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) /*지연로딩 -> 데이터응 DB에서 끌고 오는게 아니라, order에서 끌고온다. 1.일단 프록시 멤버를 넣어둔다.(바이트 버디) 2.멤버 객체를 만들 때 그때 DB에서 가져온다.*/
    @JoinColumn(name="member_id")
    private Member member;

    /*배송과 1:1관계. 자주 access하는 Order를 연관관계의 주인으로 한다.*/
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="delivery_id")
    private Delivery delivery;

    /*컬렉션은 필드에서 바로 초기화하는 것이 안전하다.*/
    @OneToMany(mappedBy="order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();/*컬렉션 바로 초기화*/

    //order_date
    private LocalDateTime orderDate; //주문 시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문 상태: ORDER, CANCEL

//    //다른데서 마음대로 생성못하도록 제약걸어두기 ->@NoArgsConstructor(access= AccessLevel.PROTECTED)로 대체
//    protected Order(){}

    //==연관관계 편의 메서드==//
    public void saveMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void saveDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    //==생성 메서드==//
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems){
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for(OrderItem orderItem: orderItems){
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    //주문 취소
    public void cancel(){
        if(delivery.getDeliveryStatus() == DeliveryStatus.COMP){
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }

        this.setStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    //==조회 로직==

    //전체 주문 가격 조회
    public int getTotalPrice(){
//        int totalPrice = 0;
//        for(OrderItem orderItem: orderItems){
//            totalPrice += orderItem.getTotalPrice();
//        }
//        return totalPrice;
        return orderItems.stream()
                .mapToInt(OrderItem::getTotalPrice)
                .sum();
    }


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




