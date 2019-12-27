package model.database;

import model.dao.EdificioDAO;
import model.pojo.Edificio;

import java.util.List;

public class StubEdificioDAO implements EdificioDAO {
    @Override
    public Edificio retriveByName(String nome) {
        return null;
    }

    @Override
    public List<Edificio> retriveAll() {
        return null;
    }
}
