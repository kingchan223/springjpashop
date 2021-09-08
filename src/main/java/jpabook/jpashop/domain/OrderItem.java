package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jpabook.jpashop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Entity
public class OrderItem {

    @Id @GeneratedValue
    @Column(name ="order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="item_id")
    private Item item;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="order_id")
    private Order order;

    private int orderPrice;
    private int count;

    private void setId(Long id) {
        this.id = id;
    }
    private void setItem(Item item) {
        this.item = item;
    }
    public void setOrder(Order order) {
        this.order = order;
    }
    private void setOrderPrice(int orderPrice) {
        this.orderPrice = orderPrice;
    }
    private void setCount(int count) {
        this.count = count;
    }

    //==생성 메서드==
    public static OrderItem createOrderItem (Item item, int count){
        //--orderItem 세팅하기
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(item.getPrice());
        orderItem.setCount(count);

        //--item 개수 줄여주기 --> 여기서 줄이면 JPA가 dirty checking해준다.
        item.removeStock(count);
        return orderItem;
    }

    //==비즈니스 로직==
    public void cancel() {
        getItem().addStock(count);
    }

    //==조회 로직==
    public int getTotalPrice() {
        return getOrderPrice()*getCount();
    }
}

