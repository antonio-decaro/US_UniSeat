package control;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Questa servlet si occupa di gestire la fase di log in di un utente.
 * */
public class LoginServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        this.getServletContext().setAttribute("UserDAO", new Object());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO
    }
}
