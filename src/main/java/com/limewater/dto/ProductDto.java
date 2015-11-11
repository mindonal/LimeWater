package com.limewater.dto;

import com.limewater.entity.Product;
import com.limewater.entity.Stock;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by mindonal@gmail.com on 11/11/15.
 */
@Getter
@Setter
public class ProductDto {
    private int prdId;
    private String prdUrl;
    private String seller;
    private String price;
    private Boolean inStock;
    private String currency;
    private int avaiable;
    private int total;


    public ProductDto(Product product) {
        this.prdId = product.getPrdId();
        this.prdUrl = product.getPrdUrl();
        this.seller = product.getSeller().toString();

        Stock stock = product.getStocks().get(0);
        this.inStock = stock.getAvailability();
        this.currency = stock.getCurrency().toString();
        this.price = stock.getPrice();
        this.avaiable = stock.getAvaiable();
        this.total = stock.getTotal();
    }
}
