package jpabook.jpashop.domain.item;

import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@DiscriminatorValue("B")
@Entity
public class Book extends Item{
    private String author;
    private String isbn;
    private void setAuthor(String author) {
        this.author = author;
    }
    private void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public static Book createBook(String name, int price, int stockQuantity, String author, String isbn){
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        book.setAuthor(author);
        book.setIsbn(isbn);

        return book;
    }

    public static Book createBook(Long id,String name, int price, int stockQuantity, String author, String isbn){
        Book book = Book.createBook(name, price, stockQuantity, author, isbn);
        book.setId(id);
        return book;
    }
    public void changeProperties(String name, int price, int stockQuantity, String author, String isbn){
        this.setName(name);
        this.setPrice(price);
        this.setStockQuantity(stockQuantity);
        this.setAuthor(author);
        this.setIsbn(isbn);

    }
}


