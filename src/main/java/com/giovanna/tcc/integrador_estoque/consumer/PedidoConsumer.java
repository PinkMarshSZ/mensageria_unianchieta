package com.giovanna.tcc.integrador_estoque.consumer;

import java.util.Map;

import org.springframework.amqp.rabbit.annotation.RabbitListener; // Certifique de ter a dependência no pom.xml
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.giovanna.tcc.integrador_estoque.config.RabbitMQConfig;
import com.giovanna.tcc.integrador_estoque.model.Pedido;
import com.giovanna.tcc.integrador_estoque.repository.PedidoRepository;

@Component
@Transactional
public class PedidoConsumer {
    
    private final PedidoRepository pedidoRepository;
    private final XmlMapper xmlMapper; // Injetado para transformar Map em XML

    public PedidoConsumer(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.xmlMapper = new XmlMapper();
    }

    @RabbitListener(queues = RabbitMQConfig.FILA_PEDIDOS)
    public void consumir(Map<String, Object> mensagem) {
        try {
            Pedido pedido = new Pedido();

            // Tenta buscar tanto em maiúsculo quanto em minúsculo se necessário
            String vendedorStr = String.valueOf(mensagem.getOrDefault("VENDEDOR", mensagem.getOrDefault("vendedor", "Vendedor Desconhecido")));
            String clienteStr = String.valueOf(mensagem.getOrDefault("CLIENTE", mensagem.getOrDefault("cliente", "Cliente Desconhecido")));

            pedido.setVendedor(vendedorStr);
            pedido.setCliente(clienteStr);

            // Mapeamento dos campos
            pedido.setIdProd(Long.valueOf(String.valueOf(mensagem.getOrDefault("ID_PROD", 0))));
            pedido.setQuantidade(Integer.valueOf(String.valueOf(mensagem.getOrDefault("QUANTIDADE", 0))));
            pedido.setValorTotal(Double.valueOf(String.valueOf(mensagem.getOrDefault("VALOR_TOTAL", 0.0))));

            // Gerando o XML completo com todos os dados que vieram na mensagem
            try {
                String xmlCompleto = xmlMapper.writeValueAsString(mensagem);
                pedido.setXmlIntegracao(xmlCompleto);
            } catch (Exception e) {
                pedido.setXmlIntegracao("<status>ERRO_AO_GERAR_XML</status>");
            }

            // O campo DATA_PEDIDO será preenchido automaticamente pelo @PrePersist

            // Salva no banco Oracle (xe)
            pedidoRepository.saveAndFlush(pedido);

            System.out.println(">>> [SUCESSO] Pedido salvo! XML Gerado: " + pedido.getXmlIntegracao());
            System.out.println(">>> TOTAL NO BANCO: " + pedidoRepository.count());

        } catch (Exception e) {
            System.err.println("XXX [ERRO] Falha ao processar: " + e.getMessage());
        }
    }
}