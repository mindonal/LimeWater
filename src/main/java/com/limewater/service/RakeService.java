package com.limewater.service;

import com.limewater.entity.*;
import com.limewater.repository.ImageRepository;
import com.limewater.repository.ItemRepository;
import com.limewater.repository.ProductRepository;
import com.limewater.repository.StockRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mindonal@gmail.com on 9/17/15.
 */
@Service
public class RakeService {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    StockRepository stockRepository;

    @Async
    private void getProductInfo(Item item) {
        rakeToysrusKR(item);
    }

    @Async
    public void rakeToysrusKR(Item item) {
        //Toysrus korea
        //http://toysrus.lottemart.com/search/search.do?searchTerm=lego+31035
        //http://toysrus.lottemart.com/product/ProductDetail.do?ProductCD=5702015366922

        try {
            String itemCode = String.valueOf(item.getItemCode());
            Document listDoc = Jsoup.connect("http://toysrus.lottemart.com/search/search.do?searchTerm=lego+" + itemCode).get();
            //item-code
            Elements searchedProductCode = listDoc.select(".goods-area .img-area a[href] ");

            Pattern pattern = Pattern.compile("\'([0-9])\\w+");
            Matcher matcher = pattern.matcher(searchedProductCode.attr("href"));

            System.out.println("searchedProductCode.attr(\"href\") = " + searchedProductCode.attr("href"));


            //lego 71016
            String prdCode = "5702015366922";

            if (matcher.find()) {
                prdCode = matcher.group().replaceAll("'", "");
                System.out.println("m.group() = " + prdCode);
            }
            Product product = new Product();

            product.setPrdUrl("http://toysrus.lottemart.com/product/ProductDetail.do?ProductCD=" + prdCode);
            System.out.println("\n>>>product.getPrdUrl() = " + product.getPrdUrl());
            Document prdDoc = Jsoup.connect(product.getPrdUrl()).get();

            System.out.println("prdDoc.toString() = " + prdDoc.toString());

            product.setItem(item);
            product.setSeller(Seller.TOYSRUS_KR);

            Stock stock = new Stock();
            stock.setProduct(product);
            stock.setCurrency(Stock.Currency.krw);

            System.out.println("\n\n>>>>>stock = " + prdDoc.select("#ItemCurrSalePrc ").html());

            stock.setPrice(prdDoc.select("#ItemCurrSalePrc ").html());

            productRepository.saveAndFlush(product);
            stockRepository.saveAndFlush(stock);


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Async
    public void rakeOfficialKR(Item item) {

    }

    @Async
    public void rakeAmazonUS(Item item) {

    }

    public Item getItemInfo(int itemCode) {
        Item parsedItem = new Item();
        try {
            System.out.println("itemCode >>>> " + itemCode);
            Document listDoc = Jsoup.connect("http://search-en.lego.com/?q=" + itemCode + "&cc=US#").get();

            //item-code
            Elements searchedItemCode = listDoc.select("#product-results .item-code ");

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

        } catch (IOException e) {
            e.printStackTrace();
        }

        //해당 item stock정보 조사
        getProductInfo(parsedItem);

        System.out.println("parsedItem.toString() = " + parsedItem.toString());

        return parsedItem;
    }
}
