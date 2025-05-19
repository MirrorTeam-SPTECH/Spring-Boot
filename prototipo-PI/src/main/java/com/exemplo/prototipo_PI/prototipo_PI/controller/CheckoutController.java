package com.exemplo.prototipo_PI.prototipo_PI.controller;

import com.exemplo.prototipo_PI.prototipo_PI.Service.MercadoPagoService;
import com.exemplo.prototipo_PI.prototipo_PI.dto.CreatePreferenceRequest;
import com.exemplo.prototipo_PI.prototipo_PI.dto.CreatePreferenceResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

    private final MercadoPagoService mercadoPagoService;

    public CheckoutController(MercadoPagoService mercadoPagoService) {
        this.mercadoPagoService = mercadoPagoService;
    }

    @PostMapping
    public ResponseEntity<CreatePreferenceResponse> criar(@RequestBody CreatePreferenceRequest request) {
        String initPoint = mercadoPagoService.criarPreferencia(request);
        return ResponseEntity.ok(new CreatePreferenceResponse(initPoint));
    }
}
