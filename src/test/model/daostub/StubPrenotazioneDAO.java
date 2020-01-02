package model.daostub;

import model.dao.PrenotazioneDAO;
import model.dao.ViolazioneEntityException;
import model.pojo.Aula;
import model.pojo.Prenotazione;
import model.pojo.Utente;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class StubPrenotazioneDAO implements PrenotazioneDAO {

    private ArrayList<Prenotazione> prenotazioni;
    {
        prenotazioni = new ArrayList<>();
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

//        if (data.after(Date.valueOf(LocalDate.now()))) // controlla la precondizione
//            throw new IllegalArgumentException(String.format("La data %s ancora deve avvenire", data.toString()));

        List<Prenotazione> ret = new ArrayList<>();
        for (Prenotazione p : prenotazioni) {
            if (p.getData().toString().equals(data.toString())) {
                ret.add(p);
            }
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
    public void update(Prenotazione prenotazione) throws ViolazioneEntityException {

        for (ListIterator<Prenotazione> i = prenotazioni.listIterator(); i.hasNext();) {
            Prenotazione p = i.next();
            if (p.getId() == prenotazione.getId()) {
                i.remove();
                i.add(prenotazione);
                break;
            }
        }
    }

    @Override
    public List<Prenotazione> retriveAll() {
        return prenotazioni;
    }
}
