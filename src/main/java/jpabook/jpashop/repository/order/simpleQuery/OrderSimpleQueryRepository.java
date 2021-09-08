package jpabook.jpashop.repository.order.simpleQuery;

import jpabook.jpashop.api.Dto.OrderSimpleQueryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class OrderSimpleQueryRepository {// 이런건 따로 뺀다.

    private final EntityManager em;

    public List<OrderSimpleQueryDTO> findOrderDTOs(){
        List<OrderSimpleQueryDTO> resultList = em.createQuery(
                "select new jpabook.jpashop.api.Dto.OrderSimpleQueryDTO(o.id, m.name, o.orderDate, o.status, d.address)"+
                        " o from Order o"
                        + " join o.member m"
                        + " join o.delivery d", OrderSimpleQueryDTO.class).getResultList();
        return resultList;
    }
}
