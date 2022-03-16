package com.example.r2dbch2.websocket;

import com.example.r2dbch2.service.PriceQueryEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.Duration;

@Component
public class ReactiveWebSocketHandler implements WebSocketHandler {

    @Autowired
    private PriceQueryEngine priceQueryEngine;

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        return session.send(getPriceQueryFlux("APPL").map(session::textMessage))
                .and(session.receive()
                        .map(WebSocketMessage::getPayloadAsText)
                        .log()
                );
    }

    private Flux<String> getPriceQueryFlux(String symbol) {
        return Flux.interval(Duration.ofSeconds(1))
                .map(seq -> String.join(" ",
                        String.valueOf(seq), symbol,
                        priceQueryEngine.getPriceForSymbol(symbol))
                );
    }
}
