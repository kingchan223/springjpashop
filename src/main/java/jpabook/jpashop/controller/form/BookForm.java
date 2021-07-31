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

    public static BookForm createBookForm(Long id,String name, int price, int stockQuantity, String author, String isbn){
        BookForm bookForm = new BookForm();
        bookForm.setId(id);
        bookForm.setName(name);
        bookForm.setPrice(price);
        bookForm.setStockQuantity(stockQuantity);
        bookForm.setAuthor(author);
        bookForm.setIsbn(isbn);

        return bookForm;

    }
}
