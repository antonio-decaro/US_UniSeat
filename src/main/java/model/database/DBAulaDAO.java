package model.database;

import model.dao.AulaDAO;
import model.dao.ViolazioneEntityException;
import model.pojo.Aula;
import model.pojo.Edificio;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Questa classe implementa l'interfaccia AulaDAO, utilizzando come gestore della persistenza il DataBase
 * @author De Caro Antonio
 * @version 0.1
 * @see AulaDAO
 * */
public class DBAulaDAO implements AulaDAO {

    private static Logger logger = Logger.getLogger(DBAulaDAO.class.getName());
    private static DBAulaDAO dao;

    /**
     * Ritorna un oggetto singleton di tipo DBAulaDAO.
     *
     * @return l'oggetto DBAulaDAO che accede agli oggetti Aula persistenti
     * @since 0.1
     * */
    public static AulaDAO getInstance(){
        if (dao == null){
            try {
                dao = new DBAulaDAO(DBConnection.getInstance().getConnection());
            } catch (SQLException e){
                logger.log(Level.SEVERE, "{0}", e);
            }
        }
        return dao;
    }

    private DBAulaDAO(Connection connection) throws SQLException {
        this.connection = connection;
    }

    private Connection connection;

    @Override
    public Aula retriveById(int id) {
        //TODO implement
        return null;
    }

    @Override
    public void update(Aula aula) throws ViolazioneEntityException {
        //TODO implement
    }

    @Override
    public void insert(Aula aula) throws ViolazioneEntityException {
        //TODO implement
    }

    @Override
    public Set<Aula> retriveAll() {
        //TODO implement
        return null;
    }

    @Override
    public Set<Aula> retriveByEdificio(Edificio edificio) {
        //TODO implement
        return null;
    }
}
