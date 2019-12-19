package model.database;

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
    private static DBConnection dbConnection;

    static {
        dbConnection = new DBConnection();
        try {
            Context ctx = new InitialContext();
            dbConnection.setDataSource((DataSource) ctx.lookup("jdbc/UniSeatDB"));
        } catch (NamingException e) {
            logger.log(Level.SEVERE, "{0}", e.toString());
        }
    }

    /**
     * Ritorna un oggetto Connection attraverso il quale sarà possibile interagire con il database.
     *
     * @return la connessione al database.
     */
    public static DBConnection getInstance() {
        return dbConnection;
    }

    private Context context;
    private DataSource dataSource;
    private Connection connection;

    private DBConnection(){
    }

    /**
     * Ritorna il DataSource a cui accede.
     *
     * @return il DataSource
     * */
    public DataSource getDataSource(){
        return dataSource;
}

    /**
     * Setta il DataSource a cui accedere
     *
     * @param dataSource il DataSource a cui accedere.
     * */
    public void setDataSource(DataSource dataSource){
        this.dataSource = dataSource;
        resetConnection();
    }

    /**
     * Ritorna la connessione al database
     *
     * @return la connessiona al database
     */
    public Connection getConnection() {
        try {
            if (this.dataSource == null){
                logger.log(Level.SEVERE, "Devi settare il DataSource prima di accedere ad una connessione.");
                return null;
            }
            if (connection == null) {
                connection = this.dataSource.getConnection();
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "{0}", e);
        }
        return connection;
    }

    private void resetConnection(){
        try {
            if (connection != null)
                this.connection.close();
        } catch (SQLException e) {
            logger.log(Level.WARNING, "{0}", e);
        }
        this.connection = null;
    }
}
