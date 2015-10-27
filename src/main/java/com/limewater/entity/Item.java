package com.limewater.entity;

import javax.persistence.*;
import java.util.List;

/**
 * Created by mindonal@gmail.com on 9/14/15.
 */
@Entity
public class Item extends BaseEntity {

    @Id
    @Column(name = "ITEM_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int itemId;

    @Column(name = "ITEM_CODE", unique = true)
    private int itemCode;

    @Column(name = "ITEM_NAME")
    private String itemName;

    @Column(name = "ITEM_URL")
    private String itemUrl;

    @OneToMany(mappedBy = "item")
    private List<Image> image;

    @OneToMany(mappedBy = "item")
    private List<Product> products;

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getItemCode() {
        return itemCode;
    }

    public void setItemCode(int itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemUrl() {
        return itemUrl;
    }

    public void setItemUrl(String itemUrl) {
        this.itemUrl = itemUrl;
    }

    public List<Image> getImage() {
        return image;
    }

    public void setImage(List<Image> image) {
        this.image = image;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
