package model.dao;

import model.pojo.Utente;

import java.util.Set;

/**
 * Questa interfaccia fornisce le operazioni effettuabili da un Data Access Object (DAO) che accede agli oggeti di tipo Utente
 *
 * @author De Caro Antonio
 * @version 0.1
 * @see model.pojo.Edificio
 */
public interface UtenteDAO {
    /**
     * Ricerca all'interno del supporto di memorizzazione, l'utente con email specificata come parametro
     *
     * @param email l'email dell'utente da ricercare.
     * @return lo studente cercato se questo esiste nel supporto di memorizzazione persistente, altrimenti null se non
     * esiste alcuno studente con quel nome
     * @since ver 0.1
     */
    Utente retriveByEmail(String email);

    /**
     * Aggiorna un determinato utente passato come parametro
     *
     * @param utente utente aggiornato
     * @throws ViolazioneEntityException se retriveByEmail(utente.getEmail()) == null
     * @since ver 0.1
     */
    void update(Utente utente) throws ViolazioneEntityException;

    /**
     * Inserisce un nuovo utente passato come parametro
     *
     * @param utente utente da inserire
     * @throws ViolazioneEntityException se retriveByEmail(utente.getEmail()) != null
     * @since ver 0.1
     */
    void insert(Utente utente) throws ViolazioneEntityException;

    /**
     * Elimina un utente dal gestore della memorizzazione persistente
     *
     * @param utente utente da eliminare
     * @since ver 0.1
     */
    void delete(Utente utente);

    /**
     * Restituisce tutti gli utente presenti nel gestore della persistenza
     *
     * @return l'insieme degli utenti presenti nel gestore della persistenza
     * @since ver 0.1
     */
    Set<Utente> retriveAll();
}
