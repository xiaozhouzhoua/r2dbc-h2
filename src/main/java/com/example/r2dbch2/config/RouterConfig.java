package com.example.r2dbch2.config;

import com.example.r2dbch2.handler.ApiHandler;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

public class RouterConfig {
    public static RouterFunction<ServerResponse> apiRouter() {
        ApiHandler handler = new ApiHandler();
        return route(GET("/api/stocks"), handler::getStocks)
                .and(route(GET("/api/stock/{symbol}"), handler::getStock));
    }
}
