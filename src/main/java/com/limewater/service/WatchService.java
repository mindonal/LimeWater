package com.limewater.service;

import com.limewater.entity.Item;
import com.limewater.repository.ImageRepository;
import com.limewater.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

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

    public Item watchItem(String itemCode) {


        Item watchItem = itemRepository.findOneByItemCode(itemCode);
        /** 먼저 있는지 check
         if (getItemList().size() > 0) {
         watchItem = getItemList().stream()
         .filter(i -> i.getItemCode() == itemCode)
         .filter(Objects::nonNull)
         .findAny()
         .get();
         }*/

        // 없으면 저장, 조사
        if (watchItem == null || watchItem.getItemCode().isEmpty()) {
            watchItem = rakeService.getItemInfo(itemCode);
        }

        return watchItem;
    }


}
