package jpabook.jpashop.api;

import jpabook.jpashop.api.Dto.OrderDTO;
import jpabook.jpashop.api.Dto.OrderDTO2;
import jpabook.jpashop.api.Dto.OrderSimpleQueryDTO;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.repository.order.OrderRepository;
import jpabook.jpashop.repository.order.OrderSearch;
import jpabook.jpashop.repository.order.query.OrderQueryDTO;
import jpabook.jpashop.repository.order.query.OrderQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class OrderApiController {

    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;

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

    @GetMapping("/api/v3.1/orders")
    public List<OrderDTO2> ordersV3_pageable(@RequestParam(value="offset", defaultValue="0") int offset,
                                             @RequestParam(value="limit", defaultValue="100") int limit){
        List<Order> orders = orderRepository.findAllWithMemberDelivery(offset, limit);//to one관계는 모두 fetch join.
        List<OrderDTO2> collect = orders.stream().map(OrderDTO2::create).collect(Collectors.toList());

        return collect;
    }

    @GetMapping("/api/v4/orders")
    public List<OrderQueryDTO> ordersV4(){
        return orderQueryRepository.findOrderQueryDTOs();
    }

    @GetMapping("/api/v5/orders")
    public List<OrderQueryDTO> ordersV5(){
        return orderQueryRepository.findAllByDTO_optimization();
    }
}
