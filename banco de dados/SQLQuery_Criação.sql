--Criando o banco de dados loja
CREATE DATABASE loja;
GO

--Usando o banco de dados loja
USE loja;
GO

--Criando uma sequence para ID de Pessoa
CREATE SEQUENCE Sequencia_IDPessoa
START WITH 1 
INCREMENT BY 1;
GO

--Criando tabela Pessoas
CREATE TABLE Pessoas (
	IDPessoa INTEGER NOT NULL DEFAULT NEXT VALUE FOR Sequencia_IDPessoa,
	NomePessoa VARCHAR(255) NOT NULL,
	Email VARCHAR(255) NOT NULL,
	Telefone VARCHAR(11) NOT NULL,
	Logradouro VARCHAR(255) NOT NULL,
	Cidade VARCHAR(255) NOT NULL,
	Estado CHAR(2) NOT NULL,
	PRIMARY KEY(IDPessoa)
);
GO

--Criando tabela PessoasFisicas
CREATE TABLE PessoasFisicas (
	Pessoas_IDPessoa INTEGER NOT NULL,
	CPF VARCHAR(11) NOT NULL UNIQUE,
	PRIMARY KEY(Pessoas_IDPessoa),
	FOREIGN KEY(Pessoas_IDPessoa) REFERENCES Pessoas(IDPessoa)
);
GO

--Criando tabela PessoasJuridicas
CREATE TABLE PessoasJuridicas (
	Pessoas_IDPessoa INTEGER NOT NULL,
	CNPJ VARCHAR(14) NOT NULL UNIQUE,
	PRIMARY KEY(Pessoas_IDPessoa),
	FOREIGN KEY(Pessoas_IDPessoa) REFERENCES Pessoas(IDPessoa)
);
GO

--Criando tabela Usuarios
CREATE TABLE Usuarios (
	IDUsuario INTEGER NOT NULL IDENTITY,
	NomeUsuario VARCHAR(255) NOT NULL,
	SenhaUsuario VARCHAR(20) NOT NULL,
	PRIMARY KEY(IDUsuario)
);
GO

--Criando tabela Produtos
CREATE TABLE Produtos (
	IDProduto INTEGER NOT NULL IDENTITY,
	NomeProduto VARCHAR(255) NOT NULL,
	QuantidadeProduto INTEGER NOT NULL,
	PrecoVendaBase NUMERIC(6,2) NOT NULL,
	PRIMARY KEY(IDProduto)
);
GO

--Criando tabela Movimentos
CREATE TABLE Movimentos (
	IDMovimento INTEGER NOT NULL IDENTITY,
	Usuarios_IDUsuario INTEGER NOT NULL,
	Pessoas_IDPessoa INTEGER NOT NULL,
	Produtos_IDProduto INTEGER NOT NULL,
	Tipo CHAR(1) NOT NULL,
	QuantidadeMovimentado INTEGER NOT NULL,
	PrecoUnitario NUMERIC(6,2) NOT NULL,
	PRIMARY KEY (IDMovimento),
	FOREIGN KEY (Usuarios_IDUsuario) REFERENCES Usuarios(IDUsuario),
	FOREIGN KEY (Pessoas_IDPessoa) REFERENCES Pessoas(IDPessoa),
	FOREIGN KEY (Produtos_IDProduto) REFERENCES Produtos(IDProduto)
);
GO