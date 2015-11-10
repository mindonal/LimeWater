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
    private Integer itemId;

    @Column(name = "ITEM_CODE", unique = true)
    private String itemCode;

    private String itemName;

    private String itemTextEn = "";

    private String itemTextKr = "";

    @Column(name = "ITEM_URL")
    private String itemUrl;

    @OneToMany(mappedBy = "item")
    private List<Image> image;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<Product> products;

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
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

    public String getItemTextEn() {
        return itemTextEn;
    }

    public void setItemTextEn(String itemTextEn) {
        this.itemTextEn = itemTextEn;
    }

    public String getItemTextKr() {
        return itemTextKr;
    }

    public void setItemTextKr(String itemTextKr) {
        this.itemTextKr = itemTextKr;
    }
}
