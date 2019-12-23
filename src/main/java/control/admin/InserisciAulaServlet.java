package control.admin;

import model.dao.AulaDAO;
import model.dao.EdificioDAO;
import model.dao.ViolazioneEntityException;
import model.database.DBAulaDAO;
import model.database.DBEdificioDAO;
import model.pojo.Aula;
import model.pojo.Edificio;
import model.pojo.Servizio;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Questa servlet permette all'admin di inserire una nuova aula all'interno del database
 * @author Spinelli Gianluca
 * @version 0.1
 * @see model.pojo.Aula
 * @see model.dao.AulaDAO
 */

@javax.servlet.annotation.WebServlet(name = "InserisciAulaServlet")
public class InserisciAulaServlet extends javax.servlet.http.HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        getServletContext().setAttribute(AULA_DAO_PARAM, DBAulaDAO.getInstance());
        getServletContext().setAttribute(EDIFICIO_DAO_PARAM, DBEdificioDAO.getInstance());
    }

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String edificio = (String) request.getParameter("edificio");
        String nome = (String) request.getParameter("nome_edificio");
        String num_posti = (String) request.getParameter("numero_posti");
        int n_posti = Integer.parseInt(num_posti);
        String disponibilità = (String) request.getParameter("disp_aula");

        if (edificio == null)
        {
            response.getWriter().print(400);
            request.setAttribute("erroreEdificioNull", "Edificio non selezionato");
        }

        if (n_posti < 20 || n_posti > 300) {

            response.getWriter().print(400);
            request.setAttribute("erroreNumeroPostiSize", "Numero posti non corretto");

        } else if (!num_posti.matches("[0-9]")) {

            response.getWriter().print(400);
            request.setAttribute("erroreNumeroPostiFormato", "Formato numero posti non valido");
        }

        EdificioDAO edificioDAO = (EdificioDAO) getServletContext().getAttribute(EDIFICIO_DAO_PARAM);
        Edificio ed = edificioDAO.retriveByName(edificio);
        if (ed == null) {

            response.getWriter().print(400);
            request.setAttribute("errorEdificio", "Edificio non trovato");

        } else {

            Aula nuova_aula = new Aula(nome, 0, n_posti, disponibilità, ed);
            String servizi_extra_prese = (String) request.getParameter("servizi_extra_prese");
            String servizi_extra_computer = (String) request.getParameter("servizi_extra_computer");
            Servizio servizi_extra;

            if (servizi_extra_computer != null || servizi_extra_prese != null) {

                if (servizi_extra_computer.equals(Servizio.COMPUTER) || servizi_extra_prese.equals(Servizio.PRESE)) {

                    response.getWriter().print(400);
                    request.setAttribute("errorServizi", "Servizi non validi");

                } else {

                    ArrayList<Servizio> servizi = new ArrayList<>();

                    if (servizi_extra_computer != null) {

                        servizi_extra = Servizio.COMPUTER;
                        servizi.add(servizi_extra);
                    }
                    if (servizi_extra_prese != null) {

                        servizi_extra = Servizio.PRESE;
                        servizi.add(servizi_extra);
                    }

                    nuova_aula.setServizi(servizi);
                }
            }

            if (disponibilità == null) {

                response.getWriter().print(400);
                request.setAttribute("errorDisponibilità", "Orari di disponibilità errati");
            }

            if (nome.length() < 1 || nome.length() > 16) {

                response.getWriter().print(400);
                request.setAttribute("erroreNomeEdificioSize", "Nome edificio non valido");

            } else if (!nome.matches("[^A-Za-z0-9]")) {

                response.getWriter().print(400);
                request.setAttribute("erroreNomeEdificioFormato", "Nome edificio non rispetta il formato");

            } else {
                AulaDAO aulaDAO = (AulaDAO) getServletContext().getAttribute(AULA_DAO_PARAM);
                Boolean result = aulaDAO.insert(nuova_aula);
                if (result == false) {
                    response.getWriter().print(400);
                    request.setAttribute("errurAulaE", "Aula già esistente!");
                }
            }

        }

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        doPost(request,response);
    }

    private static final String AULA_DAO_PARAM = "InserisciAulaServlet.AulaDAO";
    private static final String EDIFICIO_DAO_PARAM = "InserisciAulaServlet.AulaDAO";
}
