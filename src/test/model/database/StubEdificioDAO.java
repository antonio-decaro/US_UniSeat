package model.database;

import model.dao.EdificioDAO;
import model.pojo.Aula;
import model.pojo.Edificio;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

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
        for (ListIterator<Edificio> i = edifici.listIterator(); i.hasNext();) {
            Edificio u = i.next();
            if (u.getNome().equals(nome)) {
                return u;
            }
        }
        return null;
    }

    @Override
    public List<Edificio> retriveAll() {
        return null;
    }
}
