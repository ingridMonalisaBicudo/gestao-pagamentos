CREATE TABLE TB_PAGAMENTOS (
    id UUID PRIMARY KEY,
    status VARCHAR(50) NOT NULL,
    data_inclusao TIMESTAMP,
    data_pagamento TIMESTAMP NOT NULL,
    valor DECIMAL(19,2) NOT NULL,
    descricao VARCHAR(255) NOT NULL,
    recorrencia_id UUID,
    destino_id UUID NOT NULL,
    CONSTRAINT fk_recorrencia FOREIGN KEY (recorrencia_id) REFERENCES RecorrenciaModel(id),
    CONSTRAINT fk_destino FOREIGN KEY (destino_id) REFERENCES DestinoModel(id)
);

CREATE TABLE TB_DESTINO (
    id UUID PRIMARY KEY,
    chave_pix VARCHAR(255) NOT NULL,
    tipo_chave_pix VARCHAR(50)
);

CREATE TABLE TB_RECORRENCIA (
    id UUID PRIMARY KEY,
    data_final TIMESTAMP NOT NULL,
    frequencia VARCHAR(50) NOT NULL
);

