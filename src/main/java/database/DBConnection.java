package database;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Questa classe instanzia un oggetto Singleton che verrà condiviso da tutte le componenti che accederanno all DataBase.
 */
public class DBConnection {

    private static Logger logger = Logger.getLogger(DBConnection.class.getName());
    private static Connection dbConnection;

    static {
        try {
            Context ctx = new InitialContext();
            DataSource dataSource = (DataSource) ctx.lookup("jdbc/UniSeatDB");
            dbConnection = dataSource.getConnection();

        } catch(NamingException | SQLException e){
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }
    }

    /**
     * Ritorna un oggetto Connection attraverso il quale sarà possibile interagire con il database.
     *
     * @return la connessione al database.
     */
    public static Connection getInstance() throws SQLException {
        return dbConnection;
    }
}
