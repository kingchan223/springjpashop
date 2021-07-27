package jpabook.jpashop.domain.item;

import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("A")
@Getter
public class Album extends Item{
    private String artist;
    private String etc;

    private void setArtist(String artist) {
        this.artist = artist;
    }
    private void setEtc(String etc) {
        this.etc = etc;
    }
}


