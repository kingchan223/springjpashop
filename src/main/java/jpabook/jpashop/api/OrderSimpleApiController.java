package jpabook.jpashop.api;

import jpabook.jpashop.api.Dto.OrderSimpleQueryDTO;
import jpabook.jpashop.api.Dto.SimpleOrderDTO;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.repository.order.OrderRepository;
import jpabook.jpashop.repository.order.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/*
* x To One < to one 관계에 대해서 > : many to one과 one to one에서의 성능 최적화
* Order
* Order -> Member : many To One
* Order -> Delivery : one To One
* */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {
    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> orderV1(){ // >>> 무한루프에 빠지게 된다. -> 그래서 양반향 연관관계 시 하나에는 @JsonIgnore를 해줘야한다.-> 또 에러난다.
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        for (Order order : all) {
            //order.getMember()까지는 프록시 객체인데 뒤에 getName()까지 하면 DB에 쿼리가 날라가 프록시가 아닌 진짜 member객체가 된다. 즉 LAZY가 강제 초기화된다.
            order.getMember().getName();
        }
        return all;
    } //-> 외부에 엔티티 직접 노출된다.

    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDTO> orderV2(){
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());
        List<SimpleOrderDTO> result = orders.stream()
                .map(SimpleOrderDTO::new)
                .collect(Collectors.toList());
        return result;
    }/*하지만 v2도 lazy로딩으로 인해 너무 많은 쿼리가 발생한다.*/
    // N + 1 문제 : 1 + 회원 N + 배송 N
    // order가 2개 있으면 2개의 주문 가져오는 쿼리 + 주문한 멤버1 + 주문한 배송정보1 + 주문한 멤버2 + 주문한 배송정보2 총 5번의 쿼리가 나간다.

    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDTO> orderV3(){
        List<Order> orders = orderRepository.findAllWithMemberDelivery();
        List<SimpleOrderDTO> result = orders.stream()
                .map(SimpleOrderDTO::new)
                .collect(Collectors.toList());
        return result;
    }

    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDTO> orderV4(){
        List<OrderSimpleQueryDTO> orderDTOs = orderRepository.findOrderDTOs();
       return orderDTOs;
    }
}
