package com.limewater.controller;

import com.limewater.dto.ItemDto;
import com.limewater.entity.Item;
import com.limewater.entity.Product;
import com.limewater.service.StockService;
import com.limewater.service.WatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by mindonal@gmail.com on 9/14/15.
 */
@RestController
@RequestMapping("/watch/*")
public class WatchController {

    @Autowired
    WatchService watchService;

    @Autowired
    StockService stockService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<ItemDto> getItemAllList() throws IOException {
        List<ItemDto> itemDtoList = StreamSupport
                .stream(watchService.getItemList().spliterator(), false)
                .map(ItemDto::new).collect(Collectors.toList());
        return itemDtoList;
    }

    @RequestMapping(value = "/{itemCode}", method = RequestMethod.GET)
    public Item watchItem(@PathVariable String itemCode) throws IOException {
        return watchService.watchItem(itemCode);
    }

    @RequestMapping(value = "/rakeTarget", method = RequestMethod.GET)
    public List<Product> getProductWithOldStockInfo() throws IOException {
        //todo : dto 만들어서 적용
        return stockService.getProductWithOldStockInfo();
    }

}
