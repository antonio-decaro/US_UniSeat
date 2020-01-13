package model.dao;

import model.pojo.Edificio;

import java.util.List;

/**
 * Questa interfaccia fornisce le operazioni effettuabili da un Data Access Object (DAO) che accede agli oggeti di tipo Edificio
 *
 * @author De Caro Antonio
 * @version 0.1
 * @see Edificio
 */
public interface EdificioDAO {

    /**
     * Recupera l'edificio con il nome passato come parametro
     *
     * @param nome nome dell'edificio da cercare
     * @return l'edificio con il nome passato come parametro, oppure, null se non esiste alcun edificio con quel nome
     * @since ver 0.1
     */
    Edificio retriveByName(String nome);

    /**
     * Recupera tutti gli edifici
     *
     * @return l'insieme degli edifici presenti nel gestore della persistenza
     * */
    List<Edificio> retriveAll();
}
