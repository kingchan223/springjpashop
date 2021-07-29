package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Movie;
import jpabook.jpashop.service.item.ItemService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ItemServiceTest {

//    @Autowired ItemRepository itemRepository;
    @Autowired
ItemService itemService;

    @Test
    public void 상품추가_조회() throws Exception{
        //given
        Movie movie = new Movie();
        movie.setName("멀홀랜드드라이브");

        //when
        itemService.saveItem(movie);

        //then
        Assert.assertEquals(movie, itemService.findOne(movie.getId()));
    }
}
