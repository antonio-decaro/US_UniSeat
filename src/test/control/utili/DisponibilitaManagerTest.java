package control.utili;

import model.dao.PrenotazioneDAO;
import model.daostub.StubPrenotazioneDAO;
import model.pojo.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.Time;

import static org.junit.jupiter.api.Assertions.*;

class DisponibilitaManagerTest {

    private PrenotazioneDAO prenotazioneDAO;
    private static Edificio mockEdifcio;
    private static Utente mockUtente;

    @BeforeAll
    static void beforeAll() {
        mockEdifcio = new Edificio("Mock");
        mockUtente = new Utente();
    }

    @BeforeEach
    void setUp() {
        prenotazioneDAO = new StubPrenotazioneDAO();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testIsAulaDisponibile1() throws Exception {
        String disp = Files.readString(Paths.get("./src/test/resources/DisponibiltaManagerRes/aula1.json"));
        Aula aula = new Aula("Aula1", 50, 100, disp, mockEdifcio);
        Date data = Date.valueOf("2019-12-30");
        Time oraInizio = Time.valueOf("11:00:00");
        Time oraFine = Time.valueOf("12:00:00");
        DisponibilitaManager disponibilitaManager = new DisponibilitaManager(aula, prenotazioneDAO);
        assertTrue(disponibilitaManager.isAulaDisponibile(data, oraInizio, oraFine));
    }

    @Test
    void testIsAulaDisponibile2() throws Exception {
        String disp = Files.readString(Paths.get("./src/test/resources/DisponibiltaManagerRes/aula1.json"));
        Aula aula = new Aula("Aula1", 50, 100, disp, mockEdifcio);
        Date data = Date.valueOf("2019-12-30");
        Time oraInizio = Time.valueOf("09:00:00");
        Time oraFine = Time.valueOf("10:00:00");
        DisponibilitaManager disponibilitaManager = new DisponibilitaManager(aula, prenotazioneDAO);
        assertFalse(disponibilitaManager.isAulaDisponibile(data, oraInizio, oraFine));
    }

    @Test
    void testIsAulaDisponibile3() throws Exception {
        String disp = Files.readString(Paths.get("./src/test/resources/DisponibiltaManagerRes/aula1.json"));
        Aula aula = new Aula("Aula1", 50, 100, disp, mockEdifcio);
        Date data = Date.valueOf("2019-12-31");
        Time oraInizio = Time.valueOf("09:00:00");
        Time oraFine = Time.valueOf("10:00:00");
        DisponibilitaManager disponibilitaManager = new DisponibilitaManager(aula, prenotazioneDAO);
        assertTrue(disponibilitaManager.isAulaDisponibile(data, oraInizio, oraFine));
    }

    @Test
    void testIsAulaDisponibile4() throws Exception {
        String disp = Files.readString(Paths.get("./src/test/resources/DisponibiltaManagerRes/aula1.json"));
        Aula aula = new Aula("Aula1", 50, 100, disp, mockEdifcio);
        Date data = Date.valueOf("2019-12-31");
        Time oraInizio = Time.valueOf("17:00:00");
        Time oraFine = Time.valueOf("19:00:00");
        DisponibilitaManager disponibilitaManager = new DisponibilitaManager(aula, prenotazioneDAO);
        assertFalse(disponibilitaManager.isAulaDisponibile(data, oraInizio, oraFine));
    }

    @Test
    void testIsAulaDisponibile5() throws Exception {
        String disp = Files.readString(Paths.get("./src/test/resources/DisponibiltaManagerRes/aula1.json"));
        Aula aula = new Aula("Aula1", 50, 100, disp, mockEdifcio);
        Date data = Date.valueOf("2019-12-31");
        Time oraInizio = Time.valueOf("17:00:00");
        Time oraFine = Time.valueOf("19:00:00");
        DisponibilitaManager disponibilitaManager = new DisponibilitaManager(aula, prenotazioneDAO);
        assertFalse(disponibilitaManager.isAulaDisponibile(data, oraInizio, oraFine));
    }

    @Test
    void testIsAulaDisponibile6() throws Exception {
        String disp = Files.readString(Paths.get("./src/test/resources/DisponibiltaManagerRes/aula1.json"));
        Aula aula = new Aula("Aula1", 50, 100, disp, mockEdifcio);
        Date data = Date.valueOf("2019-12-30");
        Time oraInizio = Time.valueOf("11:00:00");
        Time oraFine = Time.valueOf("13:00:00");
        Prenotazione p1 = new Prenotazione(101, Date.valueOf("2019-12-30"),
                Time.valueOf("13:00:00"), Time.valueOf("14:00:00"),
                TipoPrenotazione.AULA, aula, mockUtente);
        prenotazioneDAO.insert(p1);
        DisponibilitaManager disponibilitaManager = new DisponibilitaManager(aula, prenotazioneDAO);
        assertTrue(disponibilitaManager.isAulaDisponibile(data, oraInizio, oraFine));
    }

    @Test
    void testIsAulaDisponibile7() throws Exception {
        String disp = Files.readString(Paths.get("./src/test/resources/DisponibiltaManagerRes/aula1.json"));
        Aula aula = new Aula("Aula1", 50, 100, disp, mockEdifcio);
        Date data = Date.valueOf("2019-12-30");
        Time oraInizio = Time.valueOf("11:00:00");
        Time oraFine = Time.valueOf("13:30:00");
        Prenotazione p1 = new Prenotazione(101, Date.valueOf("2019-12-30"),
                Time.valueOf("13:00:00"), Time.valueOf("14:00:00"),
                TipoPrenotazione.AULA, aula, mockUtente);
        prenotazioneDAO.insert(p1);
        DisponibilitaManager disponibilitaManager = new DisponibilitaManager(aula, prenotazioneDAO);
        assertFalse(disponibilitaManager.isAulaDisponibile(data, oraInizio, oraFine));
    }

    @Test
    void testIsAulaDisponibile8() throws Exception {
        String disp = Files.readString(Paths.get("./src/test/resources/DisponibiltaManagerRes/aula1.json"));
        Aula aula = new Aula("Aula1", 50, 100, disp, mockEdifcio);
        Date data = Date.valueOf("2019-12-30");
        Time oraInizio = Time.valueOf("11:00:00");
        Time oraFine = Time.valueOf("13:30:00");
        Prenotazione p1 = new Prenotazione(101, Date.valueOf("2019-12-30"),
                Time.valueOf("13:00:00"), Time.valueOf("14:00:00"),
                TipoPrenotazione.POSTO, aula, mockUtente);
        prenotazioneDAO.insert(p1);
        DisponibilitaManager disponibilitaManager = new DisponibilitaManager(aula, prenotazioneDAO);
        assertTrue(disponibilitaManager.isAulaDisponibile(data, oraInizio, oraFine));
    }

    @Test
    void testIsAula_InManutenzione_Disponibile() throws Exception {
        String disp = Files.readString(Paths.get("./src/test/resources/DisponibiltaManagerRes/aula2.json"));
        Aula aula = new Aula("Aula1", 50, 100, disp, mockEdifcio);
        Date data = Date.valueOf("2019-12-30");
        Time oraInizio = Time.valueOf("11:00:00");
        Time oraFine = Time.valueOf("13:30:00");
        DisponibilitaManager disponibilitaManager = new DisponibilitaManager(aula, prenotazioneDAO);
        assertFalse(disponibilitaManager.isAulaDisponibile(data, oraInizio, oraFine));
    }

    @Test
    void testIsAulaDisponibileThrows() throws Exception {
        String disp = Files.readString(Paths.get("./src/test/resources/DisponibiltaManagerRes/aula1.json"));
        Aula aula = new Aula("Aula1", 50, 100, disp, mockEdifcio);
        Date data = Date.valueOf("2019-12-30");
        Time oraInizio = Time.valueOf("11:00:00");
        Time oraFine = Time.valueOf("10:00:00");
        DisponibilitaManager disponibilitaManager = new DisponibilitaManager(aula, prenotazioneDAO);
        assertThrows(IllegalArgumentException.class, () ->
                disponibilitaManager.isAulaDisponibile(data, oraInizio, oraFine),
                "Il parametro oraFine non può essere antecedente a oraInizio");
    }

    @Test
    void testIsAulaDisponibileTuttiPostiOccupati() throws Exception {
        String disp = Files.readString(Paths.get("./src/test/resources/DisponibiltaManagerRes/aula1.json"));
        Aula aula = new Aula("Aula1", 100, 100, disp, mockEdifcio);
        Date data = Date.valueOf("2019-12-31");
        Time oraInizio = Time.valueOf("11:00:00");
        Time oraFine = Time.valueOf("13:00:00");
        DisponibilitaManager disponibilitaManager = new DisponibilitaManager(aula, prenotazioneDAO);
        assertTrue(disponibilitaManager.isAulaDisponibile(data, oraInizio, oraFine));
    }

    @Test
    void testIsPostoDisponibile1() throws Exception {
        String disp = Files.readString(Paths.get("./src/test/resources/DisponibiltaManagerRes/aula1.json"));
        Aula aula = new Aula("Aula1", 99, 100, disp, mockEdifcio);
        Date data = Date.valueOf("2019-12-30");
        Time oraInizio = Time.valueOf("11:00:00");
        Time oraFine = Time.valueOf("13:30:00");
        DisponibilitaManager disponibilitaManager = new DisponibilitaManager(aula, prenotazioneDAO);
        assertTrue(disponibilitaManager.isPostoDisponibile(data, oraInizio, oraFine));
    }

    @Test
    void testIsPostoDisponibile2() throws Exception {
        String disp = Files.readString(Paths.get("./src/test/resources/DisponibiltaManagerRes/aula1.json"));
        Aula aula = new Aula("Aula1", 100, 100, disp, mockEdifcio);
        Date data = Date.valueOf("2019-12-30");
        Time oraInizio = Time.valueOf("11:00:00");
        Time oraFine = Time.valueOf("13:30:00");
        DisponibilitaManager disponibilitaManager = new DisponibilitaManager(aula, prenotazioneDAO);
        assertFalse(disponibilitaManager.isPostoDisponibile(data, oraInizio, oraFine));
    }

    @Test
    void testIsPostoDisponibile3() throws Exception {
        String disp = Files.readString(Paths.get("./src/test/resources/DisponibiltaManagerRes/aula2.json"));
        Aula aula = new Aula("Aula1", 80, 100, disp, mockEdifcio);
        Date data = Date.valueOf("2019-12-30");
        Time oraInizio = Time.valueOf("11:00:00");
        Time oraFine = Time.valueOf("13:30:00");
        DisponibilitaManager disponibilitaManager = new DisponibilitaManager(aula, prenotazioneDAO);
        assertFalse(disponibilitaManager.isPostoDisponibile(data, oraInizio, oraFine));
    }
}