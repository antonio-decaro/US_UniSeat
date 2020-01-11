package model.database;

import com.mysql.cj.jdbc.MysqlDataSource;
import model.dao.AulaDAO;
import model.dao.ViolazioneEntityException;
import model.pojo.Aula;
import model.pojo.Edificio;
import model.pojo.Servizio;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
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
        assertEquals(50, aula.getPosti());
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
        String name = "E1";
        Aula aula = aulaDAO.retriveByName(name);
        System.out.println(aula);
        assertEquals(name, aula.getNome());
        assertEquals(50, aula.getPosti());
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
    void insert_NOK() {
        Edificio edificio = aulaDAO.retriveById(21).getEdificio();
        Aula p4 = new Aula(1, "P4", 0, 100, "", edificio);
        p4.setServizi(new ArrayList<>());

        String expectedMessage = "Aula già esistente!";
        assertThrows(ViolazioneEntityException.class, () -> aulaDAO.insert(p4), expectedMessage);
    }

    @Test
    void insert_OK() {
        int id = 31;
        Edificio edificio = aulaDAO.retriveById(21).getEdificio();
        Aula p3 = new Aula(id,"P3", 0, 100, "", edificio);
        p3.setServizi(new ArrayList<>());
        aulaDAO.insert(p3);
        assertEquals(p3, aulaDAO.retriveById(id));
    }

    @Test
    void insert_OK2() {
        int id = 31;
        ArrayList<Servizio> s = new ArrayList<>();
        Edificio edificio = aulaDAO.retriveById(21).getEdificio();
        Aula p3 = new Aula(id,"P3", 0, 100, "", edificio);
        s.add(Servizio.PRESE);
        p3.setServizi(s);
        aulaDAO.insert(p3);
        assertEquals(p3, aulaDAO.retriveById(id));
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