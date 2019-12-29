package model.database;

import control.utili.PassowrdEncrypter;
import model.dao.PrenotazioneDAO;
import model.dao.UtenteDAO;
import model.dao.ViolazioneEntityException;
import model.pojo.*;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class StubPrenotazioneDAO implements PrenotazioneDAO {

    private ArrayList<Prenotazione> prenotazioni;
    {
        prenotazioni = new ArrayList<>();
        prenotazioni.add(new Prenotazione(1,new Date(2019-11-11), new Time(14), new Time(16),
                TipoPrenotazione.POSTO, new Aula("P3", 20,
                200, "sd", new Edificio("F3")),
                new Utente("a.decaro@studenti.unisa.it", "Antonio", "De Caro",
                        "Antonio2", TipoUtente.STUDENTE)));
        prenotazioni.get(0).getAula().setId(1);
        prenotazioni.add(new Prenotazione(2,new Date(2019-11-11), new Time(14), new Time(16),
                TipoPrenotazione.POSTO, new Aula("P4", 20, 200, "sd", new Edificio("F3")),
                new Utente("c.gravino@studenti.unisa.it", "Carmine", "Gravino",
                        "Gravino1", TipoUtente.DOCENTE)));
        prenotazioni.get(1).getAula().setId(2);
    }

    @Override
    public Prenotazione retriveById(int id) throws IllegalArgumentException {
        if (id < 0)
            throw new IllegalArgumentException(String.format("L'id %d non è valido.", id));

        for (Prenotazione p : prenotazioni) {
            if (p.getId() == id)
                return p;
        }
        return null;
    }

    @Override
    public List<Prenotazione> retriveByData(Date data) throws IllegalArgumentException {
        List<Prenotazione> ret = new ArrayList<>();
        for (Prenotazione p : prenotazioni) {
            if (p.getData().equals(data))
                ret.add(p);
        }
        return ret;
    }

    @Override
    public List<Prenotazione> retriveByDataOra(Date data, Time ora) throws IllegalArgumentException {
        List<Prenotazione> ret = new ArrayList<>();
        for (Prenotazione p : prenotazioni) {
            if (p.getData().equals(data) && p.getOraInizio().equals(ora))
                ret.add(p);
        }
        return ret;
    }

    @Override
    public List<Prenotazione> retriveByUtente(Utente utente) throws ViolazioneEntityException {
        List<Prenotazione> ret = new ArrayList<>();
        for (Prenotazione p : prenotazioni) {
            if (p.getUtente().equals(utente))
                ret.add(p);
        }
        return ret;
    }

    @Override
    public List<Prenotazione> retriveByAula(Aula aula) throws ViolazioneEntityException {
        List<Prenotazione> ret = new ArrayList<>();
        for (Prenotazione p : prenotazioni) {
            if (p.getAula().equals(aula))
                ret.add(p);
        }
        return ret;
    }

    @Override
    public void insert(Prenotazione prenotazione) throws ViolazioneEntityException {
        prenotazioni.add(prenotazione);
    }

    @Override
    public void delete(Prenotazione prenotazione) {
        prenotazioni.remove(prenotazione);
    }

    @Override
    public List<Prenotazione> retriveAll() {
        return prenotazioni;
    }
}
