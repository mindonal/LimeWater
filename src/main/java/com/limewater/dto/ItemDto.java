package com.limewater.dto;

import com.limewater.entity.Item;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by mindonal@gmail.com on 11/11/15.
 */
@Getter
@Setter
public class ItemDto {

    private String itemCode;
    private String itemName;
    private String itemTextEn;
    private String itemTextKr;
    private String itemUrl;
    private List<ImageDto> image;

    public ItemDto() {
    }

    public ItemDto(Item item) {
        this.itemCode = item.getItemCode();
        this.itemName = item.getItemName();
        this.itemTextEn = item.getItemTextEn();
        this.itemTextKr = item.getItemTextKr();
        this.itemUrl = item.getItemUrl();
        this.image = StreamSupport
                .stream(item.getImage().spliterator(), false)
                .map(ImageDto::new).collect(Collectors.toList());
        //this.products = item.getProducts();
    }

    //private List<Product> products;

}


