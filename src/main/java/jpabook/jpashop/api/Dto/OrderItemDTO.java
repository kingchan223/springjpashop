package jpabook.jpashop.api.Dto;

import jpabook.jpashop.domain.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderItemDTO {
    private String itemName;
    private int orderPrice;
    private int count;

    public static OrderItemDTO create(OrderItem oi) {
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.itemName = oi.getItem().getName();
        orderItemDTO.orderPrice = oi.getOrderPrice();
        orderItemDTO.count = oi.getCount();

        return orderItemDTO;
    }
}
