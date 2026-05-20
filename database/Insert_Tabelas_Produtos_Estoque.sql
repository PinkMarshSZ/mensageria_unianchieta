-- Primeiro, garante que está no container correto do Java
ALTER SESSION SET CONTAINER = xepdb1;

DECLARE
    TYPE t_lista IS TABLE OF VARCHAR2(100);
    v_tipos   t_lista := t_lista('Smartphone', 'Notebook', 'Monitor', 'Mouse', 'Teclado', 'Headset', 'Placa de Video', 'Memoria RAM', 'SSD', 'Gabinete', 'Fonte', 'Cabo HDMI', 'Webcam');
    v_marcas  t_lista := t_lista('Samsung', 'Dell', 'LG', 'Logitech', 'Razer', 'HyperX', 'ASUS', 'Corsair', 'Kingston', 'Gigabyte', 'Apple', 'Xiaomi', 'Intel');
    v_modelos t_lista := t_lista('Pro', 'Ultra', 'Gamer', 'Office', 'Max', 'Plus', 'Air', 'Evo', 'Nitro', 'Legend');
    
    v_cidades t_lista := t_lista('Sao Paulo', 'Rio de Janeiro', 'Curitiba', 'Belo Horizonte', 'Porto Alegre', 'Salvador', 'Fortaleza', 'Recife', 'Manaus', 'Brasilia');
    v_ufs     t_lista := t_lista('SP', 'RJ', 'PR', 'MG', 'RS', 'BA', 'CE', 'PE', 'AM', 'DF');
    
    v_nome_unico VARCHAR2(300);
    v_id_gerado  INT;
    v_idx_cid    INT;
BEGIN
    -- Limpa o buffer de saída
    DBMS_OUTPUT.ENABLE(BUFFER_SIZE => NULL);

    FOR i IN 1..130 LOOP
        v_nome_unico := v_tipos(TRUNC(DBMS_RANDOM.VALUE(1, v_tipos.COUNT + 1))) || ' ' || 
                        v_marcas(TRUNC(DBMS_RANDOM.VALUE(1, v_marcas.COUNT + 1))) || ' ' || 
                        v_modelos(TRUNC(DBMS_RANDOM.VALUE(1, v_modelos.COUNT + 1))) || ' (N- ' || i || ')';

        v_idx_cid := TRUNC(DBMS_RANDOM.VALUE(1, v_cidades.COUNT + 1));

        -- 1. Inserção na tabela PRODUTO (Sem a coluna OPERANDO)
        INSERT INTO PRODUTO (PRODUTO, LOCAL_ESTOQUE, UF_CIDADE, CIDADE_ESTOQUE)
        VALUES (
            v_nome_unico,
            'Corredor ' || CHR(64 + TRUNC(DBMS_RANDOM.VALUE(1, 10))) || '-' || TRUNC(DBMS_RANDOM.VALUE(1, 50)),
            v_ufs(v_idx_cid),
            v_cidades(v_idx_cid)
        )
        RETURNING ID_PROD INTO v_id_gerado; -- Ajustado aqui para capturar o ID_PROD

        -- 2. Inserção na tabela ESTOQUE
        INSERT INTO ESTOQUE (ID_PROD, QTD_ATUAL, DATA_ULTIMA_VENDA)
        VALUES (
            v_id_gerado,
            ROUND(DBMS_RANDOM.VALUE(0, 2500)), 
            SYSTIMESTAMP - DBMS_RANDOM.VALUE(1, 365) 
        );
    END LOOP;

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Sucesso! 130 registros inseridos no xepdb1.');

EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('ERRO AO INSERIR: ' || SQLERRM);
END;
/