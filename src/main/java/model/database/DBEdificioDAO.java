package model.database;

import model.dao.AulaDAO;
import model.dao.EdificioDAO;
import model.pojo.Aula;
import model.pojo.Edificio;
import model.pojo.TipoUtente;
import model.pojo.Utente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
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

    private Edificio getEdificioFromResultSet(ResultSet rs) throws SQLException {
        AulaDAO aulaDAO = DBAulaDAO.getInstance();
        Edificio ret = new Edificio();
        ret.setNome(rs.getString("nome"));
        ret.setAule(aulaDAO.retriveByEdificio(retriveByName(rs.getString("nome"))));
        return ret;
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
}
