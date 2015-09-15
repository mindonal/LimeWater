package com.limewater.service;

import com.limewater.entity.Item;
import com.limewater.repository.ItemRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * Created by mindonal@gmail.com on 9/14/15.
 */
@Service
public class WatchService {

    /**
     * 1. 해당 제품의 정보를 조회, 저장 한다(있으면 안한다.cache로 관리)
     * - 공식 홈페이지 검색
     * - 상세페이지 접근 가격, 이미지, 상품명 저장
     * 2.
     **/

    @Autowired
    ItemRepository itemRepository;

    public Item getItem(String itemCode) {
        return checkItemExist(itemCode);
    }

    private Item checkItemExist(String itemCode) {

        Item checkItem = getItemList().stream().
                 filter(i -> i.getItemCode() == Integer.parseInt(itemCode)).findAny().get();

        //Optional<Item> i = getItemList().stream().filter(x -> x.getItemCode() == Integer.parseInt(itemCode)).findAny().orElse();

        if (checkItem != null) {
            checkItem = itemRepository.findOne(Long.parseLong(itemCode));
        } else {
            parseAndInsertItem(itemCode);

        }
        return checkItem;
    }

    @Async
    private void parseAndInsertItem(String itemCode) {
        try {
            //Document listDoc = Jsoup.connect("http://search-ko.lego.com/?q="+itemCode+"&cc=KR#").get();
            Document listDoc = Jsoup.connect("http://search-en.lego.com/?q="+itemCode+"&cc=US#").get();

            System.out.println("listDoc.toString() = " + listDoc.toString());


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Cacheable
    private List<Item> getItemList() {
        return itemRepository.findAll();
    }


    public String getHtml(String itemCode) {

        System.out.println("01");

        String result = "";
        try {
            System.out.println("01");

            //Document listDoc = Jsoup.connect("http://search-ko.lego.com/?q="+itemCode+"&cc=KR#").get();
            Document listDoc = Jsoup.connect("http://search-en.lego.com/?q=" + itemCode + "&cc=US#").get();

            System.out.println("listDoc.toString() = " + listDoc.toString());
            result =  listDoc.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
