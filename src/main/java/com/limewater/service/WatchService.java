package com.limewater.service;

import com.limewater.entity.Image;
import com.limewater.entity.Item;
import com.limewater.repository.ImageRepository;
import com.limewater.repository.ItemRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
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
     * 2. 해당제품의 stock status 를 조회한다
     * - toysrus.lottemart.com
     * a. 먼저 사이트에서 검색 URL
     * b. detail page를 알아낸다 쇼핑몰 고유의 PRD CD
     * c. detail page 정보 저장
     * <p>
     * 3. 저장된 DB의 리스트를 조회
     **/

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    RakeService rakeService;

    @Cacheable
    public List<Item> getItemList() {
        return itemRepository.findAll();
    }

    public Item watchItem(int itemCode) {

        Item watchItem = new Item();
        // 먼저 있는지 check
        if (getItemList().size() > 0) {
            watchItem = getItemList().stream().
                    filter(i -> i.getItemCode() == itemCode).findAny().get();
        }

        System.out.println("watchItem = " + watchItem);
        
        // 없으면 저장, 조사
        if (watchItem != null && watchItem.getItemCode() == 0) {
            watchItem = rakeService.getItemInfo(itemCode);
        }
        return watchItem;
    }

    /**************************************************/


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
            Document listDoc = Jsoup.connect("http://search-en.lego.com/?q=" + itemCode + "&cc=US#").get();

            System.out.println("listDoc.toString() = " + listDoc.toString());


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Item getHtml(String itemCode) {
        String result = "";
        Item parsedItem = new Item();
        try {

            //Document listDoc = Jsoup.connect("http://search-ko.lego.com/?q="+itemCode+"&cc=KR#").get();
            Document listDoc = Jsoup.connect("http://search-en.lego.com/?q=" + itemCode + "&cc=US#").get();

            //item-code
            Elements searchedItemCode = listDoc.select("#product-results .item-code ");

            System.out.println("searchedItemCode.html() = " + searchedItemCode.html());

            parsedItem.setItemCode(Integer.parseInt(searchedItemCode.html().toString()));

            //item-name-en
            Elements searchedItemName = listDoc.select("#product-results h4 > a ");
            System.out.println("searchedItemName = " + searchedItemName);
            //item-detail-url
            parsedItem.setItemName(searchedItemName.attr("title").toString());
            parsedItem.setItemUrl(searchedItemName.attr("href").toString());

            itemRepository.save(parsedItem);

            //item-image-url
            Image itemMainImage = new Image();
            itemMainImage.setImageUrl("http://cache.lego.com/e/dynamic/is/image/LEGO/" + itemCode + "?$main$");
            itemMainImage.setItem(parsedItem);
            imageRepository.saveAndFlush(itemMainImage);

            List<Image> images = new ArrayList<Image>();
            images.add(itemMainImage);
            parsedItem.setImages(images);
            itemRepository.saveAndFlush(parsedItem);


            result = searchedItemCode.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return parsedItem;
    }


}
