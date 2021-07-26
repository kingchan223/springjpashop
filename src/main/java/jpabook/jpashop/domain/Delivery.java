package jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Delivery {

    @Id @GeneratedValue
    @Column(name="delivery_id")
    private Long id;

    @OneToOne(mappedBy="delivery")
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus; // READY, COMP

    private void setId(Long id) {
        this.id = id;
    }
    private void setOrder(Order order) {
        this.order = order;
    }
    private void setAddress(Address address) {
        this.address = address;
    }
    private void setDeliveryStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }
}
