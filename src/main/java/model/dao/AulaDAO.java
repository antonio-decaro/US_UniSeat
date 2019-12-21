package model.dao;

import model.pojo.Aula;
import model.pojo.Edificio;

import java.util.Set;

/**
 * Questa interfaccia fornisce le operazioni effettuabili da un Data Access Object (DAO) che accede agli oggeti di tipo Aula
 *
 * @author De Caro Antonio
 * @version 0.1
 * @see Aula
 */
public interface AulaDAO {
    /**
     * Ricerca un'aula dato il suo identificativo
     *
     * @param id identificativo dell'aula da cercare
     * @return l'aula con l'identificativo specificato, null se non esiste alcuna aula con quell'id
     * @throws IllegalArgumentException se id < 0
     * @since ver 0.1
     */
    Aula retriveById(int id);

    /**
     * Aggiorna una determinata aula passata come parametro
     *
     * @param aula aula aggiornata
     * @throws ViolazioneEntityException se retriveById(aula.getId ()) == null
     * @since ver 0.1
     */
    void update(Aula aula) throws ViolazioneEntityException;

    /**
     * Inserisce una nuova aula passata come parametro
     *
     * @param aula aula da inserire
     * @throws ViolazioneEntityException se retriveById(aula.getId ()) != null
     * @since ver 0.1
     */
    void insert(Aula aula) throws ViolazioneEntityException;

    /**
     * Cerca tutte le aule presenti all'interno del gestore della persistenza.
     *
     * @return L'insieme delle aule presenti nel supporto di memorizzazione.
     * @since ver 0.1
     */
    Set<Aula> retriveAll();

    /**
     * Cerca tutte le aule di un determinato edificio passato come parametro
     *
     * @param edificio edificio da cui ricavare le aule
     * @return L'insieme delle aule dell'edificio passato come parametro presenti nel supporto di memorizzazione.
     * @since ver 0.1
     */
    Set<Aula> retriveByEdificio(Edificio edificio) throws ViolazioneEntityException;

}
