package jpabook.jpashop.domain.item;

import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@DiscriminatorValue("M")
@Getter
@Entity
public class Movie extends Item{

    private String director;
    private String actor;

    private void setDirector(String director) {
        this.director = director;
    }
    private void setActor(String actor) {
        this.actor = actor;
    }
}


