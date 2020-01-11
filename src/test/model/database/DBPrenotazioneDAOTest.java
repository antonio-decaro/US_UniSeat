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
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
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
        int id = 26;
        Prenotazione prenotazione = prenotazioneDAO.retriveById(id);
        assertEquals(id, prenotazione.getId());
        assertEquals( Time.valueOf("09:00:00"), prenotazione.getOraInizio());
        assertEquals(Time.valueOf("11:00:00"), prenotazione.getOraFine());
    }

    @Test
    void retriveByData_OK() {
        Date date = Date.valueOf("2019-06-20");
        List<Prenotazione> prenotazioni = prenotazioneDAO.retriveByData(date);
        System.out.println(prenotazioni);
        assertNotNull(date);
        assertEquals(prenotazioni, prenotazioneDAO.retriveByData(date));
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
        Utente u = new Utente("l.capozzoli@studenti.unisa.it", "Lorenzo", "Capozzoli", "Lorenzo1", TipoUtente.STUDENTE);
        List<Prenotazione> prenotazioni = prenotazioneDAO.retriveByUtente(u);
        System.out.println(prenotazioni);
        assertNotNull(prenotazioni);
        assertNotNull(u);
        assertEquals(prenotazioni, prenotazioneDAO.retriveByUtente(u));
    }

//    @Test
//    void insert_OK() {
//
//    }

    @Test
    void delete_OK(){
        Edificio edificio = new Edificio("F");
        Aula aula = new Aula(1, "E1", 0, 100, "", edificio);
        List<Prenotazione> p = prenotazioneDAO.retriveByAula(aula);
        prenotazioneDAO.delete((Prenotazione) p);
    }

    @Test
    void delete_NOK() {
        Prenotazione p = new Prenotazione();
        p.setId(13);
        System.out.println(p);
        assertNotNull(p);
    }

//
//    @Test
//    void update_OK(){
//        int id = 11;
//        Utente utente = new Utente("l.capozzoli@studenti.unisa.it", "Lorenzo", "Capozzoli",
//                "Lorenzo1", TipoUtente.STUDENTE);
//        Edificio edificio = new Edificio("F3");
//        Aula aula = new Aula(id, "P3", 0, 100, "", edificio);
//        Prenotazione prenotazione = new Prenotazione(id, Date.valueOf("2019-05-21"), Time.valueOf("09:00:00"),
//                Time.valueOf("11:00:00"), TipoPrenotazione.POSTO, aula, utente);
//
//        prenotazioneDAO.update(prenotazione);
//        Prenotazione newPrenotazione = prenotazioneDAO.retriveById(11);
//        assertEquals(prenotazione.getId(), newPrenotazione.getId());
//    }

    @Test
    void retriveAll_OK() {
        List<Prenotazione> prenotazioni = prenotazioneDAO.retriveAll();
        System.out.println(prenotazioni);
        assertEquals(prenotazioni, prenotazioneDAO.retriveAll());
    }
}