package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)/*싱글테이블 전략*/
@DiscriminatorColumn(name="dtype")
@Entity
public abstract class Item {

    @Id @GeneratedValue
    @Column(name ="item_id")
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;

    @ManyToMany(mappedBy="items")
    private List<Category> categories = new ArrayList<>();


    //==비즈니스 로직==//Item의 데이터를 사용하므로 Item엔티티 안에 메소드 만든다. 응집력 좋아짐

    //증가
    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }

    //감소
    public void removeStock(int quantity){
        int restStock = this.stockQuantity - quantity;
        if(restStock < 0){
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Item() {}
}




