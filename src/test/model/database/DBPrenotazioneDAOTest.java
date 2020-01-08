package model.database;

import com.mysql.cj.jdbc.MysqlDataSource;
import model.dao.*;
import model.pojo.*;
import org.junit.jupiter.api.*;

import javax.sql.DataSource;
import java.lang.ref.PhantomReference;
import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DBPrenotazioneDAOTest {

    private static DataSource dataSource;
    private static DBConnection dbConnection;
    private PrenotazioneDAO prenotazioneDAO;

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
        dataSource = mysqlDS;
        dbConnection.setDataSource(dataSource);
        DBConnection.getInstance().getConnection().setAutoCommit(false);
    }

    @BeforeEach
    void setUp() throws Exception {
        prenotazioneDAO = DBPrenotazioneDAO.getInstance();
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
        assertThrows(IllegalArgumentException.class, () -> prenotazioneDAO.retriveById(id), expectedMessage);
    }

    @Test
    void retriveById_NULL() {
        int id = 12;
        Prenotazione prenotazione = prenotazioneDAO.retriveById(id);
        assertNull(prenotazione);
    }

    @Test
    void retriveById_OK() {
        int id = 1;
        Prenotazione prenotazione = prenotazioneDAO.retriveById(id);
        System.out.println(id);
        System.out.println(prenotazione);
        assertEquals(id, prenotazione.getId());
        assertNotEquals(2019 - 05 - 12, prenotazione.getData());
        assertNotEquals(prenotazione.getOraInizio(), "09:00:00");
        assertNotEquals("11:00:00", prenotazione.getOraFine());
        assertNotEquals("POSTO", prenotazione.getTipoPrenotazione());
    }

    @Test
    void retriveByData_OK() {
        Date date = Date.valueOf("2019-05-12");
        List<Prenotazione> prenotazioni = prenotazioneDAO.retriveByData(date);
        System.out.println(prenotazioni);
        assertNotNull(date);
        assertEquals(prenotazioni, prenotazioneDAO.retriveByData(date));
        String expectedMessage = "La data " + Date.valueOf(LocalDate.now().plusDays(1)) + " ancora deve avvenire";
        assertThrows(IllegalArgumentException.class, () -> prenotazioneDAO.retriveByData(Date.valueOf(LocalDate.now().plusDays(1))), expectedMessage);
    }

    @Test
    void retriveByData_NULL() {
        Date date = Date.valueOf("2019-06-21");
        List<Prenotazione> prenotazioni = prenotazioneDAO.retriveByData(date);
        System.out.println(prenotazioni);
        assertNotNull(prenotazioni);
    }

    @Test
    void retriveByDataOra_OK() {
        Date date = Date.valueOf("2019-05-12");
        Time time = Time.valueOf("09:00:00");
        List<Prenotazione> prenotazioni = prenotazioneDAO.retriveByDataOra(date, time);
        System.out.println(prenotazioni);
        assertNotNull(date);
        assertNotNull(time);
        assertEquals(prenotazioni, prenotazioneDAO.retriveByDataOra(date, time));
    }

    @Test
    void retriveByDataOra_NULL() {
        Date date = Date.valueOf("2019-06-21");
        Time time = Time.valueOf("09:00:00");
        List<Prenotazione> prenotazioni = prenotazioneDAO.retriveByData(date);
        System.out.println(prenotazioni);
        assertNotNull(date);
        assertNotNull(time);
        assertNotNull(prenotazioni);
    }

    @Test
    void delete_OK() {
        Prenotazione p = prenotazioneDAO.retriveById(1);
        prenotazioneDAO.delete(p);
        assertNotNull(p);
    }

    @Test
    void delete_NOK() {
        Prenotazione p = new Prenotazione();
        p.setId(13);
        System.out.println(p);
        assertThrows(ViolazioneEntityException.class, () -> prenotazioneDAO.delete(p), "Non esiste la prenotazione " + p + " nel database");
    }

    @Test
    void update_OK(){
        Prenotazione prenotazione = prenotazioneDAO.retriveById(2);
        prenotazione.setId(2);
        prenotazione.setTipoPrenotazione(TipoPrenotazione.AULA);
        prenotazioneDAO.update(prenotazione);
        assertEquals(2, prenotazione.getId());
        assertEquals(TipoPrenotazione.AULA, prenotazione.getTipoPrenotazione());
    }

    @Test
    void retriveAll_OK(){
        List<Prenotazione> prenotazioni = prenotazioneDAO.retriveAll();
        System.out.println(prenotazioni);
        assertEquals(prenotazioni, prenotazioneDAO.retriveAll());
    }
}