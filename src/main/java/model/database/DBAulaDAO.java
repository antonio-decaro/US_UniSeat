package model.database;

import model.dao.AulaDAO;
import model.dao.EdificioDAO;
import model.dao.UtenteDAO;
import model.dao.ViolazioneEntityException;
import model.pojo.Aula;
import model.pojo.Edificio;
import model.pojo.Servizio;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
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
            dao = new DBAulaDAO(DBConnection.getInstance().getConnection());
        }
        return dao;
    }

    private DBAulaDAO(Connection connection) {
        this.connection = connection;
    }

    private Connection connection;

    @Override
    public Aula retriveById(int id) {
        final String QUERY = "SELECT * FROM aula WHERE id = ?";

        if (id < 0)
            throw new IllegalArgumentException(String.format("L'id %d non è valido.", id));

        try {
            PreparedStatement stm = connection.prepareStatement(QUERY);
            stm.setInt(1, id);
            stm.execute();

            ResultSet rs = stm.getResultSet();
            if (!rs.next())
                return null;
            return getAulaFromResultSet(rs);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "{0}", e);
            return null;
        }
    }

    @Override
    public void update(Aula aula) throws ViolazioneEntityException {
        final String QUERY="UPDATE aula SET id = ?, nome = ?, edificio = ?,n_posti = ?,n_posti_occupati = ?,servizi = ?,disponiblita = ? WHERE id = ?";
        if(DBEdificioDAO.getInstance().retriveByName(aula.getEdificio().getNome()) == null)
            throw new ViolazioneEntityException(String.format("Non esiste l'edificio %s nel database",aula.getEdificio().getNome()));
        try {
            PreparedStatement stm = connection.prepareStatement(QUERY);

            stm.setInt(1,aula.getId());
            stm.setString(2,aula.getNome());
            stm.setString(3,aula.getEdificio().getNome());
            stm.setInt(4,aula.getPosti());
            stm.setInt(5,aula.getPostiOccupati());
            StringBuilder servizi_db = new StringBuilder();
            for (Servizio s : aula.getServizi()){
                servizi_db.append(s.name());
                servizi_db.append(";");
            }
            stm.setString(6,servizi_db.toString());
            stm.setString(7,aula.getDisponibilita());
            stm.setInt(8,aula.getId());
            stm.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "{0}", e);
        }

    }

    @Override
    public void insert(Aula aula) throws ViolazioneEntityException {
        final String QUERY = "INSERT INTO aula(id,nome,edificio,n_posti,n_posti_occupati,servizi,disponiblita)  " +
                "VALUES (?, ?, ?, ?, ?, ?,?)";

        try {
            PreparedStatement stm = connection.prepareStatement(QUERY);
            stm.setInt(1, aula.getId());
            stm.setString(2, aula.getNome());
            stm.setString(3, aula.getEdificio().getNome());
            stm.setInt(4, aula.getPosti());
            stm.setInt(5, aula.getPostiOccupati());
            StringBuilder servizi_db = new StringBuilder();
            for (Servizio s : aula.getServizi()){
                servizi_db.append(s.name());
                servizi_db.append(";");
            }
            stm.setString(6,servizi_db.toString());
            stm.setString(7,aula.getDisponibilita());
            stm.executeUpdate();

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "{0}", e);
            throw new ViolazioneEntityException(e.getMessage());
        }
    }

    @Override
    public Set<Aula> retriveAll() {
        final String QUERY = "SELECT * FROM aula";

        Set<Aula> ret = new HashSet<>();
        try {
            PreparedStatement stm = connection.prepareStatement(QUERY);
            stm.execute();

            ResultSet rs = stm.getResultSet();
            while(rs.next()){
                ret.add(getAulaFromResultSet(rs));
            }
            return ret;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "{0}", e);
            return null;
        }
    }

    @Override
    public Set<Aula> retriveByEdificio(Edificio edificio) throws ViolazioneEntityException{
        final String QUERY = "SELECT * FROM aula WHERE edificio = ?";

        if(DBEdificioDAO.getInstance().retriveByName(edificio.getNome()) == null)
            throw new ViolazioneEntityException(String.format("Non esiste l'edificio %s nel database",edificio.toString()));

        Set<Aula> ret = new HashSet<>();
        try {
            PreparedStatement stm = connection.prepareStatement(QUERY);
            stm.setString(1, edificio.getNome());
            stm.execute();

            ResultSet rs = stm.getResultSet();
            while(rs.next()){
                ret.add(getAulaFromResultSet(rs));
            }
            return ret;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "{0}", e);
            return null;
        }
    }

    private Aula getAulaFromResultSet(ResultSet rs) throws SQLException {
        EdificioDAO edificioDAO = DBEdificioDAO.getInstance();
        Aula a = new Aula();
        a.setId(rs.getInt("id"));
        a.setNome(rs.getString("nome"));
        a.setEdificio(edificioDAO.retriveByName(rs.getString("edificio")));
        a.setPosti(rs.getInt("n_posti"));
        a.setDisponibilita(rs.getString("disponiblita"));
        a.setPostiOccupati(rs.getInt("n_posti_occupati"));
        ArrayList<Servizio> servizi = new ArrayList<>();
        for (String s : rs.getString("servizi").split(";"))
            servizi.add(Servizio.valueOf(s));
        a.setServizi(servizi);
        return a;
    }
}
