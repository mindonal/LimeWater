package com.limewater.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.data.annotation.CreatedDate;

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

    @Column(name = "PRD_URL")
    private String prdUrl;

    @CreatedDate
    @Column(name = "CREATED_DATE", updatable = false)
    private LocalDateTime createdDate;

    @PrePersist
    void createdAt() {
        createdDate = LocalDateTime.now();
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

    public String getPrdUrl() {
        return prdUrl;
    }

    public void setPrdUrl(String prdUrl) {
        this.prdUrl = prdUrl;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
}
