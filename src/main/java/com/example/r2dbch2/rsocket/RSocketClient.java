package com.example.r2dbch2.rsocket;

import com.example.r2dbch2.entity.StockDO;
import com.example.r2dbch2.entity.StockPrice;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.util.MimeTypeUtils;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.net.URI;
import java.time.Duration;

/**
 * RSocket
 * 1、响应式应用层协议
 * 2、OSI Layer 5/6
 * 3、可以在WebSocket或者TCP之上运行
 * 4、完全支持响应式流Reactive Streams的规范
 */
public class RSocketClient {
    public static void main(String[] args) {
        RSocketStrategies strategies = RSocketStrategies.builder()
                .encoders(encoders -> encoders.add(new Jackson2JsonEncoder()))
                .decoders(decoders -> decoders.add(new Jackson2JsonDecoder()))
                .build();

        RSocketRequester.Builder builder = RSocketRequester.builder();
        RSocketRequester requester = builder.rsocketConnector(connector ->
                        connector.reconnect(Retry.fixedDelay(2, Duration.ofSeconds(5)))
                .dataMimeType(MimeTypeUtils.APPLICATION_JSON_VALUE))
                .rsocketStrategies(strategies)
                .websocket(URI.create("ws://localhost:8080/rsocket"));
                //.tcp("localhost", 8081);

        Mono<StockPrice> result =  requester.route("currentStock")
                .data(new StockDO("APPL", "APPLE INC"))
                .retrieveMono(StockPrice.class);

        System.out.println(result.block());
    }
}
