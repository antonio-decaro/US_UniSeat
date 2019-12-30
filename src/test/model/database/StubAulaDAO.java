package model.database;

import model.dao.AulaDAO;
import model.dao.ViolazioneEntityException;
import model.pojo.Aula;
import model.pojo.Edificio;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Set;

public class StubAulaDAO implements AulaDAO {

    private ArrayList<Aula> aule = new ArrayList<>();

    /*  QUESTO NON VA FATTO QUI, VA FATTO NELLA CLASSE DI TEST
    {
        try {
            Edificio ed = new StubEdificioDAO().retriveByName("F3");
            String dispP3 = Files.readString(Paths.get("./src/test/resources/TC_3/disp_aulaP3.json"));
            String dispP4 = Files.readString(Paths.get("./src/test/resources/TC_3/disp_aulaP4.json"));
            Aula aulaP3 = new Aula("P3", 70, 100, dispP3, ed);
            Aula aulaP4 = new Aula("P4", 0, 100, dispP4, ed);
            aulaP3.setId(1);
            aulaP4.setId(2);
            aule.add(aulaP3);
            aule.add(aulaP4);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    */

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
        if (!aule.contains(aula)){
            aule.add(aula);
        }
        else{
            throw new ViolazioneEntityException("Aula già esistente!");
        }

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
