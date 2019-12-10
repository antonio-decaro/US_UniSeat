package model.database;

import java.sql.Connection;

/**
 * Questa classe instanzia un oggetto Singleton che verrà condiviso da tutte le componenti che accederanno all DataBase.
 * */
public class DBConnection {

    private static Connection dbConnection;

    static {
        //TODO instanziazione oggetto dbConnection
    }

    /**
     * Ritorna l'oggetto singleton Connection attraverso il quale sarà possibile interrogare il database.
     * @return la connessione al database.
     * */
    public static Connection getInstance(){
        return dbConnection;
    }
}
