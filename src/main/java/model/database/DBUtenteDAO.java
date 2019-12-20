package model.database;

import model.dao.AulaDAO;
import model.dao.PrenotazioneDAO;
import model.dao.UtenteDAO;
import model.dao.ViolazioneEntityException;
import model.pojo.Prenotazione;
import model.pojo.TipoPrenotazione;
import model.pojo.TipoUtente;
import model.pojo.Utente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Questa classe implementa l'interfaccia UtenteDAO, utilizzando come gestore della persistenza il DataBase
 * @author Spinelli Gianluca
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
                dao = new DBUtenteDAO(DBConnection.getInstance().getConnection());
            } catch (SQLException e){
                logger.log(Level.SEVERE, "{0}", e);
            }
        }
        return dao;
    }

    private DBUtenteDAO(Connection connection) throws SQLException {
        this.connection = connection;
    }

    private Connection connection;

    @Override
    public Utente retriveByEmail(String email) {
        final String QUERY = "SELECT * FROM utente WHERE email=?";

        try {
            PreparedStatement stm = connection.prepareStatement(QUERY);
            stm.setString(1, email);
            stm.execute();
            ResultSet rs = stm.getResultSet();
            if (!rs.next())
                return null;
            return getUtenteFromResultSet(rs);

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "{0}", e);
            return null;
        }


    }

    @Override
    public void update(Utente utente) throws ViolazioneEntityException {
        final String QUERY = "UPDATE utente SET email = ?, nome = ?, cognome = ?, utente = ?, password = ? WHERE email = ?";

        try {

            PreparedStatement stm = connection.prepareStatement(QUERY);
            stm.setString(1, utente.getEmail());
            stm.setString(2, utente.getNome());
            stm.setString(3, utente.getCognome());
            stm.setString(4, utente.getUtente());
            stm.setString(5, utente.getPassword());
            stm.setString(6, utente.getEmail());

            if (DBUtenteDAO.getInstance().retriveByEmail(utente.getEmail()) == null)
                throw new ViolazioneEntityException(String.format("Non esiste l'utente %s nel database", utente));

            if (!utente.getUtente().equals(TipoUtente.ADMIN) || !utente.getUtente().equals(TipoUtente.DOCENTE) || !utente.getUtente().equals(TipoUtente.STUDENTE))
                throw new ViolazioneEntityException(String.format("Non esiste questo tipo di utente %s nel database", utente));

            stm.executeUpdate();

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "{0}", e);
            throw new ViolazioneEntityException(e.getMessage());
        }
    }

    @Override
    public void insert(Utente utente) throws ViolazioneEntityException {
        final String QUERY = "INSERT INTO utente(nome, cognome, email, password, tipo)  " +
                "VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement stm = connection.prepareStatement(QUERY);
            stm.setString(1, utente.getNome());
            stm.setString(2, utente.getCognome());
            stm.setString(3, utente.getEmail());
            stm.setString(4, utente.getPassword());
            stm.setString(5, utente.getUtente());
            stm.executeUpdate();

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "{0}", e);
            throw new ViolazioneEntityException(e.getMessage());
        }
    }

    @Override
    public void delete(Utente utente) {
        final String QUERY = "DELETE FROM utente WHERE email=?";

        if (DBUtenteDAO.getInstance().retriveByEmail(utente.getEmail()) == null)
            throw new ViolazioneEntityException(String.format("Non esiste l'utente %s nel database", utente));

        try {
            PreparedStatement stm = connection.prepareStatement(QUERY);
            stm.setString(1, utente.getEmail());
            stm.executeUpdate();

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "{0}", e);
        }
    }

    @Override
    public List<Utente> retriveAll() {
        final String QUERY = "SELECT * FROM utente";
        List<Utente> ret = new LinkedList<>();
        try {
            PreparedStatement stm = connection.prepareStatement(QUERY);
            stm.execute();

            ResultSet rs = stm.getResultSet();
            while(rs.next()){
                ret.add(getUtenteFromResultSet(rs));
            }
            return ret;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "{0}", e);
            return null;
        }

    }

    private Utente getUtenteFromResultSet(ResultSet rs) throws SQLException {
        UtenteDAO utenteDAO = DBUtenteDAO.getInstance();
        Utente ret = new Utente();
        ret.setEmail(rs.getString("email"));
        ret.setCognome(rs.getString("cognome"));
        ret.setNome(rs.getString("nome"));
        ret.setPassword(rs.getString("password"));
        ret.setUtente(TipoUtente.valueOf(rs.getString("utente")));
        return ret;
    }
}
