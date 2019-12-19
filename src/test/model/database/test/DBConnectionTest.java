package model.database.test;

import com.mysql.cj.jdbc.MysqlDataSource;
import model.database.DBConnection;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class DBConnectionTest {

    private static DataSource dataSource;
    private DBConnection dbConnection;

    @BeforeAll
    static void init() throws Exception{
        MysqlDataSource mysqlDS = new MysqlDataSource();
        mysqlDS.setURL("jdbc:mysql://localhost:3306/UniSeatDB");
        mysqlDS.setUser("root");
        mysqlDS.setPassword("toor");
        mysqlDS.setServerTimezone("CET");
        mysqlDS.setVerifyServerCertificate(false);
        mysqlDS.setUseSSL(false);
        dataSource = mysqlDS;
    }

    @BeforeEach
    void setUp() {
        dbConnection = DBConnection.getInstance();
        dbConnection.setDataSource(dataSource);
    }

    @Test
    void testConnection(){
        assertNotNull(dbConnection.getConnection());
    }

    @Test
    void testStatement() throws Exception {
        Connection conn = dbConnection.getConnection();
        Statement stm = conn.createStatement();
        assertNotNull(stm);
    }

    @Test
    void testQuery() throws Exception {
        Connection conn = dbConnection.getConnection();
        Statement stm = conn.createStatement();
        stm.execute("SELECT 10 + 5");

        ResultSet rs = stm.getResultSet();
        assertTrue(rs.next());
        assertEquals(15, rs.getInt(1));
    }
}