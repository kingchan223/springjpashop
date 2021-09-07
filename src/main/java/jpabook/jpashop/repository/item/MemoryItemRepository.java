package jpabook.jpashop.repository.item;

import jpabook.jpashop.domain.item.Item;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//@Primary
@Repository
public class MemoryItemRepository implements ItemRepository{

    private Map<Long, Item> store = new ConcurrentHashMap<>();
    private Long seq = 0L;

    @Override
    public void save(Item item) {
        Long itemId = seq++;
//        item.setId(itemId);
        store.put(itemId, item);
    }

    @Override
    public Item findOne(Long id) {
        return store.get(id);
    }

    @Override
    public List<Item> findAll() {
        return new ArrayList<>(store.values());
    }
}
