package jpabook.jpashop.service.order;

import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.order.OrderSearch;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrderService {

    Long order(Long memberId, Long itemId, int count);

    void cancelOrder(Long orderId);

    List<Order> findOrders(OrderSearch orderSearch);
}
