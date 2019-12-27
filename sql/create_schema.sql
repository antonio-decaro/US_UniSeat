DROP SCHEMA IF EXISTS UniSeatDB;
CREATE SCHEMA UniSeatDB;
USE UniSeatDB;

CREATE TABLE Utente
(
    nome            VARCHAR(20)  NOT NULL,
    cognome         VARCHAR(20)  NOT NULL,
    email           VARCHAR(48)  NOT NULL,
    password        VARCHAR(256) NOT NULL,
    tipo            ENUM ('STUDENTE', 'DOCENTE', 'ADMIN'),
    codice_verifica INT          NOT NULL DEFAULT 0,

    PRIMARY KEY (email)
);

CREATE TABLE Edificio
(
    nome VARCHAR(16) PRIMARY KEY
);

CREATE TABLE Aula
(
    id               INT           NOT NULL AUTO_INCREMENT,
    nome             VARCHAR(16)   NOT NULL,
    edificio         VARCHAR(16)   NOT NULL,
    n_posti          INT           NOT NULL,
    n_posti_occupati INT           NOT NULL DEFAULT 0,
    servizi          VARCHAR(1024),
    disponibilita    VARCHAR(1024) NOT NULL,

    PRIMARY KEY (id),
    UNIQUE (nome, edificio),
    FOREIGN KEY (edificio)
        REFERENCES Edificio (nome)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Prenotazione
(
    id         INT         NOT NULL AUTO_INCREMENT,
    utente     VARCHAR(48) NOT NULL,
    aula       INT         NOT NULL,
    data       DATE        NOT NULL,
    ora_inizio TIME        NOT NULL,
    ora_fine   TIME        NOT NULL,
    tipo       ENUM ('AULA', 'POSTO'),

    PRIMARY KEY (id),
    FOREIGN KEY (utente)
        REFERENCES Utente (email)
        ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (aula)
        REFERENCES Aula (id)
        ON UPDATE CASCADE ON DELETE CASCADE
);