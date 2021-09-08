package jpabook.jpashop.api.Dto;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SimpleOrderDTO {
    private Long orderId;
    private String name;
    private LocalDateTime orderTime;
    private OrderStatus orderStatus;
    private Address address;

    public SimpleOrderDTO(Order o) {
        orderId = o.getId();
        name = o.getMember().getName();// lazy초기화 : 현재 영속성 컨텍스트에 member가 있는지 찾아보고 없으니 DB로 쿼리 날림
        orderTime = o.getOrderDate();
        orderStatus = o.getStatus();
        address = o.getDelivery().getAddress();// lazy초기화
    }
}
