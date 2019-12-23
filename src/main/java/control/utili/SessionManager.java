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
     * @param session sessione da controllare
     * @return true se la sessione è di un utente attivo, false altrimenti
     * @author De Caro Antonio
     * @since 0.1
     * */
    public static boolean isAlradyAuthenticated(HttpSession session){
        return session.getAttribute(USER) != null;
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

    }

    /**
     * Questa classe ritorna un utente data una sessione.
     *
     * @param session sessione da cui prelevare l'utente
     * @return l'utente salvato in quella session o null se nessun utente è salvato nella sessione
     * @author De Caro Antonio
     * @since 0.1
     * */
    public Utente getUtente(HttpSession session){
        Object obj = session.getAttribute(USER);
        if (obj instanceof Utente)
            return (Utente) obj;
        else
            return null;
    }

    private static final String USER = "SessionManager.user";
}
