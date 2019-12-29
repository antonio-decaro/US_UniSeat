package model.database;

import model.dao.EdificioDAO;
import model.pojo.*;

import java.util.ArrayList;
import java.util.List;

public class EdificioDAOStub implements EdificioDAO {
    private ArrayList<Edificio> edifici = new ArrayList<>();

    {
        edifici.add(new Edificio("F3"));
    }
    @Override
    public Edificio retriveByName(String nome) {
        for (Edificio e : edifici) {
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
