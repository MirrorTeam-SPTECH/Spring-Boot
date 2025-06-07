package com.exemplo.prototipo_PI.prototipo_PI.controller;

import com.exemplo.prototipo_PI.prototipo_PI.Service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/webhook")
public class WebhookController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping("/mercadopago")
    public ResponseEntity<?> receberWebhookMercadoPago(@RequestBody Map<String, Object> payload) {
        try {
            System.out.println("🔔 Webhook Mercado Pago recebido: " + payload);

            // Aqui você pode processar o webhook do Mercado Pago
            // Por exemplo, atualizar o status do pedido quando o pagamento for confirmado

            String action = (String) payload.get("action");
            String type = (String) payload.get("type");

            if ("payment".equals(type) && "payment.updated".equals(action)) {
                // Pagamento foi atualizado
                Map<String, Object> data = (Map<String, Object>) payload.get("data");
                String paymentId = (String) data.get("id");

                System.out.println("💳 Pagamento atualizado: " + paymentId);

                // Aqui você poderia buscar o pedido relacionado e atualizar seu status
                // Isso é opcional e depende de como vocês querem implementar
            }

            return ResponseEntity.ok().build();

        } catch (Exception e) {
            System.err.println("❌ Erro ao processar webhook: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
