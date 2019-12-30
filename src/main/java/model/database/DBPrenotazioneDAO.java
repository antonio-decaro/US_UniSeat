package model.database;

import model.dao.*;
import model.pojo.Aula;
import model.pojo.Prenotazione;
import model.pojo.TipoPrenotazione;
import model.pojo.Utente;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
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
    private static Logger logger = Logger.getLogger(DBAulaDAO.class.getName());
    private static DBPrenotazioneDAO dao;

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

    private Connection connection;

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
    public List<Prenotazione> retriveByData(Date data) throws IllegalArgumentException {
        final String QUERY = "SELECT * FROM prenotazione WHERE data = ?";

        if (data.after(Date.valueOf(LocalDate.now()))) // controlla la precondizione
            throw new IllegalArgumentException(String.format("La data %s ancora deve avvenire", data.toString()));

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
    public List<Prenotazione> retriveByDataOra(Date data, Time ora) throws IllegalArgumentException {
        final String QUERY = "SELECT * FROM prenotazione WHERE data=? AND ora_inizio=?";

        if (data.after(Date.valueOf(LocalDate.now())) && ora.after(Time.valueOf(LocalTime.now())))
            throw new IllegalArgumentException(String.format("La data %s ancora deve avvenire", data.toString()));

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
            PreparedStatement stm = connection.prepareStatement(QUERY);
            stm.setString(1, prenotazione.getUtente().getEmail());
            stm.setInt(2, prenotazione.getAula().getId());
            stm.setDate(3, prenotazione.getData());
            stm.setTime(4, prenotazione.getOraInizio());
            stm.setTime(5, prenotazione.getOraFine());
            stm.setString(6, prenotazione.getTipoPrenotazione().toString().toUpperCase());
            stm.executeUpdate();

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "{0}", e);
            throw new ViolazioneEntityException(e.getMessage());
        }
    }

    @Override
    public void delete(Prenotazione prenotazione) {
        final String QUERY = "DELETE FROM prenotazione WHERE id=?";

        if (DBPrenotazioneDAO.getInstance().retriveById(prenotazione.getId()) == null)
            throw new ViolazioneEntityException(String.format("Non esiste la prenotazione %s nel database", prenotazione));

        try {
            PreparedStatement stm = connection.prepareStatement(QUERY);
            stm.setInt(1, prenotazione.getId());
            stm.executeUpdate();

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "{0}", e);
        }
    }

    @Override
    public void update(Prenotazione prenotazione) throws ViolazioneEntityException {
        final String QUERY=
                "UPDATE prenotazione SET data = ?, ora_inizio = ?, ora_fine = ? WHERE id = ?";

        if (retriveById(prenotazione.getId()) == null)
            throw new ViolazioneEntityException("Non esiste alcuna prenotazione con id " + prenotazione.getId());

        try {
            PreparedStatement stm = connection.prepareStatement(QUERY);
            stm.setDate(1, prenotazione.getData());
            stm.setTime(2, prenotazione.getOraInizio());
            stm.setTime(3, prenotazione.getOraFine());
            stm.setInt(4, prenotazione.getId());

            stm.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "{0}", e);
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
        ret.setOraInizio(rs.getTime("oraInizio"));
        ret.setOraFine(rs.getTime("oraFine"));
        ret.setTipoPrenotazione(TipoPrenotazione.valueOf(rs.getString("tipo")));
        ret.setAula(aulaDAO.retriveById(rs.getInt("aula")));
        ret.setUtente(utenteDAO.retriveByEmail(rs.getString("utente")));
        return ret;
    }
}
