package com.example.r2dbch2.dao;

import com.example.r2dbch2.entity.StockDO;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface StockDao extends ReactiveCrudRepository<StockDO, String> {
    Mono<StockDO> findBySymbol(String symbol);
}
