package com.limewater.service;

import com.limewater.entity.*;
import com.limewater.repository.ImageRepository;
import com.limewater.repository.ItemRepository;
import com.limewater.repository.ProductRepository;
import com.limewater.repository.StockRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
        rakeAmazonUS(item);
    }

    @Async
    public void rakeToysrusKR(Item item) {
        /** Toysrus korea
         * http://toysrus.lottemart.com/search/search.do?searchTerm=lego+31035
         * http://toysrus.lottemart.com/product/ProductDetail.do?ProductCD=5702015366922
         */
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

            //System.out.println("prdDoc.toString() = " + prdDoc.toString());

            String priceRegex = "itemsCurrSalePrc\\s{0,9}=\\s{0,9}'\\d{1,3},\\d{1,3}'";
            pattern = pattern.compile(priceRegex);
            matcher = pattern.matcher(prdDoc.toString());

            String stockPrice = "1,000";
            if (matcher.find()) {
                stockPrice = matcher.group().replaceAll("itemsCurrSalePrc", "")
                        .replaceAll("'", "")
                        .replaceAll("=", "").trim();

                System.out.println("stockPrice = " + stockPrice);
            }

            product.setItem(item);
            product.setSeller(Seller.TOYSRUS_KR);

            Stock stock = new Stock();
            stock.setProduct(product);
            stock.setCurrency(Stock.Currency.KRW);
            stock.setPrice(stockPrice);

            productRepository.save(product);
            stockRepository.save(stock);


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Async
    public void rakeOfficialKR(Item item) {

    }

    @Async
    public void rakeAmazonUS(Item item) {

        /** Amazon US
         * http://www.amazon.com/s/ref=nb_sb_noss?url=search-alias%3Dtoys-and-games&field-keywords=lego+10240
         * http://www.amazon.com/LEGO-10240-X-Wing-Starfighter-Building/dp/B00COM5ZNE
         */
        try {
            String itemCode = String.valueOf(item.getItemCode());
            Document listDoc = Jsoup.connect("http://www.amazon.com/s/ref=nb_sb_noss?url=search-alias%3Dtoys-and-games&field-keywords=" + itemCode).get();

            Elements itemUrls = listDoc.select("li.s-result-item.celwidget a.a-link-normal.a-text-normal");
            Element firstUrl = itemUrls.first();
            Elements searchedProductCode = firstUrl.select("a[href] ");

            String prdUrl = searchedProductCode.attr("href");

            System.out.println("##################################### = MM");
            System.out.println("itemUrl = " + prdUrl);
            System.out.println("##################################### = NN");


            Pattern pattern = Pattern.compile("/([A-Z])\\w+");
            Matcher matcher = pattern.matcher(prdUrl);

            String prdCode;
            matcher.find();
            if (matcher.find()) {
                prdCode = matcher.group(0).replace("/", "");
                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>m.group() = " + prdCode);
            }

            Product product = new Product();

            Random rand = new Random();
            Document prdDoc;

            product.setPrdUrl(prdUrl);
            try {
                System.out.println("try 1");
                prdDoc = Jsoup.connect(prdUrl).timeout(rand.nextInt((15000 - 10000) + 1) + 10000).get();
            } catch (Exception e) {
                System.out.println("try 2");
                prdDoc = Jsoup.connect(prdUrl).timeout(rand.nextInt((15000 - 10000) + 1) + 10000).get();
            } finally {

            }
            Elements priceDoc = prdDoc.select("#priceblock_ourprice");
            System.out.println("priceDoc.toString() = " + priceDoc.toString());
            System.out.println("priceDoc.select(\"span\").text() = " + priceDoc.select("span").text());


            String stockPrice = priceDoc.select("span").text().replace("$", "");


            product.setItem(item);
            product.setSeller(Seller.AMAZON_US);

            Stock stock = new Stock();
            stock.setProduct(product);
            stock.setCurrency(Stock.Currency.USD);
            stock.setPrice(stockPrice);

            productRepository.save(product);
            stockRepository.save(stock);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Item getItemInfo(String itemCode) {
        Item parsedItem = new Item();
        try {
            System.out.println("itemCode >>>> " + itemCode);
            Document listDoc = Jsoup.connect("http://search-en.lego.com/?q=" + itemCode + "&cc=US#").get();

            //item-code
            Elements searchedItemCode = listDoc.select("#product-results .item-code ");

            parsedItem.setItemCode(searchedItemCode.html().toString());
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

            System.out.println("itemMainImage.toString() = " + itemMainImage.toString());

            imageRepository.save(itemMainImage);

            List<Image> imagelist = new ArrayList<Image>();
            imagelist.add(itemMainImage);
            parsedItem.setImage(imagelist);
            itemRepository.save(parsedItem);

        } catch (IOException e) {
            e.printStackTrace();
        }

        //해당 item stock정보 조사
        getProductInfo(parsedItem);

        System.out.println("parsedItem.toString() = " + parsedItem.toString());

        return parsedItem;
    }
}
