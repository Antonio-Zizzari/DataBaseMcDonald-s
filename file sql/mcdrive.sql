drop database if exists mcdrive;
create database mcdrive;
use mcdrive;

DROP TABLE IF EXISTS cliente;
CREATE TABLE cliente (
	mail varchar(50) NOT NULL,
    nome varchar(20) NOT NULL,
    cognome varchar(20) NOT NULL,
    num_scontrini int DEFAULT 0,
    PRIMARY KEY (mail)
);

DROP TABLE IF EXISTS prodotti;
CREATE TABLE prodotti (
	id int NOT NULL AUTO_INCREMENT,
    nome varchar(40) NOT NULL,
	prezzo int NOT NULL,
	PRIMARY KEY (id)
);

DROP TABLE IF EXISTS mcdrive;
CREATE TABLE mcdrive(
	nome varchar(40) NOT NULL,
	num_dipendenti int DEFAULT 0,
	PRIMARY KEY (nome)
);

DROP TABLE IF EXISTS account;
CREATE TABLE account (
	codice_account int NOT NULL AUTO_INCREMENT,
	punti int DEFAULT 0,
    mail varchar(50) NOT NULL,
    PRIMARY KEY (codice_account,mail),
    FOREIGN KEY (mail) REFERENCES cliente(mail)
);

DROP TABLE IF EXISTS dipendenti;
CREATE TABLE dipendenti(
	cf varchar(20) NOT NULL,
    nome varchar(20) NOT NULL,
    cognome varchar(20) NOT NULL,
    mc_nome varchar(40) NOT NULL,
    PRIMARY KEY (cf),
    FOREIGN KEY (mc_nome) REFERENCES mcdrive (nome)
);

DROP TABLE IF EXISTS cuochi;
CREATE TABLE cuochi (
	cf varchar(20) NOT NULL,
    anni_esperienza int DEFAULT 0,
    PRIMARY KEY (cf),
    FOREIGN KEY (cf) REFERENCES dipendenti (cf)
);

DROP TABLE IF EXISTS indirizzo;
CREATE TABLE indirizzo(
	via varchar(50) NOT NULL,
	cap int NOT NULL,
	numero int NOT NULL,
	nome varchar(40) NOT NULL,
	PRIMARY KEY (via,cap,numero,nome),
	FOREIGN KEY (nome) REFERENCES mcdrive (nome)
);

DROP TABLE IF EXISTS scontrino;
CREATE TABLE scontrino(
	data date NOT NULL,
	numero int NOT NULL,
	mail varchar(50) NOT NULL,
	mc_nome varchar(40) NOT NULL,
	PRIMARY KEY (data,numero),
	FOREIGN KEY (mail) REFERENCES cliente (mail),
	FOREIGN KEY (mc_nome) REFERENCES mcdrive (nome)
);

DROP TABLE IF EXISTS possiede;
CREATE TABLE possiede (
	data date NOT NULL,
	numero int NOT NULL,
	id int NOT NULL,
	quantita int DEFAULT 1,
	PRIMARY KEY (data,numero,id),
	FOREIGN KEY (data,numero) REFERENCES scontrino (data,numero),
	FOREIGN KEY (id) REFERENCES prodotti (id)
);

DROP TABLE IF EXISTS sportello;
CREATE TABLE sportello(
	cf varchar(20) NOT NULL,
	sesso varchar(1) NOT NULL,
	PRIMARY KEY (cf),
	FOREIGN KEY (cf) REFERENCES dipendenti (cf)
);

DROP TABLE IF EXISTS fornisce;
CREATE TABLE fornisce(
	id int NOT NULL,
	cf varchar(20) NOT NULL,
	PRIMARY KEY (id,cf),
	FOREIGN KEY (id) REFERENCES prodotti (id),
	FOREIGN KEY (cf) REFERENCES sportello (cf)
);
