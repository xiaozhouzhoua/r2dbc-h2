package com.example.r2dbch2.client;

import com.example.r2dbch2.entity.StockDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * AsyncRestTemplate可以指定线程池
 * 但是已经不推荐使用了
 */
@Slf4j
public class ApiAsyncClient {
    public static void main(String[] args) {
        String baseUrl = "http://localhost:8080";
        String apiPath = "/api/stock/{symbol}";
        // 自定义线程池
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setMaxPoolSize(10);
        taskExecutor.setThreadNamePrefix("ApiAsyncClient-");
        taskExecutor.setQueueCapacity(100);
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 异步执行rest请求
        AsyncRestTemplate asyncRestTemplate = new AsyncRestTemplate(taskExecutor);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl);
        Map<String, Object> pathVarMap = new HashMap<>();
        pathVarMap.put("symbol", "DELL");
        URI uri = builder.path(apiPath)
                .uriVariables(pathVarMap)
                .build()
                .encode()
                .toUri();

        ListenableFuture<ResponseEntity<StockDO>> result = asyncRestTemplate.getForEntity(uri, StockDO.class);
        result.addCallback(new ListenableFutureCallback<ResponseEntity<StockDO>>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error(ex.getMessage());
            }
            @Override
            public void onSuccess(ResponseEntity<StockDO> result) {
                log.info(result.getBody().toString());
            }
        });
    }
}
