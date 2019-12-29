package model.database;

import control.utili.PassowrdEncrypter;
import model.dao.PrenotazioneDAO;
import model.dao.ViolazioneEntityException;
import model.pojo.*;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class PrenotazioneDAOStub implements PrenotazioneDAO {

    private ArrayList<Prenotazione> prenotazioni = new ArrayList<>();
    private UtenteDAOStub utente = new UtenteDAOStub();
    private EdificioDAOStub edificio = new EdificioDAOStub();


    {
        prenotazioni.add(new Prenotazione(1, new Date(2019 - 11 - 15), new Time(14), new Time(16), TipoPrenotazione.POSTO, new Aula("P4", 20, 200, "", edificio.retriveAll().get(0)), utente.retriveAll().get(0)));
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
        ArrayList<Prenotazione> pByDate = new ArrayList<>();

        for (Prenotazione p : prenotazioni) {
            if (p.getData().equals(data))
                pByDate.add(p);
        }
        return pByDate;
    }

    @Override
    public List<Prenotazione> retriveByDataOra(Date data, Time ora) throws IllegalArgumentException {
        ArrayList<Prenotazione> pByDate = new ArrayList<>();

        for (Prenotazione p : prenotazioni) {
            if (p.getData().equals(data) && p.getOraInizio().equals(ora))
                pByDate.add(p);
        }
        return pByDate;
    }

    @Override
    public List<Prenotazione> retriveByUtente(Utente utente) throws ViolazioneEntityException {
        ArrayList<Prenotazione> pByDate = new ArrayList<>();

        for (Prenotazione p : prenotazioni) {
            if (p.getUtente().equals(utente))
                pByDate.add(p);
        }
        return pByDate;
    }

    @Override
    public List<Prenotazione> retriveByAula(Aula aula) throws ViolazioneEntityException {
        ArrayList<Prenotazione> pByDate = new ArrayList<>();

        for (Prenotazione p : prenotazioni) {
            if (p.getAula().equals(aula))
                pByDate.add(p);
        }
        return pByDate;
    }

    @Override
    public void insert(Prenotazione prenotazione) throws ViolazioneEntityException {
        if (!prenotazioni.contains(prenotazione))
            prenotazioni.add(prenotazione);
        else
            throw new ViolazioneEntityException("Prenotazione già esistente");
    }

    @Override
    public void delete(Prenotazione prenotazione) { prenotazioni.remove(prenotazione); }

    @Override
    public List<Prenotazione> retriveAll() { return prenotazioni; }

}
