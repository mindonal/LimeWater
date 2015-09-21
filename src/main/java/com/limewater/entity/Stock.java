package com.limewater.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

/**
 * Created by mindonal@gmail.com on 9/14/15.
 */
@Entity
public class Stock extends BaseEntity {

    @Id
    @Column(name = "STOCK_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int stockId;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "PRD_ID")
    private Product product;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    private String price;

    private Boolean inStock;

    private int avaiable;

    private int total;


    public int getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Boolean getInStock() {
        return inStock;
    }

    public void setInStock(Boolean inStock) {
        this.inStock = inStock;
    }

    public int getAvaiable() {
        return avaiable;
    }

    public void setAvaiable(int avaiable) {
        this.avaiable = avaiable;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public enum Currency {
        krw,
        usd
    }
}
