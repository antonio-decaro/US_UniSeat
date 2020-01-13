package model.daostub;

import model.dao.EdificioDAO;
import model.pojo.Edificio;

import java.util.ArrayList;
import java.util.List;

public class
StubEdificioDAO implements EdificioDAO {

    private ArrayList<Edificio> edifici;

    {
        edifici = new ArrayList<>();
        Edificio edificio1 = new Edificio("F3");
        Edificio edificio2 = new Edificio("F2");
        edifici.add(edificio1);
        edifici.add(edificio2);
    }

    @Override
    public Edificio retriveByName(String nome) {
        for(Edificio e : edifici) {
            if (e.getNome().equals(nome))
                return e;
        }

        return null;
    }

    @Override
    public List<Edificio> retriveAll() {
        return edifici;
    }
}
