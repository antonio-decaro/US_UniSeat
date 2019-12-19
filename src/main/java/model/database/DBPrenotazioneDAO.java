package model.database;

import model.dao.EdificioDAO;
import model.dao.PrenotazioneDAO;
import model.dao.ViolazioneEntityException;
import model.pojo.Aula;
import model.pojo.Prenotazione;
import model.pojo.TipoPrenotazione;
import model.pojo.Utente;

import java.sql.*;
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
            try {
                dao = new DBPrenotazioneDAO(DBConnection.getInstance());
            } catch (SQLException e){
                logger.log(Level.SEVERE, "{0}", e);
            }
        }
        return dao;
    }

    private DBPrenotazioneDAO(Connection connection) throws SQLException {
        this.connection = DBConnection.getInstance();
    }

    private Connection connection;

    @Override
    public Prenotazione retriveById(int id) throws IllegalArgumentException {
        final String QUERY = "SELECT * FROM prenotazione WHERE id = ?";

        try {
            PreparedStatement stm = connection.prepareStatement(QUERY);
            stm.setInt(1, id);
            stm.execute();

            ResultSet rs = stm.getResultSet();
            if (!rs.next())
                throw new IllegalArgumentException(String.format("L'id %d non corrisponde a nessuna prenotazione.", id));

            return getAulaFromResultSet(rs);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "{0}", e);
            return null;
        }
    }

    @Override
    public List<Prenotazione> retriveByData(Date data) throws IllegalArgumentException {
        return null;
        //TODO implement
    }

    @Override
    public List<Prenotazione> retriveByDataOra(Date data, Time ora) throws IllegalArgumentException {
        return null;
        //TODO implement
    }

    @Override
    public List<Prenotazione> retriveByUtente(Utente utente) throws ViolazioneEntityException {
        return null;
        //TODO implement
    }

    @Override
    public List<Prenotazione> retriveByAula(Aula aula) throws ViolazioneEntityException {
        return null;
        //TODO implement
    }

    @Override
    public void insert(Prenotazione prenotazione) throws ViolazioneEntityException {
        //TODO implement
    }

    @Override
    public void delete(Prenotazione prenotazione) {
        //TODO implement
    }

    @Override
    public List<Prenotazione> retriveAll() {
        //TODO implement
        return null;
    }

    private Prenotazione getAulaFromResultSet(ResultSet rs) throws SQLException {
        Prenotazione ret = new Prenotazione();
        ret.setId(rs.getInt("id"));
        ret.setData(rs.getDate("data"));
        ret.setOraInizio(rs.getTime("oraInizio"));
        ret.setOraFine(rs.getTime("oraFine"));
        ret.setTipoPrenotazione(TipoPrenotazione.valueOf(rs.getString("tipo")));
        ret.setAula(DBAulaDAO.getInstance().retriveById(rs.getInt("aula")));
        ret.setUtente(DBUtenteDAO.getInstance().retriveByEmail(rs.getString("utente")));

        return ret;
    }
}
