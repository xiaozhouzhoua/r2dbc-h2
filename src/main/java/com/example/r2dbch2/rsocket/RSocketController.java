package com.example.r2dbch2.rsocket;

import com.example.r2dbch2.entity.StockDO;
import com.example.r2dbch2.entity.StockPrice;
import com.example.r2dbch2.service.PriceQueryEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

/**
 * 使用RSocket时
 * 控制器的请求注解为MessageMapping表明非http rest方式
 */
@Controller
public class RSocketController {
    @Autowired
    private PriceQueryEngine priceQueryEngine;

    @MessageMapping("currentStock")
    public Mono<StockPrice> getStockPrice(StockDO stockDO) {
        System.out.println("Receive symbol from  client");
        return Mono.just(new StockPrice(stockDO.getSymbol(),
                priceQueryEngine.getPriceForSymbol(stockDO.getSymbol()))
        );
    }
}
