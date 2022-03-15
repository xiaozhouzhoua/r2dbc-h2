package com.example.r2dbch2.sse;

import com.example.r2dbch2.service.PriceQueryEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * spring5新增对服务器推送技术的支持
 * 即Server Sent Event
 * 1、客户端发起连接
 * 2、服务端响应，指定Content-Type为text/event-stream
 * 3、客户端处理服务端发送的实时事件
 */
@RestController
@RequestMapping("/sse")
public class SSEController {
    @Autowired
    private PriceQueryEngine priceQueryEngine;

    @GetMapping(path = "/price/{symbol}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamFlux(@PathVariable("symbol") String symbol) {
        return Flux.interval(Duration.ofSeconds(1))
                .map(seq -> priceQueryEngine.getPriceForSymbol(symbol));
    }

    @GetMapping(path = "/priceEvent/{symbol}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<String>> streamEventFlux(@PathVariable("symbol") String symbol) {
        return Flux.interval(Duration.ofSeconds(1))
                .map(seq -> ServerSentEvent.<String>builder()
                    .data(priceQueryEngine.getPriceForSymbol(symbol))
                    .id(String.valueOf(seq))
                    .event("price-update")
                    .build());
    }
}
