package model.database;

import model.dao.UtenteDAO;
import model.dao.ViolazioneEntityException;
import model.pojo.TipoUtente;
import model.pojo.Utente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
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
            dao = new DBUtenteDAO(DBConnection.getInstance().getConnection());
        }
        return dao;
    }

    private DBUtenteDAO(Connection connection) {
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
        final String QUERY = "UPDATE utente SET email = ?, nome = ?, cognome = ?, tipo = ?, password = ?, codice_verifica = ? WHERE email = ?";

        try {

            PreparedStatement stm = connection.prepareStatement(QUERY);
            stm.setString(1, utente.getEmail());
            stm.setString(2, utente.getNome());
            stm.setString(3, utente.getCognome());
            stm.setString(4, utente.getTipoUtente().toString());
            stm.setString(5, utente.getPassword());
            stm.setLong(6, utente.getCodiceVerifica());
            stm.setString(7, utente.getEmail());

            if (DBUtenteDAO.getInstance().retriveByEmail(utente.getEmail()) == null)
                throw new ViolazioneEntityException(String.format("Non esiste l'utente %s nel database", utente));

            stm.executeUpdate();

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "{0}", e);
            throw new ViolazioneEntityException(e.getMessage());
        }
    }

    @Override
    public void insert(Utente utente) throws ViolazioneEntityException {
        final String QUERY = "INSERT INTO utente(nome, cognome, email, password, tipo, codice_verifica)  " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement stm = connection.prepareStatement(QUERY);
            stm.setString(1, utente.getNome());
            stm.setString(2, utente.getCognome());
            stm.setString(3, utente.getEmail());
            stm.setString(4, utente.getPassword());
            stm.setString(5, utente.getTipoUtente().toString());
            stm.setLong(6, utente.getCodiceVerifica());
            stm.executeUpdate();

        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
            throw new ViolazioneEntityException("Utente già esistente");
        }
    }

    @Override
    public void delete(Utente utente) throws ViolazioneEntityException {
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
        Utente ret = new Utente();
        ret.setEmail(rs.getString("email"));
        ret.setCognome(rs.getString("cognome"));
        ret.setNome(rs.getString("nome"));
        ret.setPassword(rs.getString("password"));
        ret.setTipoUtente(TipoUtente.valueOf(rs.getString("tipo")));
        ret.setCodiceVerifica(rs.getInt("codice_verifica"));
        return ret;
    }
}
