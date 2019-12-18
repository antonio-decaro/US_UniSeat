package model.pojo;

/**
 * Questa classe modella il concetto di "tipo di utente" all'interno del sistema
 *
 * @author De Caro Antonio
 * @version 0.1
 */
public enum TipoUtente {
    /**
     * Quest oggetto se associato ad un utente indica che l'utente è uno studente.
     */
    STUDENTE,
    /**
     * Quest oggetto se associato ad un utente indica che l'utente è un docente.
     */
    DOCENTE,
    /**
     * Quest oggetto se associato ad un utente indica che l'utente è Admin del sistema.
     */
    ADMIN
}
