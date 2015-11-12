package com.limewater.common;

import com.limewater.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Created by mindonal@gmail.com on 11/12/15.
 */
@Slf4j
@Configuration
@EnableScheduling
public class Scheduler {

    @Autowired
    StockService stockService;

    @Scheduled(cron = " */15 * * * * *")
    public void renewStock() {
        stockService.getRenewAllOldStock();
    }
}