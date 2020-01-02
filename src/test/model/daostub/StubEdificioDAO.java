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
        edifici.add(new Edificio("F3"));
        edifici.add(new Edificio("F2"));
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
        return null;
    }
}
