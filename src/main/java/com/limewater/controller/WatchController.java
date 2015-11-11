package com.limewater.controller;

import com.limewater.dto.ItemDto;
import com.limewater.dto.ProductDto;
import com.limewater.service.StockService;
import com.limewater.service.WatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;
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
    public ItemDto watchItem(@PathVariable String itemCode) throws IOException {
        return watchService.watchItem(itemCode).convertItemDto();
    }

    @RequestMapping(value = "/rakeTarget", method = RequestMethod.GET)
    public Map rakeProductWithOldStockInfo() throws IOException {
        return stockService.rakeProductWithOldStockInfo();
    }

    @RequestMapping(value = "/rakeTarget/list", method = RequestMethod.GET)
    public List<ProductDto> getProductWithOldStockInfo() throws IOException {
        return StreamSupport
                .stream(stockService.getProductWithOldStockInfo().spliterator(), false)
                .map(ProductDto::new).collect(Collectors.toList());
    }

}
