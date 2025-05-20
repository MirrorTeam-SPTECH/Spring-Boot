package com.exemplo.prototipo_PI.prototipo_PI.controller;

import com.exemplo.prototipo_PI.prototipo_PI.Service.PagamentoService;
import com.exemplo.prototipo_PI.prototipo_PI.dto.PagamentoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/pagamento")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @PostMapping("/qrcode")
    public ResponseEntity<?> gerarQrCodePix(@RequestBody PagamentoRequest request) {
        Map<String, Object> resposta = pagamentoService.criarPagamentoPix(request.getValor(), request.getDescricao(), "test_user_123456@testuser.com");

        // Extraindo os dados do QR code
        Map<String, Object> pointOfInteraction = (Map<String, Object>) resposta.get("point_of_interaction");
        if (pointOfInteraction == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ponto de interação não encontrado");
        }

        Map<String, Object> transactionData = (Map<String, Object>) pointOfInteraction.get("transaction_data");
        if (transactionData == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Dados da transação não encontrados");
        }

        // Envia os dados do QR Code e URL
        return ResponseEntity.ok(Map.of(
                "qr_code_base64", transactionData.get("qr_code_base64"),
                "qr_code", transactionData.get("qr_code"),
                "ticket_url", resposta.get("transaction_details") != null ? ((Map) resposta.get("transaction_details")).get("external_resource_url") : null
        ));
    }
}