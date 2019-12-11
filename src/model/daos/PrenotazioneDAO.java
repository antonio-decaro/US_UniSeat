package model.daos;

import model.pojo.Aula;
import model.pojo.Prenotazione;
import model.pojo.Utente;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

/**
 * Questa interfaccia fornisce le operazioni effettuabili da un Data Access Object (DAO) che accede agli oggeti di tipo Prenotazione
 * @author De Caro Antonio
 * @version 0.1
 * @see model.pojo.Edificio
 * */
public interface PrenotazioneDAO {
    /**
     * //TODO inserire la javadoc per il metodo retriveById
     * @since ver 0.1
     * */
    Prenotazione retriveById(int id);

    /**
     * //TODO inserire la javadoc per il metodo retriveByData
     * @since ver 0.1
     * */
    List<Prenotazione> retriveByData(Date data);

    /**
     * //TODO inserire la javadoc per il metodo retriveByDataOra
     * @since ver 0.1
     * */
    List<Prenotazione> retriveByDataOra(Date data, Time ora);

    /**
     * //TODO inserire la javadoc per il metodo retriveByUtente
     * @since ver 0.1
     * */
    List<Prenotazione> retriveByUtente(Utente utente);

    /**
     * //TODO inserire la javadoc per il metodo retriveByAula
     * @since ver 0.1
     * */
    List<Prenotazione> retriveByAula(Aula aula);

    /**
     * //TODO inserire la javadoc per il metodo insert
     * @since ver 0.1
     * */
    void insert(Prenotazione prenotazione) throws ViolazioneEntity;

    /**
     * //TODO inserire la javadoc per il metodo delete
     * @since ver 0.1
     * */
    void delete(Prenotazione prenotazione);

    /**
     * //TODO inserire la javadoc per il metodo retriveAll
     * @since ver 0.1
     * */
    List<Prenotazione> retriveAll();
}
