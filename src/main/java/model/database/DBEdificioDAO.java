package model.database;

import model.dao.EdificioDAO;
import model.pojo.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Questa classe implementa l'interfaccia EdificioDAO, utilizzando come gestore della persistenza il DataBase
 * @author Spinelli Gianluca
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
        final String QUERY = "SELECT * FROM edificio WHERE nome = ?";

        try {
            PreparedStatement stm = connection.prepareStatement(QUERY);
            stm.setString(1, nome);
            stm.execute();

            ResultSet rs = stm.getResultSet();
            if (!rs.next())
                return null;
            return getEdificioFromResultSet(rs);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "{0}", e);
            return null;
        }
    }

    @Override
    public List<Edificio> retriveAll() {
        final String QUERY = "SELECT * FROM edificio";

        List<Edificio> ret = new LinkedList<>();
        try {
            PreparedStatement stm = connection.prepareStatement(QUERY);
            stm.execute();

            ResultSet rs = stm.getResultSet();
            while(rs.next()){
                ret.add(getEdificioFromResultSet(rs));
            }
            return ret;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "{0}", e);
            return null;
        }
    }

    private Edificio getEdificioFromResultSet(ResultSet rs) throws SQLException {
        Edificio ret = new Edificio();
        ret.setNome(rs.getString("nome"));
        ret.setAule(getAule(ret));
        return ret;
    }

    private Set<Aula> getAule(Edificio edificio){
        final String QUERY = "SELECT * FROM aula WHERE edificio = ?";

        Set<Aula> ret = new HashSet<>();
        try {
            PreparedStatement stm = connection.prepareStatement(QUERY);
            stm.setString(1, edificio.getNome());
            stm.execute();

            ResultSet rs = stm.getResultSet();
            while(rs.next()){
                ret.add(getAulaFromResultSet(rs, edificio));
            }
            return ret;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "{0}", e);
            return null;
        }
    }

    private Aula getAulaFromResultSet(ResultSet rs, Edificio edificio) throws SQLException {
        Aula a = new Aula();
        a.setId(rs.getInt("id"));
        a.setNome(rs.getString("nome"));
        a.setEdificio(edificio);
        a.setPosti(rs.getInt("n_posti"));
        a.setDisponibilita(rs.getString("disponibilita"));
        a.setPostiOccupati(rs.getInt("n_posti_occupati"));
        ArrayList<Servizio> servizi = new ArrayList<>();
        for (String s : rs.getString("servizi").split(";"))
            servizi.add(Servizio.valueOf(s));
        a.setServizi(servizi);
        return a;
    }
}
