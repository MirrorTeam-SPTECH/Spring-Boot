package com.exemplo.prototipo_PI.prototipo_PI.Service;


import com.exemplo.prototipo_PI.prototipo_PI.config.MercadoPagoProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

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
        payer.put("email", "TESTUSER1430126435@testuser.com");
        body.put("payer", payer);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(mercadoPagoProperties.getAccessToken());

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, request, Map.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            Map<String, Object> responseBody = response.getBody();

            if (responseBody != null) {
                return responseBody;
            } else {
                throw new RuntimeException("Resposta vazia do Mercado Pago");
            }
        } else {
            throw new RuntimeException("Erro ao criar pagamento Pix: " + response.getStatusCode());
        }
    }
}