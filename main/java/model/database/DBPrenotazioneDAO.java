package model.database;

import model.dao.AulaDAO;
import model.dao.PrenotazioneDAO;
import model.dao.UtenteDAO;
import model.dao.ViolazioneEntityException;
import model.pojo.Aula;
import model.pojo.Prenotazione;
import model.pojo.TipoPrenotazione;
import model.pojo.Utente;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Questa classe implementa l'interfaccia PrenotazioneDAO, utilizzando come gestore della persistenza il DataBase
 * @author De Caro Antonio
 * @version 0.1
 * @see PrenotazioneDAO
 * */
public class DBPrenotazioneDAO implements PrenotazioneDAO {

    /**
     * Ritorna un oggetto singleton di tipo DBEdificioDAO.
     *
     * @return l'oggetto DBPrenotazioneDAO che accede agli oggetti Prenotazione persistenti
     * @since 0.1
     * */
    public static PrenotazioneDAO getInstance(){
        if (dao == null){
            dao = new DBPrenotazioneDAO(DBConnection.getInstance().getConnection());
        }
        return dao;
    }

    private DBPrenotazioneDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Prenotazione retriveById(int id) throws IllegalArgumentException {
        final String QUERY = "SELECT * FROM prenotazione WHERE id = ?";

        if (id < 0)
            throw new IllegalArgumentException(String.format("L'id %d non è valido.", id));

        try {
            PreparedStatement stm = connection.prepareStatement(QUERY);
            stm.setInt(1, id);
            stm.execute();


            ResultSet rs = stm.getResultSet();
            if (!rs.next())
                return null;
            return getPrenotazioneFromResultSet(rs);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "{0}", e);
            return null;
        }
    }

    @Override
    public List<Prenotazione> retriveByData(Date data) {
        final String QUERY = "SELECT * FROM prenotazione WHERE data = ?";

        List<Prenotazione> ret = new LinkedList<>();
        try {
            PreparedStatement stm = connection.prepareStatement(QUERY);
            stm.setDate(1, data);
            stm.execute();

            ResultSet rs = stm.getResultSet();
            while(rs.next()){
                ret.add(getPrenotazioneFromResultSet(rs));
            }

            return ret;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "{0}", e);
            return null;
        }
    }


    @Override
    public List<Prenotazione> retriveByDataOra(Date data, Time ora) {
        final String QUERY = "SELECT * FROM prenotazione WHERE data=? AND ora_inizio=?";

        List<Prenotazione> ret = new LinkedList<>();
        try {
            PreparedStatement stm = connection.prepareStatement(QUERY);
            stm.setDate(1, data);
            stm.setTime(2, ora);
            stm.execute();

            ResultSet rs = stm.getResultSet();
            while(rs.next()){
                ret.add(getPrenotazioneFromResultSet(rs));
            }

            return ret;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "{0}", e);
            return null;
        }
    }

    @Override
    public List<Prenotazione> retriveByUtente(Utente utente) throws ViolazioneEntityException {
        final String QUERY = "SELECT * FROM prenotazione WHERE utente=?";

        if (DBUtenteDAO.getInstance().retriveByEmail(utente.getEmail()) == null)
            throw new ViolazioneEntityException(String.format("Non esiste l'utente %s nel database", utente));

        List<Prenotazione> ret = new LinkedList<>();
        try {
            PreparedStatement stm = connection.prepareStatement(QUERY);
            stm.setString(1, utente.getEmail());
            stm.execute();

            ResultSet rs = stm.getResultSet();
            while(rs.next()){
                ret.add(getPrenotazioneFromResultSet(rs));
            }
            return ret;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "{0}", e);
            return null;
        }
    }

    @Override
    public List<Prenotazione> retriveByAula(Aula aula) throws ViolazioneEntityException {
        final String QUERY = "SELECT * FROM prenotazione WHERE aula=?";

        if (DBAulaDAO.getInstance().retriveById(aula.getId()) == null)
            throw new ViolazioneEntityException(String.format("Non esiste l'aula %s nel database", aula));

        List<Prenotazione> ret = new LinkedList<>();
        try {
            PreparedStatement stm = connection.prepareStatement(QUERY);
            stm.setInt(1, aula.getId());
            stm.execute();

            ResultSet rs = stm.getResultSet();
            while(rs.next()){
                ret.add(getPrenotazioneFromResultSet(rs));
            }
            return ret;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "{0}", e);
            return null;
        }
    }

    @Override
    public void insert(Prenotazione prenotazione) throws ViolazioneEntityException {
        final String QUERY = "INSERT INTO prenotazione(utente, aula, data, ora_inizio, ora_fine, tipo)  " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try {
            connection.setAutoCommit(false);
            PreparedStatement stm = connection.prepareStatement(QUERY);
            stm.setString(1, prenotazione.getUtente().getEmail());
            stm.setInt(2, prenotazione.getAula().getId());
            stm.setDate(3, prenotazione.getData());
            stm.setTime(4, prenotazione.getOraInizio());
            stm.setTime(5, prenotazione.getOraFine());
            stm.setString(6, prenotazione.getTipoPrenotazione().toString().toUpperCase());
            stm.executeUpdate();

            createEvent(prenotazione);
            if (prenotazione.getTipoPrenotazione().equals(TipoPrenotazione.AULA)) {
                createEventForStart(prenotazione);
            }

            connection.commit();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, e.getMessage());
            }
            throw new ViolazioneEntityException(e.getMessage());
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, ex.getMessage());
            }
        }
    }

    @Override
    public void delete(Prenotazione prenotazione) {
        final String QUERY = "DELETE FROM prenotazione WHERE id=?";

        if (DBPrenotazioneDAO.getInstance().retriveById(prenotazione.getId()) == null)
            throw new ViolazioneEntityException(String.format("Non esiste la prenotazione %s nel database", prenotazione));

        try {
            connection.setAutoCommit(false);
            dropEvent(prenotazione, PULISCI);
            if (prenotazione.getTipoPrenotazione().equals(TipoPrenotazione.AULA)){
                dropEvent(prenotazione, OCCUPA);
            }

            PreparedStatement stm = connection.prepareStatement(QUERY);
            stm.setInt(1, prenotazione.getId());
            stm.execute();

            connection.commit();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, e.getMessage());
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, ex.getMessage());
            }
        }
    }

    @Override
    public void update(Prenotazione prenotazione) throws ViolazioneEntityException {
        final String QUERY=
                "UPDATE prenotazione SET data = ?, ora_inizio = ?, ora_fine = ? WHERE id = ?";

        if (retriveById(prenotazione.getId()) == null)
            throw new ViolazioneEntityException("Non esiste alcuna prenotazione con id " + prenotazione.getId());

        try {
            connection.setAutoCommit(false);
            PreparedStatement stm = connection.prepareStatement(QUERY);
            stm.setDate(1, prenotazione.getData());
            stm.setTime(2, prenotazione.getOraInizio());
            stm.setTime(3, prenotazione.getOraFine());
            stm.setInt(4, prenotazione.getId());

            stm.executeUpdate();

            createEvent(prenotazione);
            if (prenotazione.getTipoPrenotazione().equals(TipoPrenotazione.AULA)){
                createEventForStart(prenotazione);
            }

            connection.commit();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "{0}", e);
            try {
                connection.rollback();
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, e.getMessage());
            }
            throw new ViolazioneEntityException(e.getMessage());
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, ex.getMessage());
            }
        }
    }

    @Override
    public List<Prenotazione> retriveAll() {
        final String QUERY = "SELECT * FROM prenotazione";

        List<Prenotazione> ret = new LinkedList<>();
        try {
            PreparedStatement stm = connection.prepareStatement(QUERY);
            stm.execute();

            ResultSet rs = stm.getResultSet();
            while(rs.next()){
                ret.add(getPrenotazioneFromResultSet(rs));
            }
            return ret;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "{0}", e);
            return null;
        }
    }

    private Prenotazione getPrenotazioneFromResultSet(ResultSet rs) throws SQLException {
        AulaDAO aulaDAO = DBAulaDAO.getInstance();
        UtenteDAO utenteDAO = DBUtenteDAO.getInstance();
        Prenotazione ret = new Prenotazione();
        ret.setId(rs.getInt("id"));
        ret.setData(rs.getDate("data"));
        ret.setOraInizio(rs.getTime("ora_inizio"));
        ret.setOraFine(rs.getTime("ora_fine"));
        ret.setTipoPrenotazione(TipoPrenotazione.valueOf(rs.getString("tipo")));
        ret.setAula(aulaDAO.retriveById(rs.getInt("aula")));
        ret.setUtente(utenteDAO.retriveByEmail(rs.getString("utente")));
        return ret;
    }

    private void createEventForStart(Prenotazione prenotazione) throws SQLException {
        dropEvent(prenotazione, OCCUPA);

        final String QUERY_POSTO = "" +
                "CREATE EVENT " + getEventName(prenotazione, OCCUPA) + " " +
                "ON SCHEDULE AT ? " +
                "DO UPDATE Aula SET n_posti_occupati = n_posti_occupati + 1 WHERE id = ?;";

        final String QUERY_AULA = "" +
                "CREATE EVENT " + getEventName(prenotazione, OCCUPA) + " " +
                "ON SCHEDULE AT ? " +
                "DO UPDATE Aula SET n_posti_occupati = n_posti WHERE id = ?;";

        Timestamp eventOccurence = Timestamp.valueOf(String.format("%s %s", prenotazione.getData(), prenotazione.getOraInizio()));

        PreparedStatement stm;
        if (prenotazione.getTipoPrenotazione().equals(TipoPrenotazione.POSTO)) {
            stm  = connection.prepareStatement(QUERY_POSTO);
        } else {
            stm  = connection.prepareStatement(QUERY_AULA);
        }

        stm.setTimestamp(1, eventOccurence);
        stm.setInt(2, prenotazione.getAula().getId());
        stm.execute();
    }

    private void createEvent(Prenotazione prenotazione) throws SQLException {
        dropEvent(prenotazione, PULISCI);

        final String QUERY_POSTO = "" +
                "CREATE EVENT " + getEventName(prenotazione, PULISCI) + " " +
                "ON SCHEDULE AT ? " +
                "DO UPDATE Aula SET n_posti_occupati = n_posti_occupati - 1 WHERE id = ?;";

        final String QUERY_AULA = "" +
                "CREATE EVENT " + getEventName(prenotazione, PULISCI) + " " +
                "ON SCHEDULE AT ? " +
                "DO UPDATE Aula SET n_posti_occupati = 0 WHERE id = ?;";

        Timestamp eventOccurence = Timestamp.valueOf(String.format("%s %s", prenotazione.getData(), prenotazione.getOraFine()));

        PreparedStatement stm;
        if (prenotazione.getTipoPrenotazione().equals(TipoPrenotazione.POSTO)) {
            stm = connection.prepareStatement(QUERY_POSTO);
        } else {
            stm = connection.prepareStatement(QUERY_AULA);
        }

        stm.setTimestamp(1, eventOccurence);
        stm.setInt(2, prenotazione.getAula().getId());
        stm.execute();
    }

    private void dropEvent(Prenotazione prenotazione, String prefix) throws SQLException {
        final String QUERY = "DROP EVENT IF EXISTS " + getEventName(prenotazione, prefix) + ";";

        Statement stm = connection.createStatement();
        stm.execute(QUERY);
    }

    private String getEventName(Prenotazione prenotazione, String prefix) throws SQLException {
        if (prenotazione.getId() == 0) {
            final String QUERY = "SELECT id FROM prenotazione WHERE utente = ? AND aula = ? AND data = ? AND ora_inizio = ? AND" +
                    " ora_fine = ?;";

            PreparedStatement stm = connection.prepareStatement(QUERY);
            stm.setString(1, prenotazione.getUtente().getEmail());
            stm.setInt(2, prenotazione.getAula().getId());
            stm.setDate(3, prenotazione.getData());
            stm.setTime(4, prenotazione.getOraInizio());
            stm.setTime(5, prenotazione.getOraFine());
            stm.execute();
            ResultSet rs = stm.getResultSet();
            if (rs.next())
                return prefix + rs.getInt(1);
            return prefix + 0;
        } else {

            return prefix + prenotazione.getId();
        }
    }

    private Connection connection;

    private static Logger logger = Logger.getLogger(DBAulaDAO.class.getName());
    private static DBPrenotazioneDAO dao;
    private static final String PULISCI = "pulisci";
    private static final String OCCUPA = "occupa";
}
