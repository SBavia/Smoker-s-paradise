package serverpart.action;

import database.GuideDao;
import database.dao.AbstractDao;
import entity.Guide;
import exception.ApplicationException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements  interface "Action" for searching drugs.
 */
public class Searching implements Action {

    /**
     * Logger for debug and error.
     */
    private static Logger logger;

    /**
     * Public default constructor.
     */
    public Searching() {

        logger = LogManager.getLogger(Searching.class);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(final HttpServletRequest request,
                        final HttpServletResponse response)
            throws IOException, ServletException {

        final AbstractDao dao = new GuideDao();
        final String debugString;

        List<Guide> guides = new ArrayList<>();

        try {

            guides = ((GuideDao) dao).select(
                    request.getParameter("guide_name"));
            debugString = " Search by name is completed.";


            logger.log(Level.DEBUG, debugString);

        } catch (ApplicationException e) {

            logger.log(Level.ERROR, e.getMessage());
            e.printStackTrace();

        }

        request.getSession().setAttribute("guides", guides);
        request.getRequestDispatcher("web/main.jsp")
                .forward(request, response);

    }

}