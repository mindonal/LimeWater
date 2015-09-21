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
    @JoinColumn(name = "ITEM_CODE")
    private Item item;

    @Column(name = "IMAGE_URL", nullable = false)
    private String imageUrl;

    private String desc;


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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
