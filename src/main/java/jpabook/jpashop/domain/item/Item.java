package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.Category;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="dtype")
@Entity
public abstract class Item {

    @Id @GeneratedValue
    @Column(name ="item_id")
    private Long id;

    private String name;

    private int price;

    private int quantity;

    @ManyToMany(mappedBy="items")
    private List<Category> categories = new ArrayList<>();

    private void setId(Long id) {
        this.id = id;
    }
    private void setName(String name) {
        this.name = name;
    }
    private void setPrice(int price) {
        this.price = price;
    }
    private void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    private void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
