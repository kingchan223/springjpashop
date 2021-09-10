package jpabook.jpashop.api.Dto;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderDTO2 {

    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;
    private List<OrderItemDTO> orderItems;

    public static OrderDTO2 create(Order o){
        OrderDTO2 orderDTO2 = new OrderDTO2();
        orderDTO2.orderId = o.getId();
        orderDTO2.name = o.getMember().getName();
        orderDTO2.orderDate = o.getOrderDate();
        orderDTO2.orderStatus = o.getStatus();
        orderDTO2.address = o.getDelivery().getAddress();
        orderDTO2.orderItems = o.getOrderItems().stream().map(OrderItemDTO::create).collect(Collectors.toList());

        return orderDTO2;
    }
}
