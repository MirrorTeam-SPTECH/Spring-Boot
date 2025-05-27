package com.exemplo.prototipo_PI.prototipo_PI.Service;


import com.exemplo.prototipo_PI.prototipo_PI.config.MercadoPagoProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class PagamentoService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MercadoPagoProperties mercadoPagoProperties;

    public Map<String, Object> criarPagamentoPix(Double valor, String descricao, String emailCliente) {
        String url = "https://api.mercadopago.com/v1/payments";

        Map<String, Object> body = new HashMap<>();
        body.put("transaction_amount", valor);
        body.put("description", descricao);
        body.put("payment_method_id", "pix");

        Map<String, String> payer = new HashMap<>();
        payer.put("email", emailCliente);
        body.put("payer", payer);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(mercadoPagoProperties.getAccessToken());

        // ✅ Adiciona o header de idempotência exigido pela API
        headers.set("X-Idempotency-Key", UUID.randomUUID().toString());

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, request, Map.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new RuntimeException("Erro ao criar pagamento Pix: " + response.getStatusCode() + " - " + response.getBody());
        }
    }

}
