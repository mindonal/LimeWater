package com.limewater.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

/**
 * Created by mindonal@gmail.com on 9/14/15.
 */
@Entity
public class Image extends BaseEntity {

    @Id
    @Column(name = "IMAGE_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int imageId;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "ITEM_ID")
    private Item item;

    @Column(name = "IMAGE_URL")
    private String imageUrl;

    @Column(name = "IMAGE_DESC")
    private String imageDesc;

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageDesc() {
        return imageDesc;
    }

    public void setImageDesc(String imageDesc) {
        this.imageDesc = imageDesc;
    }


}
