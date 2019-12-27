package control.utili;

import model.pojo.Utente;

import javax.servlet.http.HttpSession;

/**
 * Questa classe si occupa di effettuare le operazioni ricorrenti sulla sessione. In questo modo le servlet potranno
 * gestire la sessione in maniera uniforme, evitando problemi di incosistenza delle informazioni.
 *
 * @author Antonio De Caro
 * @version 0.1
 * */
public class SessionManager {

    /**
     * Questa classe controlla se, data una sessione, un utente è già autenticato ad essa.
     *
     * @param sessione sessione da controllare
     * @return true se la sessione è di un utente attivo, false altrimenti
     * @author De Caro Antonio
     * @since 0.1
     * */
    public static boolean isAlradyAuthenticated(HttpSession sessione){
        return sessione.getAttribute(USER) != null;
    }

    /**
     * Questa classe registra all'interno di una session un determinato utente.
     *
     * @param session sessino in cui salvare l'utente
     * @param utente utente da salvare nella sessione.
     * @author De Caro Antonio
     * @since 0.1
     * */
    public static void autentica(HttpSession session, Utente utente){
        session.setAttribute(USER, utente);
    }

    /**
     * Questa classe ritorna un utente data una sessione.
     *
     * @param sessione sessione da cui prelevare l'utente
     * @return l'utente salvato in quella session o null se nessun utente è salvato nella sessione
     * @author De Caro Antonio
     * @since 0.1
     * */
    public static Utente getUtente(HttpSession sessione){
        Object obj = sessione.getAttribute(USER);
        if (obj instanceof Utente)
            return (Utente) obj;
        else
            return null;
    }

    /**
     * Questa classe setta un errore all'interno della sessione.
     *
     * @param sessione sessione in cui settare l'errore
     * @param errore messaggio di errore da inserire
     * @author De Caro Antonio
     * @since 0.1
     * */
    public static void setError(HttpSession sessione, String errore) {
        sessione.setAttribute(ERROR, errore);
    }

    /**
     * Questa classe prende, se presente, l'errore dalla sessione
     *
     * @param sessione sessione in cui prendere l'errore
     * @return la stringa di errore, o null se non è presente alcun errore
     * @author De Caro Antonio
     * @since 0.1
     * */
    public static String getError(HttpSession sessione) {
        return sessione.getAttribute(ERROR).toString();
    }

    /**
     * Questa classe pulisce la sessione dagli errori
     *
     * @param sessione sessione da pulire
     * @author De Caro Antonio
     * @since 0.1
     * */
    public static void cleanError(HttpSession sessione) {
        sessione.removeAttribute(ERROR);
    }

    /**
     * Questo metodo setta un messaggio all'interno della sessione.
     *
     * @param sessione sessione in cui settare il messaggio
     * @param messaggio messaggio  da inserire
     * @author De Caro Antonio
     * @since 0.1
     * */
    public static void setMessage(HttpSession sessione, String messaggio) {
        sessione.setAttribute(MESSAGE, messaggio);
    }

    /**
     * Questo metodo prende, se presente, il messaggio dalla sessione
     *
     * @param sessione sessione in cui prendere l'errore
     * @return il messaggio, o null se non è presente alcun messaggio
     * @author De Caro Antonio
     * @since 0.1
     * */
    public static String getMessage(HttpSession sessione) {
        return sessione.getAttribute(MESSAGE).toString();
    }

    /**
     * Questa classe pulisce la sessione dai messaggi
     *
     * @param sessione sessione da pulire
     * @author De Caro Antonio
     * @since 0.1
     * */
    public static void cleanMessage(HttpSession sessione) {
        sessione.removeAttribute(MESSAGE);
    }

    private static final String USER = "SessionManager.user";
    private static final String ERROR = "SessionManager.error";
    private static final String MESSAGE = "SessionManager.message";
}
