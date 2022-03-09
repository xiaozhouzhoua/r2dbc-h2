package com.example.r2dbch2.client;

import io.netty.channel.ChannelOption;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * WebClient高阶用法
 * webClient默认使用ReactorClientHttpConnector，即Reactor-Netty
 * 这里增加自定义配置
 */
public class ApiWebClientAdvance {
    public static void main(String[] args) {
        String baseUrl = "http://localhost:8080";
        String apiPath = "/api/stock/{symbol}";

        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .responseTimeout(Duration.ofMillis(5000));

        WebClient webClient = WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .filter(((request, next) ->
                    next.exchange(ClientRequest.from(request)
                            .header("x-b3-trace-id", "some id")
                            .build()
                    )
                ))
                .baseUrl(baseUrl)
                .defaultHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .build();
    }
}
