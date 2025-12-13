-- Criação da Tabela de Contas
CREATE TABLE IF NOT EXISTS Contas (
    numeroConta INTEGER PRIMARY KEY, -- [cite: 18]
    dataCriacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- [cite: 19]
    saldo DECIMAL(15, 2) NOT NULL -- 
);

-- Criação da Tabela de Livro Caixa (Movimentações)
CREATE TABLE IF NOT EXISTS livroCaixa (
    idtransacao SERIAL PRIMARY KEY, -- [cite: 22]
    dataTransacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- [cite: 23]
    valorTransacao DECIMAL(15, 2) NOT NULL, -- [cite: 24]
    numeroConta INTEGER NOT NULL, -- [cite: 25]
    operacao VARCHAR(10) CHECK (operacao IN ('ENTRADA', 'SAIDA')), -- [cite: 26]
    destino VARCHAR(255), -- [cite: 27]
    origem VARCHAR(255), -- [cite: 28]
    
    -- Chave estrangeira ligando à tabela de Contas
    CONSTRAINT fk_conta
      FOREIGN KEY(numeroConta) 
      REFERENCES Contas(numeroConta)
);

-- (Opcional) Inserir dados de exemplo para testar
INSERT INTO Contas (numeroConta, saldo) VALUES (123456, 1000.00);

INSERT INTO livroCaixa (valorTransacao, numeroConta, operacao, destino, origem) 
VALUES (60.00, 123456, 'SAIDA', 'Fabio Pereira', 'Tatiana Favoretti');-- 1. Criação da Tabela de Clientes (Dados Sensíveis/PII)
-- Esta tabela centraliza as informações do dono da conta
CREATE TABLE IF NOT EXISTS Clientes (
    id_cliente SERIAL PRIMARY KEY,
    nome_completo VARCHAR(100) NOT NULL,
    cpf VARCHAR(14) UNIQUE NOT NULL,
    email VARCHAR(150),
    data_nascimento DATE,
    
    -- Dados de Endereço (Normalizados)
    endereco_logradouro VARCHAR(150),
    endereco_numero VARCHAR(20),
    endereco_complemento VARCHAR(50),
    endereco_bairro VARCHAR(50),
    endereco_cidade VARCHAR(100),
    endereco_estado CHAR(2), -- Ex: SP, RJ
    endereco_cep VARCHAR(9), -- Ex: 12345-678
    
    -- Auditoria
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    -- Vínculo com a Conta Bancária
    numeroconta INTEGER NOT NULL,
    CONSTRAINT fk_conta_cliente FOREIGN KEY (numeroconta)
        REFERENCES Contas (numeroconta)
        ON DELETE CASCADE
);

-- 2. Índices para Performance
-- Como a busca no endpoint será pelo header x-account-id, esse índice é vital.
CREATE INDEX idx_clientes_numeroconta ON Clientes(numeroconta);

-- 3. Carga de Dados Inicial (Seed)
-- Inserindo seus dados vinculados à conta 123456 que já existe no dump
INSERT INTO Clientes (
    nome_completo, 
    cpf, 
    email, 
    endereco_logradouro, 
    endereco_numero, 
    endereco_bairro, 
    endereco_cidade, 
    endereco_estado, 
    endereco_cep, 
    numeroconta
) VALUES (
    'Fabio Pereira', 
    '123.456.789-00', 
    'fabio.pereira@bank123.com', 
    'Av. Paulista', 
    '1000', 
    'Bela Vista', 
    'São Paulo', 
    'SP', 
    '01310-100', 
    123456 --
);