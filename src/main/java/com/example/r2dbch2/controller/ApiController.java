package com.example.r2dbch2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {
    @GetMapping("/prices")
    public Map<String, String> getPrices() {
        Map<String, String> result = new HashMap<>();
        result.put("ASUZ", "100");
        result.put("DELL", "108");
        result.put("APPL", "198");
        return result;
    }
}
