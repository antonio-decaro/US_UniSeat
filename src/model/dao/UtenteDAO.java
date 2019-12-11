package model.dao;

import model.pojo.Utente;

import java.util.Set;

/**
 * Questa interfaccia fornisce le operazioni effettuabili da un Data Access Object (DAO) che accede agli oggeti di tipo Utente
 * @author De Caro Antonio
 * @version 0.1
 * @see model.pojo.Edificio
 * */
public interface UtenteDAO {
    /**
     * //TODO inserire la javadoc per il metodo retriveByEmail
     * @since ver 0.1
     * */
    Utente retriveByEmail(String email);

    /**
     * //TODO inserire la javadoc per il metodo update
     * @since ver 0.1
     * */
    void update(Utente utente) throws ViolazioneEntity;

    /**
     * //TODO inserire la javadoc per il metodo insert
     * @since ver 0.1
     * */
    void insert(Utente utente) throws ViolazioneEntity;

    /**
     * //TODO inserire la javadoc per il metodo delete
     * @since ver 0.1
     * */
    void delete(Utente utente);

    /**
     * //TODO inserire la javadoc per il metodo retriveAll
     * @since ver 0.1
     * */
    Set<Utente> retriveAll();
}
