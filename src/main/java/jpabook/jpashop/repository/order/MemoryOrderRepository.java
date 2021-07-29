package jpabook.jpashop.repository.order;

import jpabook.jpashop.domain.Order;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//@Primary
@Repository
public class MemoryOrderRepository implements OrderRepository{

    private Map<Long, Order> store = new ConcurrentHashMap<>();
    private Long seq = 0L;

    @Override
    public void save(Order order) {
        System.out.println(" ⎡⎯------------------------------------------------⎤");
        System.out.println(" ⎜⎯-------------●●●● O R D E R ●●●●●---------------⎜");
        System.out.println(" ⎜⎯-----------------●●●●●●--------------------⎜");
        System.out.println(" ⎜⎯--------------------●●●-----------------------⎜");
        System.out.println(" ⎜⎯----------------------●-------------------------⎜");
        System.out.println("⎿⎯--------------●●●●●●●●●●●--------------⏌");
        Long orderId = seq++;
        order.setId(orderId);
        store.put(orderId, order);
    }

    @Override
    public Order findOne(Long id) {
        return store.get(id);
    }
}
