package model.dao;

import pojo.*;
import pojo.Utente;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

/**
 * Questa interfaccia fornisce le operazioni effettuabili da un Data Access Object (DAO) che accede agli oggeti di tipo Prenotazione
 *
 * @author De Caro Antonio
 * @version 0.1
 * @see Edificio
 */
public interface PrenotazioneDAO {
    /**
     * Cerca all'interno del gestore della persistenza l'oggetto Prenotazione con id specificato.
     *
     * @param id id dell'oggetto Prenotazione da cercare
     * @return l'oggetto Prenotazione con l'id specificato, o null se questo non esiste
     * @throws IllegalArgumentException se id < 0
     * @since ver 0.1
     */
    Prenotazione retriveById(int id) throws IllegalArgumentException;

    /**
     * Cerca all'interno del gestore della persistenza gli oggetti Prenotazione aventi una certa data.
     *
     * @param data data in cui cercare le prenotazioni
     * @return la lista ordinata per data e ora delle prenotazioni di quella determinata data
     * @throws IllegalArgumentException se la data specificata ancora deve avvenire
     * @since ver 0.1
     */
    List<Prenotazione> retriveByData(Date data) throws IllegalArgumentException;

    /**
     * Cerca all'interno del gestore della persistenza gli oggetti Prenotazione di una determinata ora di un giorno.
     *
     * @param data data in cui cercare le prenotazioni
     * @param ora ora in cui cercare le prenotazioni
     * @return la lista ordinata per data e ora delle prenotazioni di quella determinata data e ora
     * @throws IllegalArgumentException se la data e l'ora specificati ancora devono avvenire
     * @since ver 0.1
     */
    List<Prenotazione> retriveByDataOra(Date data, Time ora) throws IllegalArgumentException;

    /**
     * Cerca all'interno del gestore della persistenza gli oggetti Prenotazioni creati da un determinato utente.
     *
     * @param utente utente che ha creato le prenotazioni
     * @return la lista ordinata per data e ora delle prenotazioni create dall'utente specificato
     * @throws ViolazioneEntityException se l'utente non esiste nel gestore della persistenza
     * @since ver 0.1
     */
    List<Prenotazione> retriveByUtente(Utente utente) throws ViolazioneEntityException;

    /**
     * Cerca all'interno del gestore della persistenza gli oggetti Prenotazione che sono associati ad una determinata
     * aula
     *
     * @param aula aula a cui sono associate le prenotazioni da cercare.
     * @return la lista ordinata per data e ora delle prenotazioni associate all'aula specificata
     * @throws ViolazioneEntityException se l'aula non esiste nel gestore della persistenza
     * @since ver 0.1
     */
    List<Prenotazione> retriveByAula(Aula aula) throws ViolazioneEntityException;

    /**
     * Inserisce una prenotazione all'interno del gestore della persistenza
     *
     * @param prenotazione aula da inserire nel gestore della persistenza
     * @throws ViolazioneEntityException se la prenotazione che si vuole inserire si sovrappone con una prenotazione
     * già esistente
     * @since ver 0.1
     */
    void insert(Prenotazione prenotazione) throws ViolazioneEntityException;

    /**
     * Elimina una prenotazione dall'interno del gestore della persistenza
     *
     * @param prenotazione prenotazione da eliminare dal gestore della persistenza
     * @since ver 0.1
     */
    void delete(Prenotazione prenotazione);

    /**
     * Restituisce tutti gli oggetti Prenotazione peresenti nel gestore della persistenza
     *
     * @return la lista ordinata per data e ora di tutte le prenotazioni
     * @since ver 0.1
     */
    List<Prenotazione> retriveAll();
}
