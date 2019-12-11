package model.dao;

import model.pojo.Aula;
import model.pojo.Edificio;

import java.util.Set;

/**
 * Questa interfaccia fornisce le operazioni effettuabili da un Data Access Object (DAO) che accede agli oggeti di tipo Aula
 * @author De Caro Antonio
 * @version 0.1
 * @see model.pojo.Aula
 * */
public interface AulaDAO {
    /**
     * Ricerca un'aula dato il suo identificativo
     * @param id identificativo dell'aula da cercare
     * @return l'aula con l'identificativo specificato, null se non esiste alcuna aula con quell'id
     * @since ver 0.1
     * */
    Aula retriveById(int id);

    /**
     * Aggiorna una determinata aula passata come parametro
     * @param aula aula aggiornata
     * @pre retriveById(aula.getId()) != null
     * @post aula.equals(retriveById(aula.getId())) == true
     * @throws ViolazioneEntity Questa eccezione viene lanciata quando viene violata la precondizione
     * @since ver 0.1
     * */
    void update(Aula aula) throws ViolazioneEntity;

    /**
     * Inserisce una nuova aula passata come parametro
     * @param aula aula da inserire
     * @pre L'aula passata come parametro deve avere un id che già esiste nel gestore della persistenza
     * @throws ViolazioneEntity Questa eccezione viene lanciata quando viene violata la precondizione
     * @since ver 0.1
     * */
    void insert(Aula aula) throws ViolazioneEntity;

    /**
     * Cerca tutte le aule presenti all'interno del gestore della persistenza.
     * @return L'insieme delle aule presenti nel supporto di memorizzazione.
     * @since ver 0.1
     * */
    Set<Aula> retriveAll();

    /**
     * Cerca tutte le aule di un determinato edificio passato come parametro
     * @param edificio edificio da cui ricavare le aule
     * @return L'insieme delle aule dell'edificio passato come parametro presenti nel supporto di memorizzazione.
     * @since ver 0.1
     * */
    Set<Aula> retriveByEdificio(Edificio edificio);
}
