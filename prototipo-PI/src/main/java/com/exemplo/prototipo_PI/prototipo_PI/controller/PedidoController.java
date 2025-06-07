package com.exemplo.prototipo_PI.prototipo_PI.controller;

import com.exemplo.prototipo_PI.prototipo_PI.model.DashboardStats;
import com.exemplo.prototipo_PI.prototipo_PI.model.Pedido;
import com.exemplo.prototipo_PI.prototipo_PI.Service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<Pedido> criarPedido(@RequestBody Pedido pedido) {
        try {
            Pedido novoPedido = pedidoService.salvarPedido(pedido);
            return ResponseEntity.ok(novoPedido);
        } catch (Exception e) {
            System.err.println("Erro ao criar pedido: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/registrar-pix")
    public ResponseEntity<?> registrarPagamentoPix(@RequestBody Map<String, Object> paymentData) {
        try {
            System.out.println("🔄 Registrando pagamento PIX...");
            System.out.println("📦 Dados recebidos: " + paymentData);

            // Extrair dados do pedido
            List<Map<String, Object>> items = (List<Map<String, Object>>) paymentData.get("items");
            Map<String, Object> item = items.get(0);

            // Criar pedido
            Pedido pedido = new Pedido();
            pedido.setNomeProduto((String) item.get("title"));
            pedido.setDescricao((String) item.get("title")); // Usar title como descrição também
            pedido.setQuantidade((Integer) item.get("quantity"));

            // Converter preço para Double
            Object unitPrice = item.get("unit_price");
            Double valorUnitario;
            if (unitPrice instanceof Double) {
                valorUnitario = (Double) unitPrice;
            } else if (unitPrice instanceof Integer) {
                valorUnitario = ((Integer) unitPrice).doubleValue();
            } else {
                valorUnitario = Double.parseDouble(unitPrice.toString());
            }

            pedido.setValorUnitario(valorUnitario);

            // Calcular valor total
            Double valorTotal = valorUnitario * pedido.getQuantidade();
            pedido.setValor(valorTotal);

            // Taxa de entrega padrão
            pedido.setTaxaEntrega(1.0);

            // Método de pagamento
            pedido.setMetodoPagamento("PIX");
            pedido.setStatus("PAGO"); // Consideramos como pago após o PIX

            // Salvar pedido
            Pedido pedidoSalvo = pedidoService.salvarPedido(pedido);

            Map<String, Object> response = new HashMap<>();
            response.put("id", pedidoSalvo.getId());
            response.put("status", "success");
            response.put("message", "Pagamento PIX registrado com sucesso");

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System.err.println("❌ Erro ao registrar pagamento PIX: " + e.getMessage());
            e.printStackTrace();

            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Erro ao registrar pagamento PIX");
            errorResponse.put("message", e.getMessage());

            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    @PostMapping("/registrar-balcao")
    public ResponseEntity<?> registrarPagamentoBalcao(@RequestBody Map<String, Object> paymentData) {
        try {
            System.out.println("🔄 Registrando pagamento no balcão...");
            System.out.println("📦 Dados recebidos: " + paymentData);

            // Extrair dados do pedido
            String nomeProduto = (String) paymentData.get("nomeLanche");
            Integer quantidade = (Integer) paymentData.getOrDefault("quantidade", 1);

            // Converter valores para Double
            Double valorUnitario = 0.0;
            Double valorTotal = 0.0;

            try {
                valorUnitario = Double.parseDouble(paymentData.getOrDefault("valorUnitario", "0.0").toString());
                valorTotal = Double.parseDouble(paymentData.getOrDefault("valorTotal", "0.0").toString());
            } catch (NumberFormatException e) {
                System.err.println("Erro ao converter valores: " + e.getMessage());
            }

            // Criar pedido
            Pedido pedido = new Pedido();
            pedido.setNomeProduto(nomeProduto);
            pedido.setDescricao(nomeProduto); // Usar nome como descrição
            pedido.setQuantidade(quantidade);
            pedido.setValorUnitario(valorUnitario);
            pedido.setValor(valorTotal);
            pedido.setTaxaEntrega(0.0); // Sem taxa para balcão
            pedido.setMetodoPagamento("BALCAO");
            pedido.setStatus("PAGO"); // Já consideramos como pago

            // Salvar pedido
            Pedido pedidoSalvo = pedidoService.salvarPedido(pedido);

            Map<String, Object> response = new HashMap<>();
            response.put("id", pedidoSalvo.getId());
            response.put("status", "success");
            response.put("message", "Pagamento no balcão registrado com sucesso");

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System.err.println("❌ Erro ao registrar pagamento no balcão: " + e.getMessage());
            e.printStackTrace();

            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Erro ao registrar pagamento");
            errorResponse.put("message", e.getMessage());

            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    @GetMapping
    public ResponseEntity<List<Pedido>> listarPedidos() {
        return ResponseEntity.ok(pedidoService.buscarTodosPedidos());
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Pedido>> listarPedidosPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(pedidoService.buscarPedidosPorUsuario(usuarioId));
    }

    @GetMapping("/dashboard/stats")
    public ResponseEntity<DashboardStats> obterEstatisticasDashboard() {
        try {
            DashboardStats stats = pedidoService.obterEstatisticasDashboard();
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            System.err.println("Erro ao obter estatísticas: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/periodo")
    public ResponseEntity<List<Pedido>> listarPedidosPorPeriodo(
            @RequestParam String inicio,
            @RequestParam String fim) {

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
            LocalDateTime dataInicio = LocalDateTime.parse(inicio, formatter);
            LocalDateTime dataFim = LocalDateTime.parse(fim, formatter);

            return ResponseEntity.ok(pedidoService.buscarPedidosPorPeriodo(dataInicio, dataFim));
        } catch (Exception e) {
            System.err.println("Erro ao buscar pedidos por período: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/dashboard/stats/periodo")
    public ResponseEntity<DashboardStats> obterEstatisticasPorPeriodo(
            @RequestParam String inicio,
            @RequestParam String fim) {

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
            LocalDateTime dataInicio = LocalDateTime.parse(inicio, formatter);
            LocalDateTime dataFim = LocalDateTime.parse(fim, formatter);

            DashboardStats stats = pedidoService.obterEstatisticasPorPeriodo(dataInicio, dataFim);
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            System.err.println("Erro ao obter estatísticas por período: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
