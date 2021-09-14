package jpabook.jpashop.repository.order.query;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Data
@EqualsAndHashCode(of ="orderId")//컬렉터에서 group By할 때 orderId로 묶는다.
public class OrderQueryDTO {
    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;
    private List<OrderItemsQueryDTO> orderItems;
//    o.id, m.name, o.orderDate, o.status, d.address
    public OrderQueryDTO(Long orderId,
                         String name,
                         LocalDateTime orderDate,
                         OrderStatus orderStatus,
                         Address address){
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.name = name;
        this.orderStatus = orderStatus;
        this.address = address;
    }

    public OrderQueryDTO(Long orderId, String name, LocalDateTime orderDate, OrderStatus orderStatus, Address address, List<OrderItemsQueryDTO> value) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.name = name;
        this.orderStatus = orderStatus;
        this.address = address;
        this.orderItems = value;
    }
}
