package model.database;

import com.mysql.cj.jdbc.MysqlDataSource;
import model.dao.UtenteDAO;
import model.dao.ViolazioneEntityException;
import model.pojo.TipoUtente;
import model.pojo.Utente;
import org.junit.jupiter.api.*;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DBUtenteDAOTest {
    private static DBConnection dbConnection;
    private UtenteDAO utenteDAO;

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
        utenteDAO = DBUtenteDAO.getInstance();
        dbConnection.getConnection().setAutoCommit(false);
    }

    @AfterEach
    void tearDown() throws Exception {
        dbConnection.getConnection().rollback();
        dbConnection.getConnection().setAutoCommit(true);
    }

    @Test
    void retriveByEmail_NOK() throws Exception {
        assertNull(utenteDAO.retriveByEmail(null));
    }

    @Test
    void retriveByEmail_OK() {
        String email = "m.desantis@studenti.unisa.it";
        Utente utente = utenteDAO.retriveByEmail(email);
        assertEquals(email, utente.getEmail());
    }

    @Test
    void update(){
        Utente u = new Utente("a.decaro@studenti.unisa.it","Antonio","De Caro","Antonio123", TipoUtente.STUDENTE);
        utenteDAO.update(u);
        Utente newUtente = utenteDAO.retriveByEmail("a.decaro@studenti.unisa.it");
        assertEquals(u.getEmail(), newUtente.getEmail());
        assertEquals(u.getNome(), newUtente.getNome());
        assertEquals(u.getCognome(),newUtente.getCognome());
        assertEquals(u.getPassword(),newUtente.getPassword());
        assertEquals(u.getTipoUtente(), newUtente.getTipoUtente());

    }
    @Test
    void update_NOK() throws Exception{
        Utente u = new Utente("12345@studenti.unisa.it","Lorenzo", "Capozzoli", "Alfredo1", TipoUtente.STUDENTE);

        assertThrows(ViolazioneEntityException.class, ()-> utenteDAO.update(u), "Non esiste " + u + " nel database" );
    }

    @Test
    void insert_OK(){
        Utente u = new Utente("testEmail", "", "","",TipoUtente.STUDENTE);
        utenteDAO.insert(u);
        Utente newUtente = utenteDAO.retriveByEmail(u.getEmail());
        assertNotNull(newUtente);
        assertEquals(u.getEmail(), newUtente.getEmail());
        assertEquals(u.getNome(), newUtente.getNome());
        assertEquals(u.getCognome(),newUtente.getCognome());
        assertEquals(u.getPassword(),newUtente.getPassword());
        assertEquals(u.getTipoUtente(), newUtente.getTipoUtente());
        utenteDAO.delete(u);
    }

    @Test
    void insert_NOTOK() {
        Utente u = new Utente("a.decaro@studenti.unisa.it", "", "", "", TipoUtente.STUDENTE);
        assertThrows(ViolazioneEntityException.class, () -> utenteDAO.insert(u), "Utente già esistente");
    }

    @Test
    void delete_OK(){
        Utente u = new Utente("delete_OK", "", "", "", TipoUtente.STUDENTE);
        utenteDAO.insert(u);
        assertNotNull(u);
        utenteDAO.delete(u);
        assertNull(utenteDAO.retriveByEmail("delete_OK"));
    }

    @Test
    void delete_NOTOK(){
        Utente u1 = new Utente();
        u1.setEmail("m.sadsad@stu.it");
        assertThrows(ViolazioneEntityException.class, () -> utenteDAO.delete(u1), "Non esiste l'utente " + u1 + " nel database");
    }

    @Test
    void retriveAll(){
        List<Utente> l = utenteDAO.retriveAll();
        assertNotNull(l);
    }
}
