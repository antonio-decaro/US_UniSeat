package model.database;

import model.dao.PrenotazioneDAO;
import model.dao.UtenteDAO;
import model.dao.ViolazioneEntityException;
import model.pojo.Utente;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Questa classe implementa l'interfaccia UtenteDAO, utilizzando come gestore della persistenza il DataBase
 * @author De Caro Antonio
 * @version 0.1
 * @see UtenteDAO
 * */
public class DBUtenteDAO implements UtenteDAO {
    private static Logger logger = Logger.getLogger(DBAulaDAO.class.getName());
    private static DBUtenteDAO dao;

    /**
     * Ritorna un oggetto singleton di tipo DBUtenteDAO.
     *
     * @return l'oggetto DBAulaDAO che accede agli oggetti Utente persistenti
     * @since 0.1
     * */
    public static UtenteDAO getInstance(){
        if (dao == null){
            try {
                dao = new DBUtenteDAO(DBConnection.getInstance());
            } catch (SQLException e){
                logger.log(Level.SEVERE, "{0}", e);
            }
        }
        return dao;
    }

    private DBUtenteDAO(Connection connection) throws SQLException {
        this.connection = DBConnection.getInstance();
    }

    private Connection connection;

    @Override
    public Utente retriveByEmail(String email) {
        //TODO implement
        return null;
    }

    @Override
    public void update(Utente utente) throws ViolazioneEntityException {
        //TODO implement
    }

    @Override
    public void insert(Utente utente) throws ViolazioneEntityException {
        //TODO implement
    }

    @Override
    public void delete(Utente utente) {
        //TODO implement
    }

    @Override
    public Set<Utente> retriveAll() {
        //TODO implement
        return null;
    }
}
