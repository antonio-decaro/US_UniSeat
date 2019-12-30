package control.admin;

import control.utili.SessionManager;
import model.dao.AulaDAO;
import model.dao.EdificioDAO;
import model.dao.ViolazioneEntityException;
import model.database.DBAulaDAO;
import model.database.DBEdificioDAO;
import model.pojo.*;

import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Questa servlet permette all'admin di inserire una nuova aula all'interno del database
 * @author Spinelli Gianluca
 * @version 0.1
 * @see model.pojo.Aula
 * @see model.dao.AulaDAO
 */

@WebServlet("/inserisciAula")
public class InserisciAulaServlet extends javax.servlet.http.HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        this.getServletContext().setAttribute(AULA_DAO_PARAM, DBAulaDAO.getInstance());
        this.getServletContext().setAttribute(EDIFICIO_DAO_PARAM, DBEdificioDAO.getInstance());
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        HttpSession session = request.getSession();
        if (!SessionManager.isAlradyAuthenticated(session)) {
            response.sendRedirect("Login.jsp");
            return;
        }
        Utente u = SessionManager.getUtente(session);

        if (!u.getTipoUtente().equals(TipoUtente.ADMIN)) { // se non è admin o non è loggato
            response.sendRedirect("Login.jsp");
            return;
        }

        String edificio = request.getParameter("edificio");
        String nome = request.getParameter("nome_aula");
        String num_posti = request.getParameter("numero_posti");
        int n_posti = Integer.parseInt(num_posti);
        String disponibilità = request.getParameter("disp_aula");

        if (edificio == null || edificio.length() < 1 )
        {
            //response.getWriter().print(400);
            SessionManager.setError(session, "Edificio non selezionato");

            return;
        }

        if (n_posti < 20 || n_posti > 300) {

            //response.getWriter().print(400);
            SessionManager.setError(session, "Numero posti non corretto");
            return;

        } else if (!num_posti.matches("[0-9]")) {

            //response.getWriter().print(400);
            SessionManager.setError(session, "Formato numero posti non valido");
            return;
        }

        EdificioDAO edificioDAO = (EdificioDAO) getServletContext().getAttribute(EDIFICIO_DAO_PARAM);
        Edificio ed = edificioDAO.retriveByName(edificio);
        if (ed == null) {

            //response.getWriter().print(400);
            SessionManager.setError(session, "Edificio non trovato");
            return;

        } else {

            Aula nuova_aula = new Aula(nome, 0, n_posti, disponibilità, ed);
            String servizi_extra_prese = request.getParameter("servizi_extra_prese");
            String servizi_extra_computer = request.getParameter("servizi_extra_computer");
            Servizio servizi_extra;

            ArrayList<Servizio> servizi = new ArrayList<>();

            if ( servizi_extra_computer != null && servizi_extra_computer.equals(Servizio.COMPUTER.toString())) {

                servizi_extra = Servizio.COMPUTER;
                servizi.add(servizi_extra);

            } else if (!servizi_extra_computer.equals(Servizio.COMPUTER.toString()) && servizi_extra_computer != null) {
                //response.getWriter().print(400);
                SessionManager.setError(session, "Servizi non validi");
                return;
            }
            if (servizi_extra_prese != null && servizi_extra_prese.equals(Servizio.PRESE.toString())) {

                servizi_extra = Servizio.PRESE;
                servizi.add(servizi_extra);

            } else if (!servizi_extra_prese.equals(Servizio.PRESE.toString()) && servizi_extra_prese != null) {
                //response.getWriter().print(400);
                SessionManager.setError(session, "Servizi non validi");
                return;
            }

            nuova_aula.setServizi(servizi);

            if (disponibilità == null) {

                //response.getWriter().print(400);
                SessionManager.setError(session, "Orari di disponibilità errati");
                return;
            }

            if (nome.length() < 1 || nome.length() > 16) {

                //response.getWriter().print(400);
                SessionManager.setError(session, "Nome aula non valido");
                return;

            } else if (!nome.matches("[^A-Za-z0-9]")) {

                //response.getWriter().print(400);
                SessionManager.setError(session, "Nome aula non rispetta il formato");
                return;

            } else {
                AulaDAO aulaDAO = (AulaDAO) getServletContext().getAttribute(AULA_DAO_PARAM);
                boolean result = aulaDAO.insert(nuova_aula);
                if (!result) {
                    //response.getWriter().print(400);
                    SessionManager.setError(session, "Aula già esistente!");
                    return;
                }
                response.getWriter().print(200);

            }

        }

    }

    public void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        doPost(request,response);
    }

    public static final String AULA_DAO_PARAM = "InserisciAulaServlet.AulaDAO";
    public static final String EDIFICIO_DAO_PARAM = "InserisciAulaServlet.AulaDAO";
}
