package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class Category {

    @Id @GeneratedValue @Column(name="category_id")
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name="category_item",
               joinColumns = @JoinColumn(name="category_id"),
               inverseJoinColumns = @JoinColumn(name="item_id"))
    private List<Item> items = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name="parent_id")
    private Category parent;

    @OneToMany(mappedBy="parent")
    private List<Category> child = new ArrayList<>();

    private void setId(Long id) {
        this.id = id;
    }
    private void setName(String name) {
        this.name = name;
    }
    private void setItems(List<Item> items) {
        this.items = items;
    }
    private void setParent(Category parent) {
        this.parent = parent;
    }
    private void setChild(List<Category> child) {
        this.child = child;
    }
}
