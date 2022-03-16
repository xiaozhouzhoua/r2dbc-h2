package com.example.r2dbch2.websocket;

import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import reactor.core.publisher.Mono;

import java.net.URI;

public class ReactiveWebSocketClient {
    public static void main(String[] args) {
        WebSocketClient client = new ReactorNettyWebSocketClient();
        client.execute(
                URI.create("ws://localhost:8080/insecure/price-emitter"),
                session -> session.send(Mono.just(session.textMessage("hello world")))
                        .thenMany(session.receive()
                                .map(WebSocketMessage::getPayloadAsText)
                                .log())
                        .then()
        ).block();
    }
}
