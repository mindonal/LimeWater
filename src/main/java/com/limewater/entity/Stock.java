package com.limewater.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by mindonal@gmail.com on 9/14/15.
 */
@Entity
public class Stock {

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

    private long price;

    private Boolean inStock;

    private int avaiable;

    private int total;

    @CreatedDate
    @Column(name = "CREATED_DATE", updatable = false)
    private LocalDateTime createdDate;

    @PrePersist
    void createdAt() {
        createdDate = LocalDateTime.now();
    }

    public enum Currency {
        krw,
        usd
    }

    @Override
    public String toString() {
        return "Stock{" +
                "stockId=" + stockId +
                ", product=" + product +
                ", currency=" + currency +
                ", price=" + price +
                ", inStock=" + inStock +
                ", avaiable=" + avaiable +
                ", total=" + total +
                ", createdDate=" + createdDate +
                '}';
    }

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

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
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

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
}
