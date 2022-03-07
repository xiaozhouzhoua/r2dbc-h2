package com.example.r2dbch2.dao;

import com.example.r2dbch2.entity.StockSubscriptionDO;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface StockSubscriptionDao extends ReactiveCrudRepository<StockSubscriptionDO, Long> {
    Flux<StockSubscriptionDO> findByEmail(String email);
    Flux<StockSubscriptionDO> findByEmailAndSymbol(String email, String symbol);
}
