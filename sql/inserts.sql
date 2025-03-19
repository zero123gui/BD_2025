INSERT INTO UnidadeFederacao (siglaUF, nomeUF) VALUES
('SP', 'São Paulo'),
('RJ', 'Rio de Janeiro'),
('MG', 'Minas Gerais');

INSERT INTO Cidade (nomeCidade, idUF) VALUES
('São Paulo', 1),
('Rio de Janeiro', 2),
('Belo Horizonte', 3);

INSERT INTO Bairro (nomeBairro) VALUES
('Centro'),
('Jardins'),
('Copacabana');

INSERT INTO TipoLogradouro (siglaLogradouro) VALUES
('Rua'),
('Avenida'),
('Travessa');

INSERT INTO Logradouro (nomeLogradouro, idTipoLogradouro) VALUES
('Paulista', 2),
('Atlântica', 2),
('Minas Gerais', 1);

INSERT INTO Endereco (cep, idLogradouro, idBairro, idCidade) VALUES
('01311-000', 1, 1, 1),
('22070-001', 2, 3, 2),
('30130-009', 3, 2, 3);

INSERT INTO Fornecedor (nomeFornecedor, cnpj, saldo, idEndereco, complementoEndereco, nroEndereco) VALUES
('Fornecedor A', '00.000.000/0001-01', 50000.00, 1, 'Andar 5', '123'),
('Fornecedor B', '11.111.111/0001-11', 20000.00, 2, 'Sala 10', '456'),
('Fornecedor C', '22.222.222/0001-22', 75000.00, 3, 'Loja 7', '789');

INSERT INTO DDD (nroDDD) VALUES
(11), (21), (31);

INSERT INTO TelefoneFornecedor (nroTelefone, idFornecedor, idDDD) VALUES
('98765-4321', 1, 1),
('99876-5432', 2, 2),
('98754-3210', 3, 3);

INSERT INTO EmailFornecedor (email, idFornecedor) VALUES
('contato@fornecedora.com', 1),
('suporte@fornecedorb.com', 2),
('vendas@fornecedorc.com', 3);

INSERT INTO MotivoFatura (motivo) VALUES
('Compra de materiais'),
('Serviços prestados'),
('Taxas administrativas');

INSERT INTO Fatura (dtLancamento, dtVencimento, valorFatura, idFornecedor, idMotivoFatura) VALUES
('2025-01-10', '2025-02-10', 12000.00, 1, 1),
('2025-01-15', '2025-02-15', 5000.00, 2, 2),
('2025-01-20', '2025-02-20', 3000.00, 3, 3);
