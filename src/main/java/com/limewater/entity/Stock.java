package com.limewater.entity;

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
    private Item item;

    @ManyToOne
    private Seller seller;

    private long price;

    @CreatedDate
    @Column(name = "CREATED_DATE", updatable = false)
    private LocalDateTime createdDate;

    @PrePersist
    void createdAt() {
        createdDate = LocalDateTime.now();
    }


}
