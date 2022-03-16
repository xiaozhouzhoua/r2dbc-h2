package com.example.r2dbch2.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class WebSocketConfig {
    @Autowired
    private WebSocketHandler webSocketHandler;

    @Bean
    public HandlerMapping webSocketHandlerMapping() {
        Map<String, WebSocketHandler> socketHandlerMap = new HashMap<>();
        // 路由路径
        socketHandlerMap.put("/insecure/price-emitter", webSocketHandler);
        SimpleUrlHandlerMapping handlerMapping = new SimpleUrlHandlerMapping();
        handlerMapping.setUrlMap(socketHandlerMap);
        handlerMapping.setOrder(1);
        return handlerMapping;
    }
}
