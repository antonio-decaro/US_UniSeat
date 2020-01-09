USE uniseatDB;

DELETE FROM Aula WHERE TRUE;
DELETE FROM Edificio WHERE TRUE;
DELETE FROM Utente WHERE TRUE;
DELETE FROM Prenotazione WHERE TRUE;

INSERT INTO Edificio(nome) VALUES('F');
INSERT INTO Edificio(nome) VALUES('F2');
INSERT INTO Edificio(nome) VALUES ('F3');

INSERT INTO Aula(id,nome,edificio,n_posti,n_posti_occupati,servizi,disponibilita) VALUES(01,'E1','F',50,5,'PRESE;COMPUTER','');
INSERT INTO Aula(id,nome,edificio,n_posti,n_posti_occupati,servizi,disponibilita) VALUES(02,'E2','F',50,5,'PRESE','');
INSERT INTO Aula(id,nome,edificio,n_posti,n_posti_occupati,servizi,disponibilita) VALUES(20,'F8','F2',60,30,'PRESE','');
INSERT INTO Aula(id,nome,edificio,n_posti,n_posti_occupati,servizi,disponibilita) VALUES(21,'F7','F2',30,5,'','');
INSERT INTO Aula(id,nome,edificio,n_posti,n_posti_occupati,servizi,disponibilita) VALUES(30,'P4','F3',100,35,'PRESE','');
INSERT INTO Aula(id,nome,edificio,n_posti,n_posti_occupati,servizi,disponibilita) VALUES(32,'P6','F3',50,35,'COMPUTER','');

INSERT INTO Utente(nome, cognome,email,password,tipo) VALUES ('Lorenzo','Capozzoli','l.capozzoli@studenti.unisa.it',SHA2('Lorenzo1', 256),'STUDENTE');
INSERT INTO Utente(nome, cognome,email,password,tipo) VALUES ('Antonio','De Caro','a.decaro@studenti.unisa.it',SHA2('Antonio2', 256),'STUDENTE');
INSERT INTO Utente(nome, cognome,email,password,tipo) VALUES ('Gianluca','Spinelli','g.spinelli@studenti.unisa.it',SHA2('Gianluca3', 256),'STUDENTE');
INSERT INTO Utente(nome, cognome,email,password,tipo) VALUES ('Simone Pasquale','Rocco','sp.rocco@studenti.unisa.it',SHA2('Simone4', 256),'STUDENTE');
INSERT INTO Utente(nome, cognome,email,password,tipo) VALUES ('Marco','De Santis','m.desantis@studenti.unisa.it',SHA2('desan321', 256),'STUDENTE');

INSERT INTO Utente(nome, cognome,email,password,tipo) VALUES ('Alferdo','De Pasquale','a.depasquale@unisa.it',SHA2('Depasquale1', 256),'ADMIN');

INSERT INTO Utente(nome, cognome,email,password,tipo) VALUES ('Filomena','ferrucci','f.ferrucci@unisa.it',SHA2('Ferrucci1', 256),'DOCENTE');
INSERT INTO Utente(nome, cognome,email,password,tipo) VALUES ('Carmine','Gravino','c.gravino@unisa.it',SHA2('Gravino1', 256),'DOCENTE');


INSERT INTO Prenotazione(utente,aula,data,ora_inizio,ora_fine,tipo) VALUES('l.capozzoli@studenti.unisa.it','01','2019/05/12','9:00:00','11:00:00','POSTO');
INSERT INTO Prenotazione(utente,aula,data,ora_inizio,ora_fine,tipo) VALUES('a.decaro@studenti.unisa.it','02','2019/06/20','11:00:00','12:00:00','POSTO');
INSERT INTO Prenotazione(utente,aula,data,ora_inizio,ora_fine,tipo) VALUES('f.ferrucci@unisa.it','20','2019/06/28','09:00:00','12:00:00','AULA');
INSERT INTO Prenotazione(utente,aula,data,ora_inizio,ora_fine,tipo) VALUES('c.gravino@unisa.it','32','2019/05/12','15:00:00','17:00:00','AULA');
