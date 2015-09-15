package com.limewater.controller;

import com.limewater.entity.Item;
import com.limewater.service.WatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Created by mindonal@gmail.com on 9/14/15.
 */
@RestController
@RequestMapping("/watch/*")
public class WatchController {

    @Autowired
    WatchService watchService;

    @RequestMapping(value = "/watch/{itemCode}", method = RequestMethod.GET)
    public Item watch(@PathVariable String itemCode) throws IOException
    {
        return watchService.getItem(itemCode);
    }

    @RequestMapping(value = "/domcheck/{itemCode}", method = RequestMethod.GET)
    public String domcheck(@PathVariable String itemCode) throws IOException
    {
        return watchService.getHtml(itemCode);
    }

}
