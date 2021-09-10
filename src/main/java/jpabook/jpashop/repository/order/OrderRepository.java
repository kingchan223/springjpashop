package jpabook.jpashop.repository.order;

import jpabook.jpashop.api.Dto.OrderSimpleQueryDTO;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import org.springframework.util.StringUtils;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public interface OrderRepository {
    void save(Order order);

    Order findOne(Long id);

    List<Order> findAllByString(OrderSearch orderSearch);

    List<Order> findAllByCriteria(OrderSearch orderSearch);

    List<Order> findAllWithMemberDelivery();

    List<OrderSimpleQueryDTO> findOrderDTOs();

    List<Order> findAllWithItem();
}