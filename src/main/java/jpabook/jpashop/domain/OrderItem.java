package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class OrderItem {

    @Id @GeneratedValue
    @Column(name ="order_item_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name ="item_id")
    private Item item;

    @ManyToOne
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
    private void setOrder(Order order) {
        this.order = order;
    }
    private void setOrderPrice(int orderPrice) {
        this.orderPrice = orderPrice;
    }
    private void setCount(int count) {
        this.count = count;
    }
}
