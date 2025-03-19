-- Tabela Unidade Federacao
CREATE TABLE UnidadeFederacao (
    idUF SERIAL PRIMARY KEY,
    siglaUF VARCHAR(255),
    nomeUF VARCHAR(255)
);

INSERT INTO UnidadeFederacao (siglaUF, nomeUF) VALUES
('SP', 'São Paulo'),
('RJ', 'Rio de Janeiro'),
('MG', 'Minas Gerais');

-- Tabela Cidade
CREATE TABLE Cidade (
    idCidade SERIAL PRIMARY KEY,
    nomeCidade VARCHAR(255),
    idUF INT REFERENCES UnidadeFederacao(idUF)
);

INSERT INTO Cidade (nomeCidade, idUF) VALUES
('São Paulo', 1),
('Rio de Janeiro', 2),
('Belo Horizonte', 3);

-- Tabela Bairro
CREATE TABLE Bairro (
    idBairro SERIAL PRIMARY KEY,
    nomeBairro VARCHAR(255)
);

INSERT INTO Bairro (nomeBairro) VALUES
('Centro'),
('Jardins'),
('Copacabana');

-- Tabela TipoLogradouro
CREATE TABLE TipoLogradouro (
    idTipoLogradouro SERIAL PRIMARY KEY,
    siglaLogradouro VARCHAR(255)
);

INSERT INTO TipoLogradouro (siglaLogradouro) VALUES
('Rua'),
('Avenida'),
('Travessa');

-- Tabela Logradouro
CREATE TABLE Logradouro (
    idLogradouro SERIAL PRIMARY KEY,
    nomeLogradouro VARCHAR(255),
    idTipoLogradouro INT REFERENCES TipoLogradouro(idTipoLogradouro)
);

INSERT INTO Logradouro (nomeLogradouro, idTipoLogradouro) VALUES
('Paulista', 2),
('Atlântica', 2),
('Minas Gerais', 1);

-- Tabela Endereco
CREATE TABLE Endereco (
    idEndereco SERIAL PRIMARY KEY,
    cep VARCHAR(255),
    idLogradouro INT REFERENCES Logradouro(idLogradouro),
    idBairro INT REFERENCES Bairro(idBairro),
    idCidade INT REFERENCES Cidade(idCidade)
);

INSERT INTO Endereco (cep, idLogradouro, idBairro, idCidade) VALUES
('01311-000', 1, 1, 1),
('22070-001', 2, 3, 2),
('30130-009', 3, 2, 3);

-- Tabela Fornecedor
CREATE TABLE Fornecedor (
    idFornecedor SERIAL PRIMARY KEY,
    nomeFornecedor VARCHAR(255),
    cnpj VARCHAR(255),
    saldo DOUBLE PRECISION,
    idEndereco INT REFERENCES Endereco(idEndereco),
    complementoEndereco VARCHAR(255),
    nroEndereco VARCHAR(255)
);

INSERT INTO Fornecedor (nomeFornecedor, cnpj, saldo, idEndereco, complementoEndereco, nroEndereco) VALUES
('Fornecedor A', '00.000.000/0001-01', 50000.00, 1, 'Andar 5', '123'),
('Fornecedor B', '11.111.111/0001-11', 20000.00, 2, 'Sala 10', '456'),
('Fornecedor C', '22.222.222/0001-22', 75000.00, 3, 'Loja 7', '789');

-- Tabela DDD
CREATE TABLE DDD (
    idDDD SERIAL PRIMARY KEY,
    nroDDD INT
);

INSERT INTO DDD (nroDDD) VALUES
(11), (21), (31);

-- Tabela TelefoneFornecedor
CREATE TABLE TelefoneFornecedor (
    idTelefone SERIAL PRIMARY KEY,
    nroTelefone VARCHAR(255),
    idFornecedor INT REFERENCES Fornecedor(idFornecedor),
    idDDD INT REFERENCES DDD(idDDD)
);

INSERT INTO TelefoneFornecedor (nroTelefone, idFornecedor, idDDD) VALUES
('98765-4321', 1, 1),
('99876-5432', 2, 2),
('98754-3210', 3, 3);

-- Tabela EmailFornecedor
CREATE TABLE EmailFornecedor (
    idEmailFornecedor SERIAL PRIMARY KEY,
    email VARCHAR(255),
    idFornecedor INT REFERENCES Fornecedor(idFornecedor)
);

INSERT INTO EmailFornecedor (email, idFornecedor) VALUES
('contato@fornecedora.com', 1),
('suporte@fornecedorb.com', 2),
('vendas@fornecedorc.com', 3)

-- Tabela MotivoFatura
CREATE TABLE MotivoFatura (
    idMotivoFatura SERIAL PRIMARY KEY,
    motivo VARCHAR(255)
);

INSERT INTO MotivoFatura (motivo) VALUES
('Compra de materiais'),
('Serviços prestados'),
('Taxas administrativas');

-- Tabela Fatura
CREATE TABLE Fatura (
    nroFatura SERIAL PRIMARY KEY,
    dtLancamento DATE,
    dtVencimento DATE,
    valorFatura DOUBLE PRECISION,
    idFornecedor INT REFERENCES Fornecedor(idFornecedor),
    idMotivoFatura INT REFERENCES MotivoFatura(idMotivoFatura)
);

INSERT INTO Fatura (dtLancamento, dtVencimento, valorFatura, idFornecedor, idMotivoFatura) VALUES
('2025-01-10', '2025-02-10', 12000.00, 1, 1),
('2025-01-15', '2025-02-15', 5000.00, 2, 2),
('2025-01-20', '2025-02-20', 3000.00, 3, 3);
