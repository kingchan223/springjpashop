package jpabook.jpashop.repository.order.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//특정 목적에 맞춘 리퍼지토리
@RequiredArgsConstructor
@Repository
public class OrderQueryRepository {
    private final EntityManager em;

    public List<OrderQueryDTO> findOrderQueryDTOs() {
        List<OrderQueryDTO> result = findOrders();//query 1번 -> +N개
        result.forEach(o->{
            List<OrderItemsQueryDTO> orderItems = findOrderItems(o.getOrderId());
            o.setOrderItems(orderItems);
        });
        return result;
    }

    private List<OrderQueryDTO> findOrders() {
        return em.createQuery(
                "select new jpabook.jpashop.repository.order.query.OrderQueryDTO(o.id, m.name, o.orderDate, o.status, d.address)"
                        + " from Order o"
                        + " join o.member m"
                        +" join o.delivery d", OrderQueryDTO.class)
                .getResultList();
    }

    private List<OrderItemsQueryDTO> findOrderItems(Long orderId) {
        return em.createQuery("select " +
                "new jpabook.jpashop.repository.order.query.OrderItemsQueryDTO(oi.order.id, i.name, oi.orderPrice, oi.count)" +
                " from OrderItem oi"+
                " join oi.item i"+
                " where oi.order.id = :orderId", OrderItemsQueryDTO.class)
                .setParameter("orderId", orderId)
                .getResultList();
    }


    public List<OrderQueryDTO> findAllByDTO_optimization() {
        List<OrderQueryDTO> result = findOrders();
        List<Long> orderIds = result.stream()
                .map(OrderQueryDTO::getOrderId).collect(Collectors.toList());

        List<OrderItemsQueryDTO> orderItems
                = em.createQuery("select" +
                " new jpabook.jpashop.repository.order.query.OrderItemsQueryDTO(oi.order.id, i.name, oi.orderPrice, oi.count)" +
                " from OrderItem oi" +
                " join oi.item i" +
                " where oi.order.id in :orderIds", OrderItemsQueryDTO.class)
                .setParameter("orderIds", orderIds)
                .getResultList();

        Map<Long, List<OrderItemsQueryDTO>> orderItemMap = orderItems.stream().collect(Collectors.groupingBy(OrderItemsQueryDTO::getOrderId));
        result.forEach(o -> o.setOrderItems(orderItemMap.get(o.getOrderId())));
        return result;
    }
}
