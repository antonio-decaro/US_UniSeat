package model.database;

import control.utili.PassowrdEncrypter;
import model.dao.PrenotazioneDAO;
import model.dao.UtenteDAO;
import model.dao.ViolazioneEntityException;
import model.pojo.Aula;
import model.pojo.Prenotazione;
import model.pojo.TipoUtente;
import model.pojo.Utente;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class StubPrenotazioneDAO implements PrenotazioneDAO {

    private ArrayList<Prenotazione> prenotazioni;

    {
        prenotazioni = new ArrayList<>();
    }

    @Override
    public Prenotazione retriveById(int id) throws IllegalArgumentException {

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
