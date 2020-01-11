package model.database;

import com.mysql.cj.jdbc.MysqlDataSource;
import model.dao.UtenteDAO;
import model.dao.ViolazioneEntityException;
import model.pojo.TipoUtente;
import model.pojo.Utente;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DBUtenteDAOTest {
    private static DataSource dataSource;
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
        dataSource = mysqlDS;
        dbConnection.setDataSource(dataSource);
    }

    @BeforeEach
    void setUp() throws Exception {

        utenteDAO = DBUtenteDAO.getInstance();
        DBConnection.getInstance().getConnection().setAutoCommit(false);
    }

    @AfterEach
    void tearDown() throws Exception {
        DBConnection.getInstance().getConnection().rollback();
    }

    @Test
    void retriveByEmail_NOK() throws Exception {
        String email = null;
        assertNull(utenteDAO.retriveByEmail(email));

    }

    @Test
    void retriveByEmail_OK() {
        String email = "l.capozzoli@studenti.unisa.it";
        Utente utente = utenteDAO.retriveByEmail(email);
        System.out.println(utente);
        assertEquals(email, utente.getEmail());
    }


    @Test
    void update(){
        Utente u = new Utente("l.capozzoli@studenti.unisa.it","Lorenzo","Capozzoli","Lorenzo123", TipoUtente.STUDENTE);
        utenteDAO.update(u);
        Utente newUtente = utenteDAO.retriveByEmail("l.capozzoli@studenti.unisa.it");
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
        Utente u = new Utente("a.capone@studenti.unisa.it", "Alfredo", "Capone","Alfredo1",TipoUtente.STUDENTE);
        utenteDAO.insert(u);
        Utente newUtente = utenteDAO.retriveByEmail(u.getEmail());
        assertNotNull(newUtente);
        assertEquals(u.getEmail(), newUtente.getEmail());
        assertEquals(u.getNome(), newUtente.getNome());
        assertEquals(u.getCognome(),newUtente.getCognome());
        assertEquals(u.getPassword(),newUtente.getPassword());
        assertEquals(u.getTipoUtente(), newUtente.getTipoUtente());
    }

    @Test
    void insert_NOTOK() {
        Utente u = new Utente("l.capozzoli@studenti.unisa.it", "Lorenzo", "Capozzoli", "Lorenzo1", TipoUtente.STUDENTE);

        assertThrows(ViolazioneEntityException.class, () -> utenteDAO.insert(u), "Utente già esistente " + u);
    }

    @Test
    void delete_OK(){
        Utente u = utenteDAO.retriveByEmail("l.capozzoli@studenti.unisa.it");
        utenteDAO.delete(u);
        assertNotNull(u);

    }
    @Test
    void delete_NOTOK(){
        Utente u1 = new Utente();
        u1.setEmail("m.sadsad@stu.it");
        System.out.println(u1);
        assertThrows(ViolazioneEntityException.class, () -> utenteDAO.delete(u1), "Non esiste l'utente " + u1 + " nel database");

    }

    @Test
    void retriveAll(){
        List<Utente> l = utenteDAO.retriveAll();
        assertNotNull(l);
    }
}
