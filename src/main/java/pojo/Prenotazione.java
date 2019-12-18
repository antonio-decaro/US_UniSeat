package pojo;

import org.jetbrains.annotations.NotNull;

import java.sql.Date;
import java.sql.Time;

/**
 * Questa classe modella il concetto di "Prenotazione" all'interno del sistema.
 *
 * @author De Caro Antonio
 * @version 0.1
 */
public class Prenotazione {

    private int id;
    private Date date;
    private Time oraInizio;
    private Time oraFine;
    private TipoPrenotazione tipoPrenotazione;
    private Aula aula;
    private Utente utente;

    public Prenotazione() {
    }

    public Prenotazione(int id, @NotNull Date date, @NotNull Time oraInizio, @NotNull Time oraFine,
                        @NotNull TipoPrenotazione tipoPrenotazione, @NotNull Aula aula, @NotNull Utente utente) {
        this.id = id;
        this.date = date;
        this.oraInizio = oraInizio;
        this.oraFine = oraFine;
        this.tipoPrenotazione = tipoPrenotazione;
        this.aula = aula;
        this.utente = utente;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(@NotNull Date date) {
        this.date = date;
    }

    public Time getOraInizio() {
        return oraInizio;
    }

    public void setOraInizio(@NotNull Time oraInizio) {
        this.oraInizio = oraInizio;
    }

    public Time getOraFine() {
        return oraFine;
    }

    public void setOraFine(@NotNull Time oraFine) {
        this.oraFine = oraFine;
    }

    public TipoPrenotazione getTipoPrenotazione() {
        return tipoPrenotazione;
    }

    public void setTipoPrenotazione(@NotNull TipoPrenotazione tipoPrenotazione) {
        this.tipoPrenotazione = tipoPrenotazione;
    }

    public Aula getAula() {
        return aula;
    }

    public void setAula(@NotNull Aula aula) {
        this.aula = aula;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(@NotNull Utente utente) {
        this.utente = utente;
    }
}
