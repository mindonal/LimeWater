package com.limewater.controller;

import com.limewater.entity.Item;
import com.limewater.service.WatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * Created by mindonal@gmail.com on 9/14/15.
 */
@RestController
@RequestMapping("/watch/*")
public class WatchController {

    @Autowired
    WatchService watchService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Item> getItemAllList() throws IOException
    {
        //todo : dto 만들어서 적용
        return watchService.getItemList();
    }

    @RequestMapping(value = "/{itemCode}", method = RequestMethod.GET)
    public Item watchItem(@PathVariable String itemCode) throws IOException
    {
        return watchService.watchItem(itemCode);
    }

}
