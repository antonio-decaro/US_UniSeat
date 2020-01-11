package control.admin;

import control.utili.SessionManager;
import model.dao.AulaDAO;
import model.dao.EdificioDAO;
import model.dao.ViolazioneEntityException;
import model.database.DBAulaDAO;
import model.database.DBEdificioDAO;
import model.pojo.*;

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
        getServletContext().setAttribute(AULA_DAO_PARAM, DBAulaDAO.getInstance());
        getServletContext().setAttribute(EDIFICIO_DAO_PARAM, DBEdificioDAO.getInstance());
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        HttpSession session = request.getSession();
        Utente u = SessionManager.getUtente(session);

        if (u == null || !u.getTipoUtente().equals(TipoUtente.ADMIN)) { // se non è admin o non è loggato
            response.sendRedirect("Login.jsp");
            SessionManager.setError(session, "Utente non abilitato");
            return;
        }

        String edificio = request.getParameter("edificio");
        String nome = request.getParameter("nome_aula");
        String num_posti = request.getParameter("numero_posti");

        String disponibilita = request.getParameter("disp_aula");

        if (edificio == null || edificio.length() < 1 )
        {
            response.getWriter().print("Edificio non selezionato");
            SessionManager.setError(session, "Edificio non selezionato");
            return;
        }
        int n_posti;
        if (!num_posti.matches("^[0-9]+$")) {

            response.getWriter().print("Formato numero posti non valido");
            SessionManager.setError(session, "Formato numero posti non valido");
            return;

        } else {
            n_posti = Integer.parseInt(num_posti);
            if (n_posti < 20 || n_posti > 300) {

                response.getWriter().print("Numero posti non corretto");
                SessionManager.setError(session, "Numero posti non corretto");
                return;
            }

        }


        EdificioDAO edificioDAO = (EdificioDAO) request.getServletContext().getAttribute(EDIFICIO_DAO_PARAM);
        Edificio ed = edificioDAO.retriveByName(edificio);
        if (ed == null) {

            response.getWriter().print("Edificio non trovato");
            SessionManager.setError(session, "Edificio non trovato");
            return;

        } else {


            String servizi_extra_prese = request.getParameter("servizi_extra_prese");
            String servizi_extra_computer = request.getParameter("servizi_extra_computer");
            Servizio servizi_extra;

            ArrayList<Servizio> servizi = new ArrayList<>();

            if (servizi_extra_computer != null) {

                if (servizi_extra_computer.equals(Servizio.COMPUTER.toString())) {

                    servizi_extra = Servizio.COMPUTER;
                    servizi.add(servizi_extra);

                } else {

                    response.getWriter().print("Servizi non validi");
                    SessionManager.setError(session, "Servizi non validi");
                    return;
                }
            }

            if (servizi_extra_prese != null) {

                if (servizi_extra_prese.equals(Servizio.PRESE.toString())) {

                    servizi_extra = Servizio.PRESE;
                    servizi.add(servizi_extra);

                } else {

                    response.getWriter().print("Servizi non validi");
                    SessionManager.setError(session, "Servizi non validi");
                    return;
                }
            }

            if (disponibilita == null) {

                response.getWriter().print("Orari di disponibilità errati");
                SessionManager.setError(session, "Orari di disponibilità errati");
                return;
            }

            if (nome == null || nome.length() < 1 || nome.length() > 16) {

                response.getWriter().print("Nome aula non valido");
                SessionManager.setError(session, "Nome aula non valido");
                return;

            } else if (!nome.matches("^[A-Z a-z 0-9]+$")) {

                response.getWriter().print("Nome aula non rispetta il formato");
                SessionManager.setError(session, "Nome aula non rispetta il formato");
                return;

            }
            AulaDAO aulaDAO = (AulaDAO) request.getServletContext().getAttribute(AULA_DAO_PARAM);
            Aula b = aulaDAO.retriveByName(nome);
            if (b != null) {
                response.getWriter().print("Aula già esistente!");
                SessionManager.setError(session, "Aula già esistente!");
                return;
            }

            Aula nuova_aula = new Aula(nome,n_posti,disponibilita,ed);
            nuova_aula.setServizi(servizi);
            System.out.println(nuova_aula);
            try {
                aulaDAO.insert(nuova_aula);
            } catch (ViolazioneEntityException e) {
                SessionManager.setError(session, e.getMessage());
                e.printStackTrace();
                response.getWriter().print(400);
            }

            response.setStatus(HttpServletResponse.SC_OK);
        }

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        doPost(request,response);
    }

    static final String AULA_DAO_PARAM = "InserisciAulaServlet.AulaDAO";
    static final String EDIFICIO_DAO_PARAM = "InserisciAulaServlet.EdificioDAO";
}
