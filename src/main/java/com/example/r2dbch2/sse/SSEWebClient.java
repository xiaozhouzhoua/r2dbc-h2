package com.example.r2dbch2.sse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.concurrent.CountDownLatch;

@Slf4j
public class SSEWebClient {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        WebClient webClient = WebClient.create("http://localhost:8080");

        ParameterizedTypeReference<ServerSentEvent<String>> type = new ParameterizedTypeReference<ServerSentEvent<String>>() {
        };

        Flux<ServerSentEvent<String>> serverSentEventFlux = webClient.get()
                .uri("/sse/priceEvent/APPL")
                .retrieve()
                .bodyToFlux(type);

        serverSentEventFlux.subscribe(content -> log.info("event: {}, id: {}, data: {}", content.event(), content.id(), content.data()),
                error -> log.error("error receiving sse: {}", error),
                () -> log.info("completed"));

        latch.await();
    }
}
