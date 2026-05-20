package com.giovanna.tcc.integrador_estoque.model;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
@Entity // marca a classe como uma entidade e mapea para uma tabela no banco de dados
@Table(name = "PEDIDO") // tem que estar o mesmo nome da tabela que eu criar
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID_PEDIDO") // referencia a coluna
    private Long idPedido;

    public Long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Long idPedido) {
        this.idPedido = idPedido;
    }

    @Column(name="ID_PROD")
    private Long idProd;

    public Long getIdProd() {
        return idProd;
    }

    public void setIdProd(Long idProd) {
        this.idProd = idProd;
    }

    @Column(name ="NOME_PRODUTO", insertable= false, updatable= false)
    private String nomeProduto;

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }
    
    @Column(name="QUANTIDADE")
    private Integer quantidade;

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    @Column(name="VENDEDOR")
    private String vendedor;
    
    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    @Column(name= "CLIENTE")
    private String cliente;

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    @Column(name= "DATA_PEDIDO")
    private LocalDateTime dataPedido;
    
    public LocalDateTime getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDateTime dataPedido) {
        this.dataPedido = dataPedido;
    }


    @Column(name="VALOR_TOTAL")
    private Double valorTotal;
    
    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }


    @Lob
    @Column(name="XML_INTEGRACAO")
    private String xmlIntegracao;

    public String getXmlIntegracao() {
        return xmlIntegracao;
    }

    public void setXmlIntegracao(String xmlIntegracao) {
        this.xmlIntegracao = xmlIntegracao;
    }

    @PrePersist
    protected void onCreate(){
        this.dataPedido=LocalDateTime.now();
    }


}
