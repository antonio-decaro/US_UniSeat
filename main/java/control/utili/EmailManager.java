package control.utili;

import model.pojo.Prenotazione;
import model.pojo.Utente;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Questa classe definisce metodi per inviare email.
 * @author De Caro Antonio
 * @version 0.2
 * */
public class EmailManager {

    private static final Logger logger = Logger.getLogger(EmailManager.class.getName());
    private String hostname;
    private final String USERNAME = "jamammo.unisa@gmail.com";
    private final String PASSWORD = "7T5iayhJGfBwZb5";

    /**
     * Inizializza il Manager di Email per l'invio delle email.
     *
     * @param hostname hostname del server al quale far riferimento nelle email.
     * */
    public EmailManager(String hostname) {
        this.hostname = hostname;
    }

    /**
     * Invia una email di conferma registrazione ad un utente.
     *
     * @param utente utente a cui inviare l'email di conferma.
     * @since v 0.1
     * */
    public void inviaEmailConferma(Utente utente) {

        String email = utente.getEmail();
        long code = utente.getCodiceVerifica();

        String subject = "Conferma Email";
        String body = "Link di attivazione account: "
                + "<a href=\"http://" + hostname + "/verifica?email=" + email + "&code=" + code
                + "\">Activation Code</a>";
        send(subject, body, email);
    }

    /**
     * Questo metodo invia una mail ad un utente per informalo che deve uscire dall'aula che ha prenotato,
     * comunicandogli i dettagli della prenotazione del docente che occuperà l'aula al posto suo.
     *
     * @param utente utente a cui inviare l'email della prenotazione
     * @param prenotazione dettagli della prenotazione da inviare all'utente
     * @since v 0.2
     * */
    public void comunicaPrenotazione(Utente utente, Prenotazione prenotazione) {

        String to = utente.getEmail();
        String subject = "Avviso prenotazione Aula";
        String body = String.format("Gentile %s %s,\nvogliamo informarla che l'aula verrà occupata dalle ore %s alle ore " +
                "%s del giorno %s. " +
                "La preghiamo pertanto di abbandonare l'aula per l'orario richiesto al fine di evitare situazioni scomode.",
                utente.getCognome(), utente.getNome(), prenotazione.getOraInizio().toString(),
                prenotazione.getOraFine().toString(), prenotazione.getData().toString());

        send(subject, body, to);
    }

    /**
     * Metodo che si occupa di effettuare l'invio della email.
     *
     * @param subject soggetto della email
     * @param body corpo della email
     * @param to destinatario della email
     * @since v 0.2
     * */
    private void send(String subject, String body, String to) {
        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });

        new Thread(() -> {
            try {
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress("noreply@jamammo.it"));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(to));
                message.setSubject(subject);
                message.setText(body,"UTF-8", "html");

                logger.log(Level.INFO, "Trying to send an email to " + to);

                Transport.send(message);

                logger.log(Level.INFO, "Email sent");

            } catch (MessagingException e) {
                logger.log(Level.SEVERE, "Message: {0}\nCause: {1}", new Object[]{e.getMessage(), e.getCause()});
            }
        }).start();
    }
}
