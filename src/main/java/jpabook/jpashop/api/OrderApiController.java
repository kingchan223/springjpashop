package jpabook.jpashop.api;

import jpabook.jpashop.api.Dto.OrderDTO2;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.repository.order.OrderRepository;
import jpabook.jpashop.repository.order.OrderSearch;
import jpabook.jpashop.repository.order.query.OrderFlatDTO;
import jpabook.jpashop.repository.order.query.OrderItemsQueryDTO;
import jpabook.jpashop.repository.order.query.OrderQueryDTO;
import jpabook.jpashop.repository.order.query.OrderQueryRepository;
import jpabook.jpashop.service.query.OrderQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

@RequiredArgsConstructor
@RestController
public class OrderApiController {

    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;
    private final OrderQueryService orderQueryService;

    @GetMapping("/api/v1/orders")
    public List<Order> ordersV1(){
        return orderQueryService.ordersInServiceV1();
    }

    @GetMapping("/api/v2/orders")
    public List<OrderDTO2> ordersV2(){
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());
        List<OrderDTO2> collect = orders.stream().map(OrderDTO2::create).collect(toList());

        return collect;
    }


    @GetMapping("/api/v3/orders")
    public List<OrderDTO2> ordersV3(){
        return orderQueryService.ordersInServiceV3();
    }

    @GetMapping("/api/v3.1/orders")
    public List<OrderDTO2> ordersV3_pageable(@RequestParam(value="offset", defaultValue="0") int offset,
                                             @RequestParam(value="limit", defaultValue="100") int limit){
        List<Order> orders = orderRepository.findAllWithMemberDelivery(offset, limit);//to one관계는 모두 fetch join.
        List<OrderDTO2> collect = orders.stream().map(OrderDTO2::create).collect(toList());

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

    @GetMapping("/api/v6/orders")
    public List<OrderQueryDTO> ordersV6(){
        List<OrderFlatDTO> flats = orderQueryRepository.findAllByDTO_flat();
        return flats
                .stream()
                .collect(groupingBy(o -> new OrderQueryDTO(o.getOrderId(), o.getName(), o.getOrderDate(), o.getOrderStatus(), o.getAddress()),mapping(o -> new OrderItemsQueryDTO(o.getOrderId(), o.getItemName(), o.getOrderPrice(), o.getCount()), toList())))
                .entrySet()
                .stream()
                .map(e -> new OrderQueryDTO(e.getKey().getOrderId(), e.getKey().getName(), e.getKey().getOrderDate(), e.getKey().getOrderStatus(),e.getKey().getAddress(), e.getValue()))
                .collect(toList());
    }
}
