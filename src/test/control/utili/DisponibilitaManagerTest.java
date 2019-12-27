package control.utili;

import model.dao.PrenotazioneDAO;
import model.pojo.Aula;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DisponibilitaManagerTest {

    @Mock
    private PrenotazioneDAO mockPrenotazioneDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testConstructor() throws Exception {

        String jsonString = Files.readString(Paths.get("./src/test/resources/example.json"));
        Aula aula = new Aula();
        aula.setDisponibilita(jsonString);
        DisponibilitaManager disponibilitaManager = new DisponibilitaManager(aula, mockPrenotazioneDAO);
        final String expected = "[[[10:30, 15:30], [16:00, 17:00]], [[11:00, 12:30], [15:00, 18:30]], [[10:30, 15:30], " +
                "[16:00, 17:00]], [[10:30, 15:30], [16:00, 17:00]], [[10:30, 15:30], [16:00, 17:00]], [[10:30, 15:30], " +
                "[16:00, 17:00]], [[10:30, 15:30], [16:00, 17:00]]]";
        System.out.println(Arrays.toString(disponibilitaManager.getDisponibilita().intervalli));
        assertEquals(expected, Arrays.toString(disponibilitaManager.getDisponibilita().intervalli));
    }
}