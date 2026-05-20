package com.giovanna.tcc.integrador_estoque.controller;
// Vamos enviar os pedidos para o banco por aqui!!!!
import java.util.Map;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.giovanna.tcc.integrador_estoque.config.RabbitMQConfig;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final RabbitTemplate rabbitTemplate;

    public PedidoController(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }


    // Nivel de estresse, envia N número de pedidos para a fila 
    @PostMapping("/enviar")
        public ResponseEntity<String> enviarPedidoReal(@RequestBody Map<String, Object> dadosPedido) {
        try {
            // Validamos se o ID_PROD existe antes de mandar para a fila
            if (!dadosPedido.containsKey("ID_PROD")) {
                return ResponseEntity.badRequest().body("Erro: Campo ID_PROD é obrigatório.");
            }

            // Enviamos o mapa exatamente como recebemos do terminal
            rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_PEDIDOS, 
                "pedidos.rota", 
                dadosPedido
            );

            return ResponseEntity.ok("Pedido enviado com sucesso para processamento!");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao enviar: " + e.getMessage());
        }
    
    }
}
