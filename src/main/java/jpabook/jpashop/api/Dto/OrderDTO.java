package jpabook.jpashop.api.Dto;

import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class OrderDTO {
    private OrderStatus status;
    private LocalDateTime orderDate;
    public static OrderDTO create(Order order){
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setStatus(order.getStatus());
        orderDTO.setOrderDate(order.getOrderDate());

        return orderDTO;
    }
}
