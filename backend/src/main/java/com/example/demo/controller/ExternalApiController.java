package com.example.demo.controller;

import com.example.demo.dto.CustomerResponse;
import com.example.demo.dto.PageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ExternalApiController {

    private final RestTemplate restTemplate;

    public ExternalApiController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/external-demo")
    public ResponseEntity<Map<String, Object>> externalDemo(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {

        String url = "https://jsonplaceholder.typicode.com/posts?_page=" + page + "&_limit=" + size;
        Object[] posts = restTemplate.getForObject(url, Object[].class);

        Map<String, Object> result = new HashMap<>();
        result.put("source", "third-party-api");
        result.put("requestPage", page);
        result.put("requestSize", size);
        result.put("data", posts);

        return ResponseEntity.ok(result);
    }
}
