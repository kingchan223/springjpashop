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
}


