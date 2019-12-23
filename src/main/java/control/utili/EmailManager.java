package control.utili;

import model.pojo.Utente;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Questa classe definisce metodi per inviare email.
 * @author De Caro Antonio
 * @version 0.1
 * */
public class EmailManager {

    private String hostname;

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
     * @since 0.1
     * */
    public void inviaEmailConferma(Utente utente) {
        //TODO un miglior sistema di invio delle mail
        final String username = "jamammo.unisa@gmail.com";
        final String password = "7T5iayhJGfBwZb5";

        String email = utente.getEmail();
        Long code = utente.getCodiceVerifica();

        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("noreply@jamammo.it"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email));
            message.setSubject("Conferma Email");
            message.setText("Link di attivazione account: "
                            + "<a href=\"http://" + hostname + "/verify?email=" + email + "&code=" + code.toString()
                            + "\">Activation Code</a>",
                    "UTF-8", "html");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
