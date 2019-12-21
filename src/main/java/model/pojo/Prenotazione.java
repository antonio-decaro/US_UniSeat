package model.pojo;

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
    private Date data;
    private Time oraInizio;
    private Time oraFine;
    private TipoPrenotazione tipoPrenotazione;
    private Aula aula;
    private Utente utente;

    public Prenotazione() {
    }

    public Prenotazione(int id, Date data, Time oraInizio, Time oraFine, TipoPrenotazione tipoPrenotazione,
                        Aula aula, Utente utente) {
        this.id = id;
        this.data = data;
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

    public Date getData() {
        return data;
    }

    public void setData(Date date) {
        this.data = date;
    }

    public Time getOraInizio() {
        return oraInizio;
    }

    public void setOraInizio(Time oraInizio) {
        this.oraInizio = oraInizio;
    }

    public Time getOraFine() {
        return oraFine;
    }

    public void setOraFine(Time oraFine) {
        this.oraFine = oraFine;
    }

    public TipoPrenotazione getTipoPrenotazione() {
        return tipoPrenotazione;
    }

    public void setTipoPrenotazione(TipoPrenotazione tipoPrenotazione) {
        this.tipoPrenotazione = tipoPrenotazione;
    }

    public Aula getAula() {
        return aula;
    }

    public void setAula(Aula aula) {
        this.aula = aula;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    @Override
    public String toString() {
        return "Prenotazione{" +
                "id=" + id +
                ", data=" + data +
                ", oraInizio=" + oraInizio +
                ", oraFine=" + oraFine +
                ", tipoPrenotazione=" + tipoPrenotazione +
                ", aula=" + aula +
                ", utente=" + utente +
                '}';
    }
}
