package model.database;

import com.mysql.cj.jdbc.MysqlDataSource;
import model.dao.AulaDAO;
import model.pojo.Aula;
import org.junit.jupiter.api.*;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DBAulaDAOTest {

    private static DataSource dataSource;
    private static DBConnection dbConnection;
    private AulaDAO aulaDAO;

    @BeforeAll
    static void init() throws Exception{
        dbConnection = DBConnection.getInstance();
        MysqlDataSource mysqlDS = new MysqlDataSource();
        mysqlDS.setURL("jdbc:mysql://localhost:3306/UniSeatDB");
        mysqlDS.setUser("root");
        mysqlDS.setPassword("toor");
        mysqlDS.setServerTimezone("CET");
        mysqlDS.setVerifyServerCertificate(false);
        mysqlDS.setUseSSL(false);
        dataSource = mysqlDS;
        dbConnection.setDataSource(dataSource);
    }

    @BeforeEach
    void setUp() throws Exception {

        aulaDAO = DBAulaDAO.getInstance();
        DBConnection.getInstance().getConnection().setAutoCommit(false);
    }

    @AfterEach
    void tearDown() throws Exception {
        DBConnection.getInstance().getConnection().rollback();
    }

    @Test
    void retriveById() {
        Aula aula = aulaDAO.retriveById(1);
        System.out.println(aula);
        assertEquals(50, aula.getPosti());
    }

    @Test
    void update() {

    }

    @Test
    void insert() {

    }

    @Test
    void retriveAll() {
    }

    @Test
    void retriveByEdificio() {
    }
}