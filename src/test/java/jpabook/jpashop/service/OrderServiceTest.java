package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.order.OrderRepository;
import jpabook.jpashop.service.order.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest{

    @Autowired OrderRepository orderRepository;
    @Autowired OrderService orderService;
    @Autowired EntityManager em;

    @Test
    public void 상품주문() throws Exception{
        //given
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "서대문", "11111"));
        em.persist(member);

        Item book = new Book();
        book.setName("노르웨이의 숲");
        book.setPrice(10000);
        book.setStockQuantity(10);
        em.persist(book);

        //when
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //then
        Order order = orderRepository.findOne(orderId);
        assertEquals("상품 주문시 상품 상태는 ORDER", OrderStatus.ORDER, order.getStatus());
        assertEquals("주문한 상품 종류 수가 정확해야한다.", 1, order.getOrderItems().size());
        assertEquals("주문 가격은 가격*수량이다.", 10000*2, order.getTotalPrice());
        assertEquals("주문 수량만큼 재고가 줄어야한다.", 8, book.getStockQuantity());

    }

    @Test(expected = NotEnoughStockException.class)
    public void 상품주문_재고수량초과() throws Exception{

        //given
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "서대문", "11111"));
        em.persist(member);

        Item book = new Book();
        book.setName("노르웨이의 숲");
        book.setPrice(10000);
        book.setStockQuantity(10);
        em.persist(book);

        int orderCount = 11;

        //when
        orderService.order(member.getId(), book.getId(), orderCount);

        //then
        fail("재고 수량 부족 예외가 터져야하는데, 터지지 않았습니다.");

    }

    @Test
    public void 주문_취소() throws Exception{
        //given
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "서대문", "11111"));
        em.persist(member);

        Item book = new Book();
        book.setName("노르웨이의 숲");
        book.setPrice(10000);
        book.setStockQuantity(10);
        em.persist(book);

        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //when
        orderService.cancelOrder(orderId);

        //then
        Order order = orderRepository.findOne(orderId);
        assertEquals("주문 취소시 주문 상태는 CANCEL이어야합니다.", OrderStatus.CANCEL, order.getStatus());
        assertEquals("주문이 취소된 상품은 그만큰 재고가원복되어야 합니다.", 10, book.getStockQuantity());
    }
}
