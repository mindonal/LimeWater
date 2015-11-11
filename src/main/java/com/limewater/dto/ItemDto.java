package com.limewater.dto;

import com.limewater.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by mindonal@gmail.com on 11/11/15.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {

    private String itemCode;
    private String itemName;
    private String itemTextEn;
    private String itemTextKr;
    private String itemUrl;
    private String itemBadge;
    private List<String> images;
    private List<ProductDto> product;

    public ItemDto(Item item) {
        this.itemCode = item.getItemCode();
        this.itemName = item.getItemName();
        this.itemTextEn = item.getItemTextEn();
        this.itemTextKr = item.getItemTextKr();
        this.itemUrl = item.getItemUrl();
        this.itemBadge = item.getItemBadge();

        this.images = item.getImage()
                .stream()
                .map(i -> i.getImageUrl())
                .collect(Collectors.toList());

        this.product = StreamSupport
                .stream(item.getProducts().spliterator(), false)
                .map(ProductDto::new).collect(Collectors.toList());
    }

    //

}


