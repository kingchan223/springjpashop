package jpabook.jpashop.api.Dto;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MemberDTO {
    private Long id;
    private String name;
    private List<OrderDTO> orderDTOs;

    public static MemberDTO create(Member member){
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(member.getId());
        memberDTO.setName(member.getName());
        List<Order> orders = member.getOrders();
        List<OrderDTO> orderDTOs = new ArrayList<>();
        for (Order order : orders) {
            OrderDTO orderDTO = OrderDTO.create(order);
            orderDTOs.add(orderDTO);
        }
        memberDTO.setOrderDTOs(orderDTOs);

        return memberDTO;
    }
}
