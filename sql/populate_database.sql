USE uniseatDB;

DELETE FROM Aula WHERE TRUE;
DELETE FROM Edificio WHERE TRUE;
DELETE FROM Utente WHERE TRUE;
DELETE FROM Prenotazione WHERE TRUE;

INSERT INTO Edificio(nome) VALUES('F');
INSERT INTO Edificio(nome) VALUES('F2');
INSERT INTO Edificio(nome) VALUES ('F3');

INSERT INTO Aula(id,nome,edificio,n_posti,n_posti_occupati,servizi,disponibilita) VALUES(01,'P1','F3',200,0,'PRESE;COMPUTER','{"intervalli":[[["08:00", "20:00"]],[["08:00", "20:00"]],[["08:00", "20:00"]],[["08:00", "20:00"]],[["08:00", "20:00"]],[],[]]}');
INSERT INTO Aula(id,nome,edificio,n_posti,n_posti_occupati,servizi,disponibilita) VALUES(02,'P4','F3',120,0,'PRESE','{"intervalli":[[],[],[],[],[],[],[]]}');
INSERT INTO Aula(id,nome,edificio,n_posti,n_posti_occupati,servizi,disponibilita) VALUES(03,'P3','F3',110,0,'PRESE','{"intervalli":[[["00:00", "23:59"]],[["00:00", "23:59"]],[["00:00", "23:59"]],[["00:00", "23:59"]],[["00:00", "23:59"]],[["00:00", "23:59"]],[["00:00", "23:59"]]]}');
INSERT INTO Aula(id,nome,edificio,n_posti,n_posti_occupati,servizi,disponibilita) VALUES(20,'F8','F2',110,0,'PRESE','{"intervalli":[[["08:00", "20:00"]],[["08:00", "20:00"]],[["08:00", "20:00"]],[["08:00", "20:00"]],[["08:00", "20:00"]],[],[]]}');
INSERT INTO Aula(id,nome,edificio,n_posti,n_posti_occupati,servizi,disponibilita) VALUES(21,'F7','F2',70,0,'','{"intervalli":[[["08:00", "20:00"]],[["08:00", "20:00"]],[["08:00", "20:00"]],[["08:00", "20:00"]],[["08:00", "20:00"]],[],[]]}');
INSERT INTO Aula(id,nome,edificio,n_posti,n_posti_occupati,servizi,disponibilita) VALUES(30,'P14','F3',35,0,'PRESE','{"intervalli":[[["08:00", "20:00"]],[["08:00", "20:00"]],[["08:00", "20:00"]],[["08:00", "20:00"]],[["08:00", "20:00"]],[],[]]}');
INSERT INTO Aula(id,nome,edificio,n_posti,n_posti_occupati,servizi,disponibilita) VALUES(32,'P6','F3',50,0,'COMPUTER','{"intervalli":[[["08:00", "20:00"]],[["08:00", "20:00"]],[["08:00", "20:00"]],[["08:00", "20:00"]],[["08:00", "20:00"]],[],[]]}');

INSERT INTO Utente(nome, cognome,email,password,tipo) VALUES ('Lorenzo','Capozzoli','l.capozzoli@studenti.unisa.it',SHA2('Lorenzo1', 256),'STUDENTE');
INSERT INTO Utente(nome, cognome,email,password,tipo) VALUES ('Antonio','De Caro','a.decaro@studenti.unisa.it',SHA2('Antonio2', 256),'STUDENTE');
INSERT INTO Utente(nome, cognome,email,password,tipo) VALUES ('Gianluca','Spinelli','g.spinelli@studenti.unisa.it',SHA2('Gianluca3', 256),'STUDENTE');
INSERT INTO Utente(nome, cognome,email,password,tipo) VALUES ('Simone Pasquale','Rocco','sp.rocco@studenti.unisa.it',SHA2('Simonerocco4', 256),'STUDENTE');
INSERT INTO Utente(nome, cognome,email,password,tipo) VALUES ('Marco','De Santis','m.desantis@studenti.unisa.it',SHA2('desan321', 256),'STUDENTE');

INSERT INTO Utente(nome, cognome,email,password,tipo) VALUES ('Alferdo','De Pasquale','a.depasquale@unisa.it',SHA2('Depasquale1', 256),'ADMIN');

INSERT INTO Utente(nome, cognome,email,password,tipo) VALUES ('Filomena','ferrucci','f.ferrucci@unisa.it',SHA2('Ferrucci1', 256),'DOCENTE');
INSERT INTO Utente(nome, cognome,email,password,tipo) VALUES ('Carmine','Gravino','c.gravino@unisa.it',SHA2('Gravino1', 256),'DOCENTE');
