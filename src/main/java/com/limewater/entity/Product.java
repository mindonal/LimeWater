package com.limewater.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mindonal@gmail.com on 9/16/15.
 */
@Entity
public class Product extends BaseEntity {

    @Id
    @Column(name = "PRD_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int prdId;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "ITEM_CODE")
    private Item item;

    private Seller seller;

    @Column(name = "PRD_URL")
    private String prdUrl;

    @OneToMany(mappedBy = "product")
    private List<Stock> stocks = new ArrayList<Stock>();


    public int getPrdId() {
        return prdId;
    }

    public void setPrdId(int prdId) {
        this.prdId = prdId;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public String getPrdUrl() {
        return prdUrl;
    }

    public void setPrdUrl(String prdUrl) {
        this.prdUrl = prdUrl;
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
    }

}
