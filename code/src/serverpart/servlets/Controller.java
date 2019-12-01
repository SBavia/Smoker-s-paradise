package serverpart.servlets;

import database.GuideDao;
import exception.ApplicationException;
import serverpart.action.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet controller.
 */
public class Controller extends HttpServlet {

    /**
     * Logger for debug and error.
     */
    private static Logger logger;

    /**
     * {@inheritDoc}
     */
    @Override
    public void init() {

        logger = LogManager.getLogger(Controller.class);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doPost(final HttpServletRequest request,
                       final HttpServletResponse response)
            throws IOException, ServletException {

        try {
            this.process(request, response);
        } catch (ApplicationException e) {

            logger.log(Level.ERROR, " " + e.getMessage());
            e.printStackTrace();

        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doGet(final HttpServletRequest request,
                      final HttpServletResponse response)
            throws IOException, ServletException {

        try {
            this.process(request, response);
        } catch (ApplicationException e) {

            logger.log(Level.ERROR, " " + e.getMessage());
            e.printStackTrace();

        }

    }

    private void process(final HttpServletRequest request,
                         final HttpServletResponse response)
            throws ApplicationException, IOException, ServletException {

        final Action action;
        final String clientAction = request.getParameter("action");
        final String errorString = "Action type is incorrect.";
        final String debugString = "Attribute is null.";

        if (clientAction != null) {

            switch (clientAction) {

                case "signin":
                    action = new Authorization();
                    action.execute(request, response);
                    break;
                case "logout":
                    request.getSession().removeAttribute("user");
                    request.getRequestDispatcher("web/index.jsp").forward(request, response);
                    break;
                case "delete_guide":
                    action = new DeletingGuide();
                    action.execute(request, response);
                    break;
                case "show_guides":
                    action = new ShowingGuides();
                    action.execute(request, response);
                    break;
                case "add_guide":
                    action = new AddingGuide();
                    action.execute(request, response);
                    break;
                case "change_guide":
                    action = new DeletingGuide();
                    action.execute(request, response);
                    break;
                case "search_guide":
                    action = new Searching();
                    action.execute(request, response);
                    break;
                case "back_admin":
                    request.getRequestDispatcher("web/admin.jsp")
                            .forward(request, response);
                    break;
                case "back_user":
                    request.getRequestDispatcher("web/index.jsp")
                            .forward(request, response);
                    break;
                case "all":
                    request.getSession().removeAttribute("guides");
                    request.getSession()
                            .setAttribute("guides", new GuideDao().select());
                    request.getRequestDispatcher("web/main.jsp")
                            .forward(request, response);
                    break;
                default:
                    throw new ApplicationException(errorString);

            }

        } else {
            logger.log(Level.DEBUG, debugString);
        }

    }

}