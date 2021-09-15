package jpabook.jpashop.service.query;

import jpabook.jpashop.api.Dto.OrderDTO2;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.repository.order.OrderRepository;
import jpabook.jpashop.repository.order.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Transactional(readOnly=true)
@Service
public class OrderQueryService {

    private final OrderRepository orderRepository;

    public List<Order> ordersInServiceV1(){
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


    public List<OrderDTO2> ordersInServiceV3(){
        List<Order> orders = orderRepository.findAllWithItem();
        List<OrderDTO2> collect = orders.stream().map(OrderDTO2::create).collect(toList());
        return collect;
    }

}
