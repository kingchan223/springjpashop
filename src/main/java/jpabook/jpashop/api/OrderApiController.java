package jpabook.jpashop.api;

import jpabook.jpashop.api.Dto.OrderDTO;
import jpabook.jpashop.api.Dto.OrderDTO2;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.repository.order.OrderRepository;
import jpabook.jpashop.repository.order.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class OrderApiController {
    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/orders")
    public List<Order> ordersV1(){
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        for (Order order : all) {
            order.getMember().getName();//연관관계 엔티티 강제 초기화
            order.getDelivery().getAddress();//연관관계 엔티티 강제 초기화
            List<OrderItem> orderItems = order.getOrderItems();//연관관계 엔티티 강제 초기화
            orderItems.forEach(o -> o.getItem().getName());//연관관계 엔티티 강제 초기화
            //연관관계 엔티티 강제 초기화를 해서 연관관계 엔티티를 프록시가 아닌 진짜 객체로 채우기
        }
        return all;
    }

    @GetMapping("/api/v2/orders")
    public List<OrderDTO2> ordersV2(){
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());
        List<OrderDTO2> collect = orders.stream().map(OrderDTO2::create).collect(Collectors.toList());

        return collect;
    }

    @GetMapping("/api/v3/orders")
    public List<OrderDTO2> ordersV3(){
        List<Order> orders = orderRepository.findAllWithItem();
        List<OrderDTO2> collect = orders.stream().map(OrderDTO2::create).collect(Collectors.toList());

        return collect;
    }
}
