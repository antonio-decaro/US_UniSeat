package model.database;

import model.dao.AulaDAO;
import model.dao.ViolazioneEntityException;
import model.pojo.Aula;
import model.pojo.Edificio;
import model.pojo.Utente;

import java.util.ArrayList;
import java.util.Set;

public class AulaDAOStub implements AulaDAO {

    private ArrayList<Aula> aule = new ArrayList<>();

    public ArrayList<Aula> getAule() {
        return aule;
    }

    @Override
    public Aula retriveById(int id) {
        for (Aula u : aule) {
            if (u.getId() == id)
                return u;
        }
        return null;
    }

    public Aula retriveByName(String name) {
        for (Aula u : aule) {
            if (u.getNome().equals(name))
                return u;
        }
        return null;
    }


    @Override
    public void update(Aula aula) throws ViolazioneEntityException {

    }

    @Override
    public boolean insert(Aula aula) throws ViolazioneEntityException {
        if (!aule.contains(aula)){
            aule.add(aula);
            return true;
        }
        return false;

    }

    @Override
    public Set<Aula> retriveAll() {
        return null;
    }

    @Override
    public Set<Aula> retriveByEdificio(Edificio edificio) throws ViolazioneEntityException {
        return null;
    }
}
