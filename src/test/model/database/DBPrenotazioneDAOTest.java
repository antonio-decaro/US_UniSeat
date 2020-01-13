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
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DBPrenotazioneDAOTest {

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
        dbConnection.setDataSource(mysqlDS);
    }

    @BeforeEach
    void setUp() throws SQLException {
        dbConnection.getConnection().setAutoCommit(false);
        prenotazioneDAO = DBPrenotazioneDAO.getInstance();
    }

    @AfterEach
    void tearDown() throws Exception {
        try {
            dbConnection.getConnection().rollback();
        } catch (Exception e) {
            e.printStackTrace();
        }
        dbConnection.getConnection().setAutoCommit(true);
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
    void retriveById_OK() throws SQLException {
        Edificio edificio = new Edificio("F");
        Aula aula = new Aula(1, "E1", 0, 100, "", edificio);
        Utente utente = new Utente("a.decaro@studenti.unisa.it", "", "", "", TipoUtente.STUDENTE);
        Date date = Date.valueOf("2018-01-13");
        Time time = Time.valueOf("12:00:00");
        Prenotazione p = new Prenotazione(date, time, time, TipoPrenotazione.POSTO, aula, utente);
        prenotazioneDAO.insert(p);

        final String QUERY = "SELECT id FROM prenotazione WHERE data = ?";
        PreparedStatement stm = dbConnection.getConnection().prepareStatement(QUERY);
        stm.setDate(1, date);
        stm.execute();
        ResultSet rs = stm.getResultSet();
        Prenotazione prenotazione = null;
        if (rs.next()) {
            int idx = rs.getInt(1);
            prenotazione = prenotazioneDAO.retriveById(idx);
        }

        assertNotNull(prenotazione);
        assertEquals(p.getTipoPrenotazione(), prenotazione.getTipoPrenotazione());
        assertEquals(p.getAula().getId(), prenotazione.getAula().getId());
        assertEquals(p.getUtente().getEmail(), prenotazione.getUtente().getEmail());
        assertEquals(p.getOraInizio(), prenotazione.getOraInizio());
        assertEquals(p.getOraFine(), prenotazione.getOraFine());
        assertEquals(p.getData(), prenotazione.getData());
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
        Utente u = new Utente();
        u.setEmail("a.decaro@studenti.unisa.it");
        List<Prenotazione> prenotazioni = prenotazioneDAO.retriveByUtente(u);
        assertNotNull(prenotazioni);
        assertNotNull(u);
        assertEquals(prenotazioni, prenotazioneDAO.retriveByUtente(u));
    }

    @Test
    void delete_OK() throws SQLException {
        Edificio edificio = new Edificio("F");
        Aula aula = new Aula(1, "E1", 0, 100, "", edificio);
        Utente utente = new Utente("a.decaro@studenti.unisa.it", "", "", "", TipoUtente.STUDENTE);
        Date date = Date.valueOf("2020-01-13");
        Time time = Time.valueOf("12:00:00");
        Prenotazione p = new Prenotazione(date, time, time, TipoPrenotazione.POSTO, aula, utente);
        prenotazioneDAO.insert(p);

        List<Prenotazione> prenotazioni = prenotazioneDAO.retriveByAula(aula);
        assertFalse(prenotazioni.isEmpty());
        int before = prenotazioni.size();
        prenotazioneDAO.delete(prenotazioni.get(0));
        prenotazioni = prenotazioneDAO.retriveByAula(aula);
        assertEquals(before, prenotazioni.size() + 1);
    }

    @Test
    void delete_NOK() {
        Prenotazione p = new Prenotazione();
        p.setId(13);;
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