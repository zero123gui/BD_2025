-- Tabela Unidade Federacao
CREATE TABLE UnidadeFederacao (
    idUF SERIAL PRIMARY KEY,
    siglaUF VARCHAR(255),
    nomeUF VARCHAR(255)
);

-- Tabela Cidade
CREATE TABLE Cidade (
    idCidade SERIAL PRIMARY KEY,
    nomeCidade VARCHAR(255),
    idUF INT REFERENCES UnidadeFederacao(idUF)
);

-- Tabela Bairro
CREATE TABLE Bairro (
    idBairro SERIAL PRIMARY KEY,
    nomeBairro VARCHAR(255)
);

-- Tabela TipoLogradouro
CREATE TABLE TipoLogradouro (
    idTipoLogradouro SERIAL PRIMARY KEY,
    siglaLogradouro VARCHAR(255)
);

-- Tabela Logradouro
CREATE TABLE Logradouro (
    idLogradouro SERIAL PRIMARY KEY,
    nomeLogradouro VARCHAR(255),
    idTipoLogradouro INT REFERENCES TipoLogradouro(idTipoLogradouro)
);

-- Tabela Endereco
CREATE TABLE Endereco (
    idEndereco SERIAL PRIMARY KEY,
    cep VARCHAR(255),
    idLogradouro INT REFERENCES Logradouro(idLogradouro),
    idBairro INT REFERENCES Bairro(idBairro),
    idCidade INT REFERENCES Cidade(idCidade)
);

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

-- Tabela DDD
CREATE TABLE DDD (
    idDDD SERIAL PRIMARY KEY,
    nroDDD INT
);

-- Tabela TelefoneFornecedor
CREATE TABLE TelefoneFornecedor (
    idTelefoneFornecedor SERIAL PRIMARY KEY,
    nroTelefone VARCHAR(255),
    idFornecedor INT REFERENCES Fornecedor(idFornecedor),
    idDDD INT REFERENCES DDD(idDDD)
);

-- Tabela EmailFornecedor
CREATE TABLE EmailFornecedor (
    idEmailFornecedor SERIAL PRIMARY KEY,
    email VARCHAR(255),
    idFornecedor INT REFERENCES Fornecedor(idFornecedor)
);

-- Tabela MotivoFatura
CREATE TABLE MotivoFatura (
    idMotivoFatura SERIAL PRIMARY KEY,
    motivo VARCHAR(255)
);

-- Tabela Fatura
CREATE TABLE Fatura (
    nroFatura SERIAL PRIMARY KEY,
    dtLancamento DATE,
    dtVencimento DATE,
    valorFatura DOUBLE PRECISION,
    idFornecedor INT REFERENCES Fornecedor(idFornecedor),
    idMotivoFatura INT REFERENCES MotivoFatura(idMotivoFatura)
);
