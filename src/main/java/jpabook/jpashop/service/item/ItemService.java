package jpabook.jpashop.service.item;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;

import java.util.List;

public interface ItemService {
   void saveItem(Item item);

    List<Item> findItems();

    Item findOne(Long itemId);

    Item updateItem(Long itemId, String name, int price, int stockQuantity, String author, String isbn);
}
