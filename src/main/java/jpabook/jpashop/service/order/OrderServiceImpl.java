package jpabook.jpashop.service.order;


import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.item.ItemRepository;
import jpabook.jpashop.repository.member.MemberRepository;
import jpabook.jpashop.repository.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Primary
@RequiredArgsConstructor
@Transactional(readOnly = true )
@Service
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    //주문
    @Transactional//데이터를 변경하는 것이기 때문에 트랜잭션 단위로 묶는다.
    public Long order(Long memberId, Long itemId, int count){
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //주문상품 생성 : OrderItem은 Item과 수량(count)에 대한 정보가 필요하다.
        OrderItem orderItem = OrderItem.createOrderItem(item, count);

        //주문생성 : 주문은 주문한 회원정보, 배송지 정보, orderItem에 대한 정보가 필요하다.
        Order order = Order.createOrder(member, delivery, orderItem);

        orderRepository.save(order);//cascade덕에 orderitem, delivery모두 persist된다.
        //order만 delivery를 사용하고, order만 orderItem을 사용하기 때문에 cascade의 범위를 이렇게 해도된다. 라이프 사이클이 같다.
        return order.getId();
    }

    //취소
    @Transactional
    public void cancelOrder(Long orderId){
        //주문 엔티티조회
        Order order = orderRepository.findOne(orderId);
        //주문 취소
        order.cancel();//->내가 update문을 안날려도 jpa의 dirty checking으로 데이터 변경이 DB에 반영된다.

    }

    //검색
//    public List<Order> findOrders(OrderSearch orderSearch){
//        return orderRepository.findAll(orderSearch);
//    }
}
