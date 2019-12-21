use uniseatDB;
Insert into Edificio(nome) values("F");
Insert into Edificio(nome) values("F2");
Insert into Edificio(nome) values ("F3");

Insert into Aula(id,nome,edificio,n_posti,n_posti_occupati,servizi,disponibilita) values(01,"E1","F",50,5,"l'aula e1 offre tre prese vicino al primo banco e cinque prese situate dietro agli ultimi banchi","aula disponibile");
Insert into Aula(id,nome,edificio,n_posti,n_posti_occupati,servizi,disponibilita) values(02,"E2","F",50,5,"l'aula e2 offre cinque prese vicino al primo banco e due prese situate dietro agli ultimi banchi","aula non disponibile");
Insert into Aula(id,nome,edificio,n_posti,n_posti_occupati,servizi,disponibilita) values(20,"F8","F2",60,30,"l'aula offre per ogni alunno seduto una presa in più possiede altre prese situate vicino alla cattedra","aula disponibile");
Insert into Aula(id,nome,edificio,n_posti,n_posti_occupati,servizi,disponibilita) values(21,"F7","F2",30,5,"l'aula offre in totale cinque prese, due delle quali sono in corrispondenza della cattedra e due vicino alla prima fila dei banchi","aula non disponibile");
Insert into Aula(id,nome,edificio,n_posti,n_posti_occupati,servizi,disponibilita) values(30,"P4","F3",100,35,"l'aula e1 offre sette prese vicino alla lavagna, due in prossimita del  primo banco e cinque prese situate dietro agli ultimi banchi","aula disponibile");
Insert into Aula(id,nome,edificio,n_posti,n_posti_occupati,servizi,disponibilita) values(32,"P6","F3",50,35,"l'aula e1 offre due prese in prossimita della lavagna e due disposte a destra e sinistra del primo banco","aula non disponibile");

Insert into Utente(nome, cognome,email,password,tipo) values ("Lorenzo","Capozzoli","l.capozzoli@studenti.unisa.it","Lorenzo1","Studente");
Insert into Utente(nome, cognome,email,password,tipo) values ("Antonio","De Caro","a.decaro@studenti.unisa.it","Antonio2","Studente");
Insert into Utente(nome, cognome,email,password,tipo) values ("Gianluca","Spinelli","g.spinelli@studenti.unisa.it","Gianluca3","Studente");
Insert into Utente(nome, cognome,email,password,tipo) values ("Simone Pasquale","Rocco","sp.rocco@studenti.unisa.it","Simone4","Studente");
Insert into Utente(nome, cognome,email,password,tipo) values ("Marco","De Santis","m.desantis@studenti.unisa.it","Des321","Studente");

Insert into Utente(nome, cognome,email,password,tipo) values ("Alferdo","De Pasquale","a.depasquale@studenti.unisa.it","Admin1","Admin");

Insert into Utente(nome, cognome,email,password,tipo) values ("Filomena","ferrucci","f.ferrucci@studenti.unisa.it","Ferrucci1","Docente");
Insert into Utente(nome, cognome,email,password,tipo) values ("Carmine","Gravino","c.gravino@studenti.unisa.it","Gravino1","Docente");


Insert into Prenotazione(utente,aula,data,ora_inizio,ora_fine,tipo) values("l.capozzoli@studenti.unisa.it","01","2019/05/12","9:00:00","11:00:00","POSTO");
Insert into Prenotazione(utente,aula,data,ora_inizio,ora_fine,tipo) values("a.decaro@studenti.unisa.it","02","2019/06/20","11:00:00","12:00:00","POSTO");
Insert into Prenotazione(utente,aula,data,ora_inizio,ora_fine,tipo) values("f.ferrucci@studenti.unisa.it","20","2019/06/28","09:00:00","12:00:00","AULA");
Insert into Prenotazione(utente,aula,data,ora_inizio,ora_fine,tipo) values("c.gravino@studenti.unisa.it","32","2019/05/12","15:00:00","17:00:00","AULA");

