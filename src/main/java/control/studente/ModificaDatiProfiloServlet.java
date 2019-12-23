package control.studente;

import control.utili.SessionManager;
import model.dao.UtenteDAO;
import model.dao.ViolazioneEntityException;
import model.database.DBUtenteDAO;
import model.pojo.Utente;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet("/modificaProfilo")
public class ModificaDatiProfiloServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        getServletContext().setAttribute(UTENTE_DAO_PARAM, DBUtenteDAO.getInstance());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession ssn = request.getSession();
        SessionManager sessionManager = new SessionManager();
        Utente user = sessionManager.getUtente(ssn);
        String addres = "VisulizzaProfilo.jsp";

        try {
            if (user == null) {
                SessionManager.setError(ssn, "Utente non registrato");
                response.sendRedirect(request.getServletContext().getContextPath() + "/login");
                return;
            }

            Utente u = new Utente();
            int count = 0;
            String nomeRgx = "/[A-Z][a-zA-Z][^#&<>\\\"~;$^%{}?{0-9}]{3,20}$/";
            String cogRgx = "/[A-Z][a-zA-Z][^#&<>\\\"~;$^%{}?{0-9}]{3,20}$/";
            String pswRgz = "^((?=.*[\\d])(?=.*[a-z])(?=.*[A-Z])) .{8,30}$";

            String nome = request.getParameter("nome").trim();
            String cognome = request.getParameter("cognome").trim();
            String password = request.getParameter("psw");
            String cPassword = request.getParameter("pswC");

            if (nome == null) {
                SessionManager.setError(ssn, "Il campo Nome non rispetta il formato");
                response.sendRedirect(request.getServletContext().getContextPath() + "/VisualizzaProfilo");
                return;
            }
            if (nome.matches(nomeRgx)) {
                u.setNome(nome);
                count++;
            } else {
                SessionManager.setError(ssn, "Il campo Nome non rispetta il formato");
                response.sendRedirect(request.getServletContext().getContextPath() + "/VisualizzaProfilo");
                return;
            }

            if (cognome == null) {
                SessionManager.setError(ssn, "Il campo Cognome non rispetta il formato");
                response.sendRedirect(request.getServletContext().getContextPath() + "/VisualizzaProfilo");
                return;
            }
            if (nome.matches(cogRgx)) {
                u.setCognome(cognome);
                count++;
            } else {
                SessionManager.setError(ssn, "Il campo Cognome non rispetta il formato");
                response.sendRedirect(request.getServletContext().getContextPath() + "/VisualizzaProfilo");
                return;
            }

            if (password == null) {
                SessionManager.setError(ssn, "Il campo Password non rispetta il formato");
                response.sendRedirect(request.getServletContext().getContextPath() + "/VisualizzaProfilo");
                return;
            }
            if (password.matches(pswRgz)) {
                u.setPassword(password);
                count++;
            } else {
                SessionManager.setError(ssn, "Il campo Cognome non rispetta il formato");
                response.sendRedirect(request.getServletContext().getContextPath() + "/VisualizzaProfilo");
                return;
            }
            if (!password.equals(cPassword)) {
                SessionManager.setError(ssn, "Il campo Password non corrisponde con la Password di conferma ");
                response.sendRedirect(request.getServletContext().getContextPath() + "/VisualizzaProfilo");
                return;
            } else
                count++;


            if (count == 4) {
                u.setEmail(user.getEmail());
                DBUtenteDAO.getInstance().update(u);
            }
        } catch (ViolazioneEntityException e) {
            addres = "error.jsp";
            e.printStackTrace();
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher(addres);
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }


    private static final long serialVersionUID = 1L;
    private static final String UTENTE_DAO_PARAM = "LogoutServlet.UtenteDAO";
}