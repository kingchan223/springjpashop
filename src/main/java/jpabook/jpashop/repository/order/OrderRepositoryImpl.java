package jpabook.jpashop.repository.order;

import jpabook.jpashop.api.Dto.OrderSimpleQueryDTO;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

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



    //String query = "select o from Order o join o.member m where o.status =:status and m.name like :name";
    public List<Order> findAllByString(OrderSearch orderSearch) {
        //language=JPAQL
        String jpql = "select o From Order o join o.member m";
        boolean isFirstCondition = true;
        //주문 상태 검색
        if (orderSearch.getOrderStatus() != null) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " o.status = :status";
        }
        //회원 이름 검색
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " m.name like :name";
        }
        TypedQuery<Order> query = em.createQuery(jpql, Order.class) .setMaxResults(1000); //최대 1000건
        if (orderSearch.getOrderStatus() != null) {
            query = query.setParameter("status", orderSearch.getOrderStatus());
        }
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            query = query.setParameter("name", orderSearch.getMemberName());
        }
        return query.getResultList();
    }

    public List<Order> findAllByCriteria(OrderSearch orderSearch) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> o = cq.from(Order.class);
        Join<Order, Member> m = o.join("member", JoinType.INNER); //회원과 조인
        List<Predicate> criteria = new ArrayList<>();
        //주문 상태 검색
        if (orderSearch.getOrderStatus() != null) {
            Predicate status = cb.equal(o.get("status"),
                    orderSearch.getOrderStatus());
            criteria.add(status);
        }
        //회원 이름 검색
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            Predicate name = cb.like(m.<String>get("name"), "%" +
                    orderSearch.getMemberName() + "%");
            criteria.add(name);
        }

        cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
        TypedQuery<Order> query = em.createQuery(cq).setMaxResults(1000); //최대 1000건
        return query.getResultList();
    }

    // 패치 조인
    @Override
    public List<Order> findAllWithMemberDelivery(int offset, int limit) {
        List<Order> resultList = em.createQuery(
                "select o from Order o "
                        + "join fetch o.member m "
                        + "join fetch o.delivery d", Order.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
        return resultList;
    }

    @Override
    public List<Order> findAllWithMemberDelivery() {
        List<Order> resultList = em.createQuery(
                "select o from Order o "
                        + "join fetch o.member m "
                        + "join fetch o.delivery d", Order.class
        ).getResultList();
        return resultList;
    }

    // api 스펙에 맞춰서 만들어진 리파지토리 메소드. 지양해야한다.
    public List<OrderSimpleQueryDTO> findOrderDTOs(){
        List<OrderSimpleQueryDTO> resultList = em.createQuery(
                "select new jpabook.jpashop.api.Dto.OrderSimpleQueryDTO(o.id, m.name, o.orderDate, o.status, d.address)"+
                        " o from Order o"
                        + " join o.member m"
                        + " join o.delivery d", OrderSimpleQueryDTO.class).getResultList();
        return resultList;
    }

    @Override
    public List<Order> findAllWithItem() {
        List<Order> resultList = em.createQuery(
                "select distinct o from Order o"
                        + " join fetch o.member m"//member는 To One이기 때문에 데이터 벙튀기 안된다.
                        + " join fetch o.delivery d"//delivery는 To One이기 때문에 데이터 벙튀기 안된다.
                        + " join fetch o.orderItems oi"
                        + " join fetch oi.item i", Order.class)
                .setFirstResult(1)
                .setMaxResults(100)
                .getResultList();
        return resultList;
    }
}
