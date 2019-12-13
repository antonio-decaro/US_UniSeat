package model.pojo;

/**
 * Questa classe modella il concetto di "tipo di una prenotazione" all'interno sistema
 *
 * @author De Caro Antonio
 * @version 0.1
 */
public enum TipoPrenotazione {

    /**
     * Questo costante, se associata ad una prenotazione, indica che la prenotazione è stata effettuata sull'intera aula.
     */
    AULA,
    /**
     * Questo costante, se associata ad una prenotazione, indica che la prenotazione è stata effettuata sul singolo posto.
     */
    POSTO
}
