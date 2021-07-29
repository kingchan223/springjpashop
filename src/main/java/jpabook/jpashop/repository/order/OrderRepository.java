package jpabook.jpashop.repository.order;

import jpabook.jpashop.domain.Order;

public interface OrderRepository {
    void save(Order order);

    Order findOne(Long id);
}
