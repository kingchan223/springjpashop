package jpabook.jpashop.repository.order;

import jpabook.jpashop.domain.Order;
import jpabook.jpashop.repository.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Primary
@RequiredArgsConstructor
@Repository
public class OrderRepositoryImpl implements OrderRepository {

    private final EntityManager em;

    public void save(Order order){
        em.persist(order);
    }

    public Order findOne(Long id){
        return em.find(Order.class, id);
    }

//    public List<Order> findAll(OrderSearch orderSearch){ }
}
