package model.database;

import model.dao.AulaDAO;
import model.dao.ViolazioneEntityException;
import model.pojo.Aula;
import model.pojo.Edificio;

import java.util.ArrayList;
import java.util.Set;

public class StubAulaDAO implements AulaDAO {

    private ArrayList<Aula> aule = new ArrayList<>();


    {

        Edificio ed = new StubEdificioDAO().retriveByName("F3");
        aule.add(new Aula("P4", 70, 100, "Lunedi:10:00-13:00", ed));

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
        for (Aula a : aule){
            if (a.getId() == aula.getId()) {
                a.setPostiOccupati(aula.getPostiOccupati());
                a.setPosti(aula.getPosti());
                a.setDisponibilita(aula.getDisponibilita());
                a.setEdificio(aula.getEdificio());
                a.setNome(aula.getNome());
                a.setServizi(aula.getServizi());
                break;
            }
        }
    }

    @Override
    public void insert(Aula aula) throws ViolazioneEntityException {
        if (!aule.contains(aula))
            aule.add(aula);
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
