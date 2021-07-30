package jpabook.jpashop.controller.form;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BookForm {

    //==상품 공통 속성==
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;

    //==책만의 특정 속성
    private String author;
    private String isbn;
}
