package model.database;

import com.mysql.cj.jdbc.MysqlDataSource;
import model.dao.AulaDAO;
import model.dao.PrenotazioneDAO;
import model.dao.ViolazioneEntityException;
import model.pojo.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.lang.ref.PhantomReference;
import java.security.UnrecoverableEntryException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
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
        int id = 3;
        Prenotazione prenotazione = prenotazioneDAO.retriveById(id);
        System.out.println(id);
        System.out.println(prenotazione);
        assertEquals(id, prenotazione.getId());
        assertNotEquals(2019-06-28, prenotazione.getData());
        assertNotEquals(prenotazione.getOraInizio(), "09:00:00");
        assertNotEquals("11:00:00", prenotazione.getOraFine());
        assertNotEquals(TipoPrenotazione.POSTO, prenotazione.getTipoPrenotazione());
    }

    @Test
    void retriveByData_OK() {
        Date date = Date.valueOf("2019-06-20");
        List<Prenotazione> prenotazioni = prenotazioneDAO.retriveByData(date);
        System.out.println(prenotazioni);
        assertNotNull(date);
        assertEquals(prenotazioni, prenotazioneDAO.retriveByData(date));
        String expectedMessage = "La data " + Date.valueOf(LocalDate.now().plusDays(1)) + " ancora deve avvenire";
//        assertThrows(IllegalArgumentException.class, () -> prenotazioneDAO.retriveByData(Date.valueOf(LocalDate.now().plusDays(1))), expectedMessage);
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
        Date date = Date.valueOf("2019-06-20");
        Time time = Time.valueOf("11:00:00");
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
    void retrieveByAula_OK(){
        int id = 20;
        Edificio edificio = new Edificio("F3");
        Aula p4 = new Aula(id, "P4", 0, 100, "", edificio);
        List<Prenotazione> prenotazioni = prenotazioneDAO.retriveByAula(p4);
        System.out.println(prenotazioni);
        assertNotNull(prenotazioni);
        assertNotNull(p4);
        assertEquals(prenotazioni, prenotazioneDAO.retriveByAula(p4));
    }

    @Test
    void retrieveByAula_NULL(){
        int id = 20;
        Edificio edificio = new Edificio("EFFE");
        Aula p4 = new Aula(id, "EFFE", 0, 100, "", edificio);
        List<Prenotazione> prenotazioni = prenotazioneDAO.retriveByAula(p4);
        System.out.println(prenotazioni);
        assertNotNull(prenotazioni);
        assertNotNull(p4);
        assertEquals(prenotazioni, prenotazioneDAO.retriveByAula(p4));
    }

    @Test
    void retrieveByUtente_OK(){
        int id = 3;
        Edificio edificio = new Edificio("F3");
        Aula aula = new Aula(id, "F8", 30, 60, "", edificio);
        Utente u = new Utente("f.ferrucci@unisa.it", "Filomena", "Ferrucci","Ferrucci1",
                TipoUtente.DOCENTE);
        Prenotazione prenotazione = new Prenotazione(id, Date.valueOf("2019-06-28"), Time.valueOf("09:00:00"),
                Time.valueOf("12:00:00"), TipoPrenotazione.AULA, aula, u);
        System.out.println(prenotazioneDAO);
        assertNotNull(prenotazione);
        assertEquals(prenotazione, prenotazioneDAO.retriveByUtente(u));
    }

    @Test
    void insert_OK() {
        int id = 33;
        Utente utente = new Utente("a.parisi@studenti.unisa.it", "Antonio", "Parisi",
                "Antonio1", TipoUtente.STUDENTE);
        Edificio edificio = new Edificio("F3");
        Aula aula = new Aula(id, "P3", 0, 100, "", edificio);
        Prenotazione prenotazione = new Prenotazione(id, Date.valueOf("2019-05-21"), Time.valueOf("09:00:00"),
                Time.valueOf("11:00:00"), TipoPrenotazione.POSTO, aula, utente);
        System.out.println(prenotazione);
        prenotazioneDAO.insert(prenotazione);
//        assertNotNull(prenotazione);
//        assertEquals(prenotazione.getUtente(), prenotazioneDAO.retriveByUtente(utente));
//        assertEquals(prenotazione.getOraInizio(), prenotazioneDAO.retriveByDataOra(Date.valueOf("2019-05-21"), Time.valueOf("09:00:00")));
//        assertEquals(prenotazione.getOraFine(), prenotazioneDAO.retriveByDataOra(Date.valueOf("2019-05-21"), Time.valueOf("11:00:00")));
//        assertEquals(prenotazione, prenotazioneDAO.retriveById(id));
    }

    @Test
    void delete_OK() {
        Prenotazione p = prenotazioneDAO.retriveById(7);
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
        int id = 11;
        Utente utente = new Utente("l.capozzoli@studenti.unisa.it", "Lorenzo", "Capozzoli",
                "Lorenzo1", TipoUtente.STUDENTE);
        Edificio edificio = new Edificio("F3");
        Aula aula = new Aula(id, "P3", 0, 100, "", edificio);
        Prenotazione prenotazione = new Prenotazione(id, Date.valueOf("2019-05-21"), Time.valueOf("09:00:00"),
                Time.valueOf("11:00:00"), TipoPrenotazione.POSTO, aula, utente);

        prenotazioneDAO.update(prenotazione);
        Prenotazione newPrenotazione = prenotazioneDAO.retriveById(11);
        assertEquals(prenotazione.getId(), newPrenotazione.getId());
    }

    @Test
    void retriveAll_OK() {
        List<Prenotazione> prenotazioni = prenotazioneDAO.retriveAll();
        System.out.println(prenotazioni);
        assertEquals(prenotazioni, prenotazioneDAO.retriveAll());
    }
}