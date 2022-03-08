package com.example.r2dbch2.reactive;

import com.example.r2dbch2.config.RouterConfig;
import com.example.r2dbch2.controller.ApiController;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

public class WebTestClientBindTest {
    @Test
    public void bindToApiController() {
        WebTestClient client = WebTestClient.bindToController(new ApiController()).build();
        client.get()
                .uri("/api/prices")
                .exchange()
                .expectBody()
                .consumeWith(System.out::println);
    }

    @Test
    public void bindToRouterFunction() {
        WebTestClient client = WebTestClient.bindToRouterFunction(RouterConfig.apiRouter()).build();
        client.get()
                .uri("/api/stocks")
                .exchange()
                .expectBody()
                .consumeWith(System.out::println);
    }

    /**
     * 绑定到本地正在运行的应用
     */
    @Test
    public void bindToServer() {
        WebTestClient client = WebTestClient.bindToServer().baseUrl("http://localhost:8080").build();
        client.get()
                .uri("/api/prices")
                .exchange()
                .expectBody()
                .consumeWith(System.out::println);
    }

    @Test
    public void bindToExternalServer() {
        WebTestClient client = WebTestClient.bindToServer().baseUrl("https://quotes.rest").build();
        client.get()
                .uri(uriBuilder -> uriBuilder.path("/qod").queryParam("language", "en").build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectBody()
                .consumeWith(System.out::println);
    }
}
