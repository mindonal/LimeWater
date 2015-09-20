package com.limewater.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by mindonal@gmail.com on 9/16/15.
 */
@Entity
public class Product {

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

    @CreatedDate
    @Column(name = "CREATED_DATE", updatable = false)
    private LocalDateTime createdDate;
    @LastModifiedBy
    @Column(name = "LAST_MODIFIED_DATE", updatable = false)
    private LocalDateTime lastModifiedDate;

    @PrePersist
    void createdAt() {
        createdDate = LocalDateTime.now();
    }

    @PreUpdate
    void updatedAt() {
        lastModifiedDate = LocalDateTime.now();
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

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

    @Override
    public String toString() {
        return "Product{" +
                "prdId=" + prdId +
                ", item=" + item +
                ", prdUrl='" + prdUrl + '\'' +
                ", createdDate=" + createdDate +
                '}';
    }

}
