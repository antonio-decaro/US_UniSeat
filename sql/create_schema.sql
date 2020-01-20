DROP SCHEMA IF EXISTS UniSeatDB;
CREATE SCHEMA UniSeatDB;
USE UniSeatDB;

CREATE TABLE IF NOT EXISTS Utente
(
    nome            VARCHAR(20)  NOT NULL,
    cognome         VARCHAR(20)  NOT NULL,
    email           VARCHAR(48)  NOT NULL,
    password        VARCHAR(256) NOT NULL,
    tipo            ENUM ('STUDENTE', 'DOCENTE', 'ADMIN'),
    codice_verifica INT          NOT NULL DEFAULT 0,

    PRIMARY KEY (email)
);

CREATE TABLE IF NOT EXISTS Edificio
(
    nome VARCHAR(16) PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS Aula
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

CREATE TABLE IF NOT EXISTS Prenotazione
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

DROP EVENT IF EXISTS clean_non_confirmed_users;

CREATE EVENT IF NOT EXISTS clean_non_confirmed_users
    ON SCHEDULE
        EVERY 1 day
    DO
        DELETE
        FROM Utente
        WHERE codice_verifica != 0;

DELETE FROM Aula WHERE TRUE;
DELETE FROM Edificio WHERE TRUE;
DELETE FROM Utente WHERE TRUE;
DELETE FROM Prenotazione WHERE TRUE;

INSERT INTO Edificio(nome) VALUES('F');
INSERT INTO Edificio(nome) VALUES('F2');
INSERT INTO Edificio(nome) VALUES ('F3');

INSERT INTO Aula (id, nome, edificio, n_posti, n_posti_occupati, servizi, disponibilita) VALUES (1, 'P1', 'F3', 200, 0, 'PRESE;COMPUTER', '{"intervalli":[[],[],[],[],[],[],[]]}');
INSERT INTO Aula (id, nome, edificio, n_posti, n_posti_occupati, servizi, disponibilita) VALUES (2, 'P4', 'F3', 120, 0, 'PRESE', '{"intervalli":[[],[],[],[],[],[],[["02:00", "03:00"]]]}');
INSERT INTO Aula (id, nome, edificio, n_posti, n_posti_occupati, servizi, disponibilita) VALUES (3, 'P3', 'F3', 110, 0, 'PRESE;', '{"intervalli":[[["08:00", "22:00"]],[["08:00", "22:00"]],[["08:00", "22:00"]],[["08:00", "22:00"]],[["08:00", "22:00"]],[["08:00", "22:00"]],[["08:00", "22:00"]]]}');
INSERT INTO Aula (id, nome, edificio, n_posti, n_posti_occupati, servizi, disponibilita) VALUES (20, 'F8', 'F2', 100, 0, 'PRESE;', '{"intervalli":[[["08:00", "20:00"]],[["08:00", "20:00"]],[["08:00", "20:00"]],[["08:00", "20:00"]],[["08:00", "20:00"]],[],[]]}');
INSERT INTO Aula (id, nome, edificio, n_posti, n_posti_occupati, servizi, disponibilita) VALUES (21, 'F7', 'F2', 70, 0, '', '{"intervalli":[[["08:00", "20:00"]],[["08:00", "20:00"]],[["08:00", "20:00"]],[["08:00", "20:00"]],[["08:00", "20:00"]],[],[]]}');
INSERT INTO Aula (id, nome, edificio, n_posti, n_posti_occupati, servizi, disponibilita) VALUES (30, 'P14', 'F3', 35, 0, 'PRESE', '{"intervalli":[[["08:00", "20:00"]],[["08:00", "20:00"]],[["08:00", "20:00"]],[["08:00", "20:00"]],[["08:00", "20:00"]],[],[]]}');
INSERT INTO Aula (id, nome, edificio, n_posti, n_posti_occupati, servizi, disponibilita) VALUES (32, 'P6', 'F3', 50, 0, 'COMPUTER', '{"intervalli":[[["08:00", "20:00"]],[["08:00", "20:00"]],[["08:00", "20:00"]],[["08:00", "20:00"]],[["08:00", "20:00"]],[],[]]}');
INSERT INTO Aula (id, nome, edificio, n_posti, n_posti_occupati, servizi, disponibilita) VALUES (1112, 'ISIS Lab', 'F', 40, 0, 'PRESE;', '{"intervalli": [	[],	[],	[],	[],	[],	[],	[]]}');

INSERT INTO Utente(nome, cognome,email,password,tipo) VALUES ('Lorenzo','Capozzoli','l.capozzoli@studenti.unisa.it',SHA2('Lorenzo1', 256),'STUDENTE');
INSERT INTO Utente(nome, cognome,email,password,tipo) VALUES ('Antonio','De Caro','a.decaro@studenti.unisa.it',SHA2('Antonio2', 256),'STUDENTE');
INSERT INTO Utente(nome, cognome,email,password,tipo) VALUES ('Gianluca','Spinelli','g.spinelli@studenti.unisa.it',SHA2('Gianluca3', 256),'STUDENTE');
INSERT INTO Utente(nome, cognome,email,password,tipo) VALUES ('Simone Pasquale','Rocco','sp.rocco@studenti.unisa.it',SHA2('Simonerocco4', 256),'STUDENTE');
INSERT INTO Utente(nome, cognome,email,password,tipo) VALUES ('Marco','De Santis','m.desantis@studenti.unisa.it',SHA2('desan321', 256),'STUDENTE');

INSERT INTO Utente(nome, cognome,email,password,tipo) VALUES ('Alferdo','De Pasquale','a.depasquale@unisa.it',SHA2('Depasquale1', 256),'ADMIN');

INSERT INTO Utente(nome, cognome,email,password,tipo) VALUES ('Filomena','ferrucci','f.ferrucci@unisa.it',SHA2('Ferrucci1', 256),'DOCENTE');
INSERT INTO Utente(nome, cognome,email,password,tipo) VALUES ('Carmine','Gravino','c.gravino@unisa.it',SHA2('Gravino1', 256),'DOCENTE');
