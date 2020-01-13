package model.daostub;

import model.dao.AulaDAO;
import model.dao.ViolazioneEntityException;
import model.pojo.Aula;
import model.pojo.Edificio;

import java.util.Set;
import java.util.TreeSet;

public class StubAulaDAO implements AulaDAO {

    private Set<Aula> aule = new TreeSet<>();

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
            if (u.getNome().equals(name)) {
                return u;
            }
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
        if (!aule.contains(aula)){
            aule.add(aula);
        }
        else{
            throw new ViolazioneEntityException("Aula già esistente!");
        }

    }

    @Override
    public Set<Aula> retriveAll() {
        return aule;
    }

    @Override
    public Set<Aula> retriveByEdificio(Edificio edificio) throws ViolazioneEntityException {
        Set<Aula> ret = new TreeSet<>();
        for (Aula a : aule) {
            if (a.getEdificio().equals(edificio))
                ret.add(a);
        }
        return ret;
    }
}
