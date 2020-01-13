package model.database;

import com.mysql.cj.jdbc.MysqlDataSource;
import model.dao.AulaDAO;
import model.dao.EdificioDAO;
import model.dao.ViolazioneEntityException;
import model.pojo.Aula;
import model.pojo.Edificio;
import model.pojo.Servizio;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DBAulaDAOTest {

    private AulaDAO aulaDAO;

    @BeforeAll
    static void init() throws Exception {
        DBConnection dbConnection = DBConnection.getInstance();
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
        aulaDAO = DBAulaDAO.getInstance();
    }

    @AfterEach
    void tearDown() throws Exception {
        DBConnection.getInstance().getConnection().rollback();
    }

    @AfterAll
    static void reset() throws Exception {
        DBConnection.getInstance().getConnection().setAutoCommit(true);
    }

    @Test
    void retriveById_NOK() {
        int id = -1;
        String expectedMessage = "L'id " + id + " non è valido.";
        assertThrows(IllegalArgumentException.class, () -> aulaDAO.retriveById(id), expectedMessage);
    }

    @Test
    void retriveById_NULL() {
        int id = 800;
        Aula aula = aulaDAO.retriveById(id);
        assertNull(aula);
    }

    @Test
    void retriveById_OK() {
        int id = 1;
        Aula aula = aulaDAO.retriveById(id);
        System.out.println(aula);
        assertEquals(id, aula.getId());
        assertEquals(200, aula.getPosti());
    }

    @Test
    void retriveByName_NOK() {
        String name = "";
        String expectedMessage = "Nome non valido.";
        assertThrows(IllegalArgumentException.class, () -> aulaDAO.retriveByName(name), expectedMessage);
    }

    @Test
    void retriveByName_NULL() {
        String name = "FFFF";
        Aula aula = aulaDAO.retriveByName(name);
        assertNull(aula);
    }

    @Test
    void retriveByName_OK() {
        String name = "P1";
        Aula aula = aulaDAO.retriveByName(name);
        assertNotNull(aula);
        assertEquals(name, aula.getNome());
        assertEquals(200, aula.getPosti());
    }

    @Test
    void update_NOK() {
        Aula aula = aulaDAO.retriveById(10000);

        String expectedMessage = "Aula non esistente!";
        assertThrows(ViolazioneEntityException.class, () -> aulaDAO.update(aula), expectedMessage);
    }

    @Test
    void update_OK() {
        Aula aula = aulaDAO.retriveById(1);
        aula.setPosti(80);
        aula.setPostiOccupati(40);
        aulaDAO.update(aula);
        assertEquals(80, aula.getPosti());
        assertEquals(40, aula.getPostiOccupati());
    }

    @Test
    void insert_NOK() throws SQLException {
        Edificio edificio = aulaDAO.retriveById(21).getEdificio();
        Aula a = new Aula(1001, "Aula", 0, 100, "", edificio);
        a.setServizi(new ArrayList<>());
        Connection conn = DBConnection.getInstance().getConnection();
        PreparedStatement stm = conn.prepareStatement("INSERT INTO " +
                "aula(id, nome, edificio, n_posti, n_posti_occupati, servizi, disponibilita) " +
                "VALUE (?, ?, ?, ?, ?, ?, ?)");
        stm.setInt(1, a.getId());
        stm.setString(2, a.getNome());
        stm.setString(3, a.getEdificio().getNome());
        stm.setInt(4, a.getPosti());
        stm.setInt(5, a.getPostiOccupati());
        stm.setString(6, a.getServizi().toString());
        stm.setString(7, "");
        stm.execute();

        String expectedMessage = "Aula già esistente";
        assertThrows(ViolazioneEntityException.class, () -> aulaDAO.insert(a), expectedMessage);
    }

    @Test
    void insert_OK() throws SQLException {
        final int ID = 1001;
        Edificio edificio = aulaDAO.retriveById(21).getEdificio();
        Aula expected = new Aula(ID,"Aula", 0, 100, "", edificio);
        expected.setServizi(new ArrayList<>());
        aulaDAO.insert(expected);

        final String QUERY = "SELECT * FROM aula WHERE nome = ?";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(QUERY);
        stm.setString(1, expected.getNome());
        stm.execute();
        EdificioDAO edificioDAO = DBEdificioDAO.getInstance();
        Aula a = null;
        ResultSet rs = stm.getResultSet();
        if (rs.next()) {
            a = new Aula();
            a.setId(rs.getInt("id"));
            a.setNome(rs.getString("nome"));
            a.setEdificio(edificioDAO.retriveByName(rs.getString("edificio")));
            a.setPosti(rs.getInt("n_posti"));
            a.setDisponibilita(rs.getString("disponibilita"));
            a.setPostiOccupati(rs.getInt("n_posti_occupati"));
            ArrayList<Servizio> servizi = new ArrayList<>();
            String strServizi = rs.getString("servizi");
            if (strServizi != null && !strServizi.equals("")) {
                for (String s : rs.getString("servizi").split(";"))
                    servizi.add(Servizio.valueOf(s));
            }
            a.setServizi(servizi);
        }

        assertNotNull(a);
        assertEquals(expected.getNome(), a.getNome());
        assertEquals(expected.getPostiOccupati(), a.getPostiOccupati());
        assertEquals(expected.getPosti(), a.getPosti());
        assertEquals(expected.getDisponibilita(), a.getDisponibilita());
        assertEquals(expected.getServizi(), a.getServizi());
    }

    @Test
    void insert_OK2() throws SQLException {
        ArrayList<Servizio> servizi = new ArrayList<>();
        Edificio edificio = aulaDAO.retriveById(21).getEdificio();
        Aula expected = new Aula("P3", 0, 100, "", edificio);
        servizi.add(Servizio.PRESE);
        expected.setServizi(servizi);
        aulaDAO.insert(expected);

        final String QUERY = "SELECT * FROM aula WHERE nome = ?";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(QUERY);
        stm.setString(1, expected.getNome());
        stm.execute();
        EdificioDAO edificioDAO = DBEdificioDAO.getInstance();
        Aula a = null;
        ResultSet rs = stm.getResultSet();
        if (rs.next()) {
            a = new Aula();
            a.setId(rs.getInt("id"));
            a.setNome(rs.getString("nome"));
            a.setEdificio(edificioDAO.retriveByName(rs.getString("edificio")));
            a.setPosti(rs.getInt("n_posti"));
            a.setDisponibilita(rs.getString("disponibilita"));
            a.setPostiOccupati(rs.getInt("n_posti_occupati"));
            ArrayList<Servizio> serv = new ArrayList<>();
            String strServizi = rs.getString("servizi");
            if (strServizi != null && !strServizi.equals("")) {
                for (String s : rs.getString("servizi").split(";"))
                    serv.add(Servizio.valueOf(s));
            }
            a.setServizi(serv);
        }

        assertNotNull(a);
        assertEquals(expected.getNome(), a.getNome());
        assertEquals(expected.getPostiOccupati(), a.getPostiOccupati());
        assertEquals(expected.getPosti(), a.getPosti());
        assertEquals(expected.getDisponibilita(), a.getDisponibilita());
        assertEquals(expected.getServizi(), a.getServizi());
    }

    @Test
    void retriveAll_OK() {
        Set<Aula> aule = aulaDAO.retriveAll();
        System.out.println(aule);
        assertEquals(aule, aulaDAO.retriveAll());
    }

    @Test
    void retriveByEdificio_NULL() {
        String msg;
        Edificio edificio = new Edificio("F42");

        String expectedMessage = "Non esiste l'edificio " + edificio + " nel database";
        assertThrows(ViolazioneEntityException.class, () -> aulaDAO.retriveByEdificio(edificio), expectedMessage);

    }

    @Test
    void retriveByEdificio_OK() {
        Edificio edificio = new Edificio("F3");
        Set<Aula> aule = aulaDAO.retriveByEdificio(edificio);
        System.out.println(aule);
        assertEquals(aule, aulaDAO.retriveByEdificio(edificio));
    }
}