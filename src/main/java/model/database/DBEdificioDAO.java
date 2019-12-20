package model.database;

import model.dao.EdificioDAO;
import model.pojo.Edificio;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Questa classe implementa l'interfaccia EdificioDAO, utilizzando come gestore della persistenza il DataBase
 * @author De Caro Antonio
 * @version 0.1
 * @see EdificioDAO
 * */
public class DBEdificioDAO implements EdificioDAO {
    private static Logger logger = Logger.getLogger(DBAulaDAO.class.getName());
    private static DBEdificioDAO dao;

    /**
     * Ritorna un oggetto singleton di tipo DBEdificioDAO.
     *
     * @return l'oggetto DBAulaDAO che accede agli oggetti Edificio persistenti
     * @since 0.1
     * */
    public static EdificioDAO getInstance(){
        if (dao == null){
            dao = new DBEdificioDAO(DBConnection.getInstance().getConnection());
        }
        return dao;
    }

    private DBEdificioDAO(Connection connection) {
        this.connection = connection;
    }

    private Connection connection;

    @Override
    public Edificio retriveByName(String nome) {
        //TODO implement
        return null;
    }

    @Override
    public List<Edificio> retriveAll() {
        //TODO implement
        return null;
    }
}
