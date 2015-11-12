package com.limewater.service;

import com.limewater.entity.Product;
import com.limewater.entity.Stock;
import com.limewater.repository.ProductRepository;
import com.limewater.repository.StockRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by mindonal@gmail.com on 11/10/15.
 */
@Service
public class StockService {
    /**
     * 1. 이벤트 발생시 전체 아이템의 마지막 조회시간을 참조
     * 2. 일정시간 (우선 1분) 초과시 해당되는 전체 제품의 stock정보를 조회
     */

    @Autowired
    WatchService watchService;

    @Autowired
    StockRepository stockRepository;

    @Autowired
    ProductRepository productRepository;

    public List<Product> getProductWithOldStockInfo() {
        return productRepository.findAll()
                .stream()
                .filter(i -> i.getUpdatedByLocalDateTime()
                        .plusSeconds(Long.valueOf("30"))
                        .isBefore(LocalDateTime.now())
                )
                .collect(Collectors.toList());
    }

    public Map rakeProductWithOldStockInfo() {

        return null;
    }

    public void getStockOfToysrusKR(Product product) {
        Stock stock = new Stock();
        try {
            //price
            Document prdDoc = Jsoup.connect(product.getPrdUrl()).get();

            String priceRegex = "itemsCurrSalePrc\\s{0,9}=\\s{0,9}'\\d{1,3},\\d{1,3}'";
            Pattern pattern = Pattern.compile(priceRegex);
            Matcher matcher = pattern.matcher(prdDoc.toString());

            String stockPrice = "";
            if (matcher.find()) {
                stockPrice = matcher.group().replaceAll("itemsCurrSalePrc", "")
                        .replaceAll("'", "")
                        .replaceAll("=", "").trim();

                System.out.println("stockPrice = " + stockPrice);
            }

            //구매가능 수량
            Element availabilityDom = prdDoc.select(".pd0.limited-price td .point").first();

            if (availabilityDom != null) System.out.println("availabilityDom.text() = " + availabilityDom.text());

            Boolean availability;
            availability = (availabilityDom != null && Integer.valueOf(availabilityDom.text()) > 0) ? true : false;

            if (availability) {
                stock.setAvailability(availability);
                stock.setAvaiable(Integer.valueOf(availabilityDom.text()));
                stock.setTotal(Integer.valueOf(availabilityDom.nextElementSibling().text()));
            }
            stock.setProduct(product);
            stock.setCurrency(Stock.Currency.KRW);
            stock.setPrice(stockPrice);
            stockRepository.save(stock);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getRenewAllOldStock() {
        System.out.println("this is batch");
    }
}
