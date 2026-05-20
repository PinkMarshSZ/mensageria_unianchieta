CREATE OR REPLACE NONEDITIONABLE PROCEDURE REGISTRAR_VENDA (
    p_id_produto IN NUMBER,
    p_quantidade IN NUMBER,
    p_valor_unitario IN NUMBER DEFAULT 0,
    p_cliente IN VARCHAR2 DEFAULT 'N/A',
    p_vendedor IN VARCHAR2 DEFAULT 'SISTEMA',
    p_xml_dados IN CLOB DEFAULT NULL
) AS
    v_estoque_atual NUMBER;
BEGIN
    -- 1. Verifica e bloqueia a linha para atualização (Prevenção de concorrência)
    BEGIN
        SELECT QTD_ATUAL INTO v_estoque_atual 
        FROM ESTOQUE 
        WHERE ID_PROD = p_id_produto
        FOR UPDATE WAIT 5; -- Espera até 5 segundos se a linha estiver travada
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            DBMS_OUTPUT.PUT_LINE('Erro: Produto ' || p_id_produto || ' não encontrado no estoque.');
            RETURN;
    END;

    -- 2. Validação de Quantidade
    IF v_estoque_atual >= p_quantidade THEN

        -- 3. REGISTRA O PEDIDO (Agora com os campos de Cliente e XML que o seu Java envia)
        INSERT INTO PEDIDO (
            ID_PROD, 
            QUANTIDADE, 
            VALOR_TOTAL, 
            CLIENTE, 
            VENDEDOR, 
            DATA_PEDIDO, 
            XML_INTEGRACAO
        ) VALUES (
            p_id_produto, 
            p_quantidade, 
            (p_quantidade * p_valor_unitario), 
            p_cliente, 
            p_vendedor, 
            SYSTIMESTAMP, 
            p_xml_dados
        );

        -- 4. ATUALIZA O ESTOQUE (Baixa a quantidade e atualiza a data)
        UPDATE ESTOQUE 
        SET QTD_ATUAL = QTD_ATUAL - p_quantidade,
            DATA_ULTIMA_VENDA = SYSTIMESTAMP
        WHERE ID_PROD = p_id_produto;

        COMMIT;
        DBMS_OUTPUT.PUT_LINE('Sucesso: Pedido ' || p_id_produto || ' registrado e estoque baixado!');
    ELSE
        DBMS_OUTPUT.PUT_LINE('Erro: Estoque insuficiente para o produto ' || p_id_produto);
    END IF;

EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Erro Crítico ao processar venda: ' || SQLERRM);
END;
/