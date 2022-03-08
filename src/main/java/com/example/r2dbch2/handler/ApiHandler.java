package com.example.r2dbch2.handler;

import com.example.r2dbch2.entity.StockDO;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ApiHandler {
    public Mono<ServerResponse> getStocks(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        Flux.create(sink -> {
                            sink.next(new StockDO("AZSU", "华硕"));
                            sink.next(new StockDO("DELL", "戴尔"));
                            sink.complete();
                        }), StockDO.class);
    }

    public Mono<ServerResponse> getStock(ServerRequest request) {
        String symbol = request.pathVariable("symbol");
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(new StockDO(symbol, "其它")), StockDO.class);
    }
}
