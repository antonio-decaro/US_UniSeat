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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Questa servlet permette all'admin di modificare un'aula all'interno del database
 *
 * @author Spinelli Gianluca
 * @version 0.1
 * @see model.pojo.Aula
 * @see model.dao.AulaDAO
 */
@WebServlet("/modificaAula")
public class ModificaAulaServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        getServletContext().setAttribute(AULA_DAO_PARAM, DBAulaDAO.getInstance());
        getServletContext().setAttribute(EDIFICIO_DAO_PARAM, DBEdificioDAO.getInstance());
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Utente u = SessionManager.getUtente(session);
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

        if (u == null || !u.getTipoUtente().equals(TipoUtente.ADMIN)) { // se non è admin o non è loggato
            response.sendRedirect("Login.jsp");
            SessionManager.setError(session, "Utente non abilitato");
            return;
        }

        String edificio = request.getParameter("edificio");
        String nome = request.getParameter("nome_aula");
        String num_posti = request.getParameter("numero_posti");
        int n_posti = 0;
        String disponibilita = request.getParameter("disp_aula");

        if (edificio == null) {
            response.getWriter().print("Edificio non selezionato");
            SessionManager.setError(session, "Edificio non selezionato");
            return;
        }

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

                    response.getWriter().print(400);
                    SessionManager.setError(session, "Servizi non validi");
                    return;
                }
            }

            String regex = "\\{\\s*?[\"']intervalli[\"']\\s*?:\\s*?\\[\\s*?(\\[(\\[\\s*?(\"[0-2][0-9]:[0-6][0-9]\")\\s*?," +
                    "\\s*?(\"[0-2][0-9]:[0-6][0-6]\")\\s*?],?\\s*?)*],?\\s*){5,7}\\s*]\\s*?}";
            if (disponibilita == null || !disponibilita.matches(regex)) {
                response.getWriter().print("Orari di disponibilit&agrave; errati");
                SessionManager.setError(session, "Orari di disponibilità errati");
                return;
            }

            if (nome == null || nome.length() < 1 || nome.length() > 16) {

                response.getWriter().print("Nome aula non valido");
                SessionManager.setError(session, "Nome aula non valido");
                return;

            } else if (!nome.matches("^[A-Z a-z0-9]+$")) {

                response.getWriter().print("Nome aula non rispetta il formato");
                SessionManager.setError(session, "Nome aula non rispetta il formato");
                return;
            }

            AulaDAO aulaDAO = (AulaDAO) request.getServletContext().getAttribute(AULA_DAO_PARAM);
            Aula b = aulaDAO.retriveByName(nome);

            if (b == null) {
                response.getWriter().print("Aula non esistente");
                SessionManager.setError(session, "Aula non esistente");
                return;
            }


            Aula nuova_aula = new Aula(nome, n_posti, disponibilita, ed);
            nuova_aula.setServizi(servizi);
            nuova_aula.setId(b.getId());

            try {
                aulaDAO.update(nuova_aula);
            } catch (ViolazioneEntityException e) {
                SessionManager.setError(session, e.getMessage());
                response.getWriter().print(e.getMessage());
            }
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().print("Aula modificata con successo");
        }
    }

    static final String AULA_DAO_PARAM = "ModificaAulaServlet.AulaDAO";
    static final String EDIFICIO_DAO_PARAM = "ModificaAulaServlet.EdificioDAO";
}
