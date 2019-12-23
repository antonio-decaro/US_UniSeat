USE uniseatDB;

DELETE FROM Edificio WHERE TRUE;
DELETE FROM Aula WHERE TRUE;
DELETE FROM Utente WHERE TRUE;

INSERT INTO Edificio(nome) VALUES('F');
INSERT INTO Edificio(nome) VALUES('F2');
INSERT INTO Edificio(nome) VALUES ('F3');

INSERT INTO Aula(id,nome,edificio,n_posti,n_posti_occupati,servizi,disponibilita) VALUES(01,'E1','F',50,5,'PRESE;COMPUTER','');
INSERT INTO Aula(id,nome,edificio,n_posti,n_posti_occupati,servizi,disponibilita) VALUES(02,'E2','F',50,5,'PRESE','');
INSERT INTO Aula(id,nome,edificio,n_posti,n_posti_occupati,servizi,disponibilita) VALUES(20,'F8','F2',60,30,'PRESE','');
INSERT INTO Aula(id,nome,edificio,n_posti,n_posti_occupati,servizi,disponibilita) VALUES(21,'F7','F2',30,5,'','');
INSERT INTO Aula(id,nome,edificio,n_posti,n_posti_occupati,servizi,disponibilita) VALUES(30,'P4','F3',100,35,'PRESE','');
INSERT INTO Aula(id,nome,edificio,n_posti,n_posti_occupati,servizi,disponibilita) VALUES(32,'P6','F3',50,35,'COMPUTER','');

INSERT INTO Utente(nome, cognome,email,password,tipo) VALUES ('Lorenzo','Capozzoli','l.capozzoli@studenti.unisa.it','Lorenzo1','Studente');
INSERT INTO Utente(nome, cognome,email,password,tipo) VALUES ('Antonio','De Caro','a.decaro@studenti.unisa.it','Antonio2','Studente');
INSERT INTO Utente(nome, cognome,email,password,tipo) VALUES ('Gianluca','Spinelli','g.spinelli@studenti.unisa.it','Gianluca3','Studente');
INSERT INTO Utente(nome, cognome,email,password,tipo) VALUES ('Simone Pasquale','Rocco','sp.rocco@studenti.unisa.it','Simone4','Studente');
INSERT INTO Utente(nome, cognome,email,password,tipo) VALUES ('Marco','De Santis','m.desantis@studenti.unisa.it','Des321','Studente');

INSERT INTO Utente(nome, cognome,email,password,tipo) VALUES ('Alferdo','De Pasquale','a.depasquale@studenti.unisa.it','Admin1','Admin');

INSERT INTO Utente(nome, cognome,email,password,tipo) VALUES ('Filomena','ferrucci','f.ferrucci@studenti.unisa.it','Ferrucci1','Docente');
INSERT INTO Utente(nome, cognome,email,password,tipo) VALUES ('Carmine','Gravino','c.gravino@studenti.unisa.it','Gravino1','Docente');


INSERT INTO Prenotazione(utente,aula,data,ora_inizio,ora_fine,tipo) VALUES('l.capozzoli@studenti.unisa.it','01','2019/05/12','9:00:00','11:00:00','POSTO');
INSERT INTO Prenotazione(utente,aula,data,ora_inizio,ora_fine,tipo) VALUES('a.decaro@studenti.unisa.it','02','2019/06/20','11:00:00','12:00:00','POSTO');
INSERT INTO Prenotazione(utente,aula,data,ora_inizio,ora_fine,tipo) VALUES('f.ferrucci@studenti.unisa.it','20','2019/06/28','09:00:00','12:00:00','AULA');
INSERT INTO Prenotazione(utente,aula,data,ora_inizio,ora_fine,tipo) VALUES('c.gravino@studenti.unisa.it','32','2019/05/12','15:00:00','17:00:00','AULA');
