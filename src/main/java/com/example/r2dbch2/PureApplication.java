package com.example.r2dbch2;

import com.example.r2dbch2.config.RouterConfig;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.RouterFunctions;
import reactor.netty.http.server.HttpServer;

import java.util.concurrent.CountDownLatch;

/**
 * 使用Netty的方式启动
 */
public class PureApplication {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(
                RouterFunctions.toHttpHandler(RouterConfig.apiRouter()));

        HttpServer.create().host("localhost").port(8081).handle(adapter).bind().block();
        latch.await();
    }
}
