package jpabook.jpashop.repository.item;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Primary
@RequiredArgsConstructor
@Repository
public class ItemRepositoryImpl implements ItemRepository {

    @PersistenceContext
    private final EntityManager em;

    public void save(Item item){
        if(item.getId()==null){//아예 새로운 item
            em.persist(item);
        }else{//이미 있던 item을 update
            em.merge(item);
        }
    }

    public Item findOne(Long id){
        return em.find(Item.class, id);
    }

    public List<Item> findAll(){
        return em.createQuery("select i from Item i", Item.class).getResultList();
    }
}
