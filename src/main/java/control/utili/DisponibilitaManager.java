package control.utili;

import com.google.gson.Gson;
import model.dao.PrenotazioneDAO;
import model.pojo.Aula;
import model.pojo.Prenotazione;
import model.pojo.TipoPrenotazione;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Questa classe racchiude il concectto di Disponibilità di un aula.
 *
 * @author De Caro Antonio
 * @version 0.1
 * @see model.pojo.Aula
 * */
public class DisponibilitaManager {

    private DisponibilitaGiornaliera disponibilita;
    private Aula aula;
    private PrenotazioneDAO prenotazioneDAO;

    /**
     * Costruttore
     *
     * @param aula aula di cui gestire la disponibilità
     * @param prenotazioneDAO dao che accede agli oggetti di tipo Prenotazone nel gestore della persistenza
     * @since v 0.1
     * */
    public DisponibilitaManager(Aula aula, PrenotazioneDAO prenotazioneDAO){
        Gson gson = new Gson();
        this.aula = aula;
        this.disponibilita = gson.fromJson(aula.getDisponibilita(), DisponibilitaGiornaliera.class);
        this.prenotazioneDAO = prenotazioneDAO;
    }

    /**
     * Questo metodo controlla se il posto di un aulta è disponibile ad una determinata ora di un determinato giorno.
     *
     * @param data data in cui controllare la disponibilità
     * @param oraInizio ora di inizio prenotazione cui controllare la disponibilità
     * @param oraFine ora di fine prenotazione in cui controllare la disponibilità
     * @return true se il posto è disponible, false altrimenti
     * @throws IllegalArgumentException se oraFine è minore di oraInizio
     * @since v 0.1
     * */
    public boolean isPostoDisponibile(Date data, Time oraInizio, Time oraFine) throws IllegalArgumentException {
        return isAulaDisponibile(data, oraInizio, oraFine) && aula.getPostiOccupati() < aula.getPosti();
    }

    /**
     * Questo metodo controlla se un aulta è disponibile ad una determinata ora di un determinato giorno.
     *
     * @param data data in cui controllare la disponibilità
     * @param oraInizio ora di inizio prenotazione cui controllare la disponibilità
     * @param oraFine ora di fine prenotazione in cui controllare la disponibilità
     * @return true se l'aula è disponible, false altrimenti
     * @throws IllegalArgumentException se oraFine è minore di oraInizio
     * @since v 0.1
     * */
    public boolean isAulaDisponibile(Date data, Time oraInizio, Time oraFine) throws IllegalArgumentException {
        if (oraInizio.after(oraFine))
            throw new IllegalArgumentException("Il parametro oraFine non può essere antecedente a oraInizio");

        int dayIndex = data.toLocalDate().getDayOfWeek().getValue() - 1;

        if (this.disponibilita.intervalli[dayIndex].isEmpty())
            return false;

        for (String[] intervallo : this.disponibilita.intervalli[dayIndex]) {
            Time[] tmp = parseIntervallo(intervallo);
            if (!comparaIntervallo(oraInizio, oraFine, tmp[0], tmp[1])) {
                return false;
            }
        }

        List<Prenotazione> prenotazioni = getPrenotazioniAule(data, oraInizio, oraFine);
        return prenotazioni.isEmpty();
    }

    private List<Prenotazione> getPrenotazioniAule(Date data, Time oraInizio, Time oraFine) {
        List<Prenotazione> prenotazioni = prenotazioneDAO.retriveByAula(this.aula);
        prenotazioni.retainAll(prenotazioneDAO.retriveByData(data));

        prenotazioni.removeIf(p -> p.getTipoPrenotazione().equals(TipoPrenotazione.POSTO));
        prenotazioni.removeIf(p -> p.getOraFine().equals(oraInizio) || p.getOraInizio().equals(oraFine));
        prenotazioni.removeIf(p -> p.getOraFine().before(oraInizio) || p.getOraInizio().after(oraFine));
        prenotazioni.removeIf(p -> p.getTipoPrenotazione().equals(TipoPrenotazione.POSTO));

        return prenotazioni;
    }

    private boolean comparaIntervallo(Time oraInizio1, Time oraFine1, Time oraInizio2, Time oraFine2) {
        long oraInizioV1 = oraInizio1.toLocalTime().getHour() * 60 + oraInizio1.toLocalTime().getSecond();
        long oraFineV1 = oraFine1.toLocalTime().getHour() * 60 + oraFine1.toLocalTime().getSecond() - 1;
        long oraInizioV2 = oraInizio2.toLocalTime().getHour() * 60 + oraInizio2.toLocalTime().getSecond();
        long oraFineV2 = oraFine2.toLocalTime().getHour() * 60 + oraFine2.toLocalTime().getSecond();

        return oraInizioV1 >= oraInizioV2 && oraFineV1 <= oraFineV2;
    }

    private Time[] parseIntervallo(String[] intervallo){
        Time[] ret = new Time[2];

        String[] tmpInizio = intervallo[0].split(":");
        String[] tmpFine = intervallo[1].split(":");

        LocalTime inizio = LocalTime.ofSecondOfDay(
                ((Integer.parseInt(tmpInizio[0]) * (60 * 60)) + (Integer.parseInt(tmpInizio[1]) * 60)));
        LocalTime fine = LocalTime.ofSecondOfDay(
                ((Integer.parseInt(tmpFine[0]) * (60 * 60)) + (Integer.parseInt(tmpFine[1]) * 60)) - 1);

        ret[0] = Time.valueOf(inizio);
        ret[1] = Time.valueOf(fine);
        return ret;
    }

    static class DisponibilitaGiornaliera {

        ArrayList<String[]>[] intervalli;

        {
            intervalli = new ArrayList[7];
            Arrays.fill(intervalli, new ArrayList<>());
        }
    }
}
