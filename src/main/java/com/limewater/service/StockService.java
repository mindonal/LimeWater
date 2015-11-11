package com.limewater.service;

import com.limewater.entity.Product;
import com.limewater.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
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
}
