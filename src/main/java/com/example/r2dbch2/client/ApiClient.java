package com.example.r2dbch2.client;

import com.example.r2dbch2.entity.StockDO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * RestTemplate是一个同步的rest请求
 */
public class ApiClient {
    public static void main(String[] args) {
        String baseUrl = "http://localhost:8080";
        String apiPath = "/api/stock/{symbol}";
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl);
        Map<String, Object> pathVarMap = new HashMap<>();
        pathVarMap.put("symbol", "DELL");
        URI uri = builder.path(apiPath)
                .uriVariables(pathVarMap)
                .build()
                .encode()
                .toUri();
        ResponseEntity<StockDO> result = restTemplate.getForEntity(uri, StockDO.class);
        System.out.println(result.getBody());
    }
}
