package com.exemplo.prototipo_PI.prototipo_PI.Service;

import com.exemplo.prototipo_PI.prototipo_PI.config.MercadoPagoProperties;
import com.exemplo.prototipo_PI.prototipo_PI.dto.CreatePreferenceRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class MercadoPagoService {

    private final RestTemplate restTemplate;
    private final MercadoPagoProperties properties;

    public MercadoPagoService(RestTemplate restTemplate, MercadoPagoProperties properties) {
        this.restTemplate = restTemplate;
        this.properties = properties;
    }

    public String criarPreferencia(CreatePreferenceRequest request) {
        String url = "https://api.mercadopago.com/checkout/preferences";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(properties.getAccessToken());

        Map<String, Object> body = new HashMap<>();
        body.put("items", request.getItems());

        // Aqui você pode definir as URLs de retorno
        Map<String, String> backUrls = new HashMap<>();
        backUrls.put("success", request.getSuccessUrl());
        backUrls.put("failure", request.getFailureUrl());
        body.put("back_urls", backUrls);
        body.put("auto_return", "approved");

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);
        Map<String, Object> responseBody = response.getBody();

        if (responseBody != null && responseBody.containsKey("init_point")) {
            return (String) responseBody.get("init_point");
        } else {
            throw new RuntimeException("Falha ao criar preferência: " + responseBody);
        }
    }
}