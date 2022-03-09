package com.example.r2dbch2.client;

import com.example.r2dbch2.entity.StockDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Spring5推荐使用WebClient
 */
@Slf4j
public class ApiWebClient {
    public static void main(String[] args) {
        String baseUrl = "http://localhost:8080";
        String apiPath = "/api/stock/{symbol}";

        // WebClient webClient = WebClient.create();
        // WebClient webClient = WebClient.create(baseUrl);

        WebClient webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .build();

        // webClient.method(HttpMethod.GET);
        webClient.get()
                .uri(uriBuilder -> uriBuilder.path(apiPath).build("DELL"))
                .header("x-b3-trace-id", "some id")
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode().equals(HttpStatus.OK)) {
                        return clientResponse.bodyToMono(StockDO.class);
                    } else {
                        return clientResponse.createException().flatMap(Mono::error);
                    }
                })
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(1)))
                .subscribe();

        webClient.get()
                .uri(uriBuilder -> uriBuilder.path(apiPath).build("DELL"))
                .header("x-b3-trace-id", "some id")
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, error-> Mono.error(new RuntimeException("4xx error")))
                .onStatus(HttpStatus::is5xxServerError, error-> Mono.error(new RuntimeException("5xx error")))
                .bodyToMono(StockDO.class)
                .doOnError(error -> log.error("error {}", error.getMessage()))
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(1)))
                .subscribe();

        webClient.post().uri("")
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .body(Mono.just(new StockDO()), StockDO.class)
                .retrieve()
                .bodyToMono(StockDO.class)
                .doOnError(error -> log.error("error {}", error.getMessage()))
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(1)))
                .subscribe();
    }
}
