package jpabook.jpashop;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.item.ItemRepository;
import jpabook.jpashop.repository.member.MemberRepository;
import jpabook.jpashop.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@RequiredArgsConstructor
//@Component
public class InitDB {

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final EntityManager em;

    @PostConstruct
    public void init(){
        Member m1 = memberRepository.findOne(1L);
        Item item1 = itemRepository.findOne(3L);
        Item item2 = itemRepository.findOne(4L);
        OrderItem orderItem1 = OrderItem.createOrderItem(item1, 2);
        OrderItem orderItem2 = OrderItem.createOrderItem(item2, 3);

        Delivery delivery = new Delivery();
        delivery.setAddress(m1.getAddress());
        Order order = Order.createOrder(m1, delivery, orderItem1, orderItem2);

        em.persist(order);

        Member m2 = memberRepository.findOne(2L);
        Item item3 = itemRepository.findOne(14L);
        Item item4 = itemRepository.findOne(15L);
        OrderItem orderItem3 = OrderItem.createOrderItem(item3, 4);
        OrderItem orderItem4 = OrderItem.createOrderItem(item4, 5);

        Delivery delivery2 = new Delivery();
        delivery2.setAddress(m2.getAddress());
        Order order2 = Order.createOrder(m2, delivery2, orderItem3, orderItem4);

        em.persist(order2);

    }

//    @Component
//    @Transactional
//    @RequiredArgsConstructor
//    static class InitService{
//
//        public void dbInit1(){
//            Member member = new Member();
//
//
//            Member member2 = new Member();
//            member.setName("userB");
//            member.setAddress(new Address("서울", "은평구", "321233"));
//            em.persist(member2);
//
//            Book book = new Book();
//            book.setName("JPA1 BOOK");
//            book.setPrice(10000);
//            book.setStockQuantity(100);
//            em.persist(book);
//
//            Book book2 = new Book();
//            book.setName("React BOOK");
//            book.setPrice(10000);
//            book.setStockQuantity(100);
//            em.persist(book2);
//
//            OrderItem orderItem1 = OrderItem.createOrderItem(book, 1);
//            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 1);
//
//            Delivery delivery = new Delivery();
//            delivery.setAddress(member.getAddress());
//            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
//            em.persist(order);
//        }
//    }
}


