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
        try {
            Map<String, Object> resposta = pagamentoService.criarPagamentoPix(
                    request.getValor(),
                    request.getDescricao(),
                    request.getEmail() // ✔️ agora o DTO deve ter o e-mail do cliente
            );

            Map<String, Object> pointOfInteraction = (Map<String, Object>) resposta.get("point_of_interaction");
            Map<String, Object> transactionData = (Map<String, Object>) pointOfInteraction.get("transaction_data");

            return ResponseEntity.ok(Map.of(
                    "qr_code_base64", transactionData.get("qr_code_base64"),
                    "qr_code", transactionData.get("qr_code"),
                    "ticket_url", resposta.get("transaction_details") != null
                            ? ((Map) resposta.get("transaction_details")).get("external_resource_url")
                            : null
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao gerar QR Code Pix: " + e.getMessage());
        }
    }
}
