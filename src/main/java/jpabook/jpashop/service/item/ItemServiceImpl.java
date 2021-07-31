package jpabook.jpashop.service.item;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.item.ItemRepository;
import jpabook.jpashop.service.item.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item){
        itemRepository.save(item);
    }

    @Transactional
    public Item updateItem(Long itemId, String name, int price, int stockQuantity, String author, String isbn){
        Book findItem = (Book)itemRepository.findOne(itemId);/*item을 영속상태로 만든다.*/
        findItem.changeProperties(name, price, stockQuantity, author, isbn);
        /*영속상태에 있는 아이템은 dirty checking되어 수정된 값에 대해 DB에 반영된다.*/

        return findItem;
    }

    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }
}
