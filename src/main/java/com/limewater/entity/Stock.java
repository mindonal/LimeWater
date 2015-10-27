package com.limewater.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by mindonal@gmail.com on 9/14/15.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    public enum Currency {
        KRW,
        USD
    }
}
