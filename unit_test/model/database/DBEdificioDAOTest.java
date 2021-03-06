package model.database;

import com.mysql.cj.jdbc.MysqlDataSource;
import model.dao.EdificioDAO;
import model.pojo.Edificio;
import org.junit.jupiter.api.*;

import javax.sql.DataSource;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DBEdificioDAOTest {
    private EdificioDAO edificioDAO;
    private static DBConnection dbConnection;

    @BeforeAll
    static void init() throws Exception {
        dbConnection = DBConnection.getInstance();
        MysqlDataSource mysqlDS = new MysqlDataSource();
        mysqlDS.setURL("jdbc:mysql://localhost:3306/UniSeatDB");
        mysqlDS.setUser("root");
        mysqlDS.setPassword("toor");
        mysqlDS.setServerTimezone("CET");
        mysqlDS.setVerifyServerCertificate(false);
        mysqlDS.setUseSSL(false);
        dbConnection.setDataSource(mysqlDS);
        dbConnection.getConnection().setAutoCommit(false);
    }

    @BeforeEach
    void setUp() throws Exception {
        edificioDAO = DBEdificioDAO.getInstance();
    }

    @AfterEach
    void tearDown() throws Exception {
        dbConnection.getConnection().rollback();
    }

    @AfterAll
    static void reset() throws Exception {
        dbConnection.getConnection().setAutoCommit(true);
    }

    @Test
    void retriveByName_OK() {
        String name = "F2";
        Edificio edificio = edificioDAO.retriveByName(name);
        assertNotNull(edificio);
        assertEquals(name, edificio.getNome());
    }

    @Test
    void retriveByName_NOK() {
        String name = "";
        Edificio edificio = null;
        String msg = null;
        assertThrows(IllegalArgumentException.class, ()-> edificioDAO.retriveByName(name), "Nome non valido");
    }

    @Test
    void retriveByName_NULL() {
        String name = "EDIFICIO";
        Edificio edificio = edificioDAO.retriveByName(name);
        assertNull(edificio);
    }

    @Test
    void retriveAll_OK() {
        List<Edificio> edificio = edificioDAO.retriveAll();
        System.out.println(edificio);
        assertEquals(edificio, edificioDAO.retriveAll());
    }
}