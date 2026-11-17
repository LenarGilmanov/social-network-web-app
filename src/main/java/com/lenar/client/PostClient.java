package com.lenar.client;

import com.lenar.entity.dto.PostDto;
import com.lenar.entity.dto.PostsContainerDto;
import com.lenar.entity.dto.request.CreatePostRequest;
import com.lenar.entity.dto.request.UpdatePostRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PostClient {

    private final RestTemplate restTemplate;

    @Value("${post.client.base-url}")
    private String baseUrl;

    @Autowired
    public PostClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public PostsContainerDto findAll() {
        return restTemplate.getForObject(baseUrl, PostsContainerDto.class);
    }

    public PostDto findById(int id) {
        return restTemplate.getForObject(baseUrl + "/" + id, PostDto.class);
    }

    public PostDto save(CreatePostRequest request) {
        return restTemplate.postForObject(baseUrl, request, PostDto.class);
    }

    public PostDto updateById(int id, UpdatePostRequest request) {
        // [1] Простой вариант, который ничего не возвращает
        // restTemplate.put(baseUrl + "/" + id, request);

        // [2] Более сложный, но гибкий вариант
        return restTemplate.exchange(
                baseUrl + "/" + id,
                HttpMethod.PUT,
                new HttpEntity<>(request),
                PostDto.class
        ).getBody();
    }

    public void deleteById(int id) {
        restTemplate.delete(baseUrl + "/" + id);
    }
}
