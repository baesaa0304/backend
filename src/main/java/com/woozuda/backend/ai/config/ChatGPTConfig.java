package com.woozuda.backend.ai.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;

/**
 * ChatGPT API 통합을 위한 설정 클래스입니다.
 * RestTemplate 및 HTTP 헤더 관련 설정
 */
@Configuration
@Slf4j
public class ChatGPTConfig {

    @Value("${openai.api.key}")
    private String apiKey;

    /**
     * RestTemplate 빈 설정.
     * ChatGPT API와 같은 외부 서비스에 HTTP 요청을 보낼 때 사용됩니다.
     *
     * @return RestTemplate 인스턴스
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * ChatGPT API 요청에 사용되는 HTTP 헤더를 설정
     *
     * @return HttpHeaders 인스턴스
     */
    @Bean
    public HttpHeaders httpHeaders() {
        HttpHeaders headers = new HttpHeaders();


        // API 키를 x-api-key 헤더에 추가
        headers.set("Authorization", "Bearer " + apiKey);

        // 요청 본문을 JSON 형식으로 설정
        headers.setContentType(MediaType.APPLICATION_JSON);

        return headers;
    }
}