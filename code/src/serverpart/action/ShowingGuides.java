package serverpart.action;

import database.GuideDao;
import database.dao.AbstractDao;
import exception.ApplicationException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This class implements interface "Action" for showing guides.
 */
public class ShowingGuides implements Action {

    /**
     * Logger for debug and error.
     */
    private Logger logger;

    /**
     * Public default constructor.
     */
    public ShowingGuides() {

        logger = LogManager.getLogger(ShowingGuides.class);

    }

    @Override
    public void execute(final HttpServletRequest request,
                        final HttpServletResponse response)
            throws ServletException, IOException {

        final AbstractDao dao = new GuideDao();

        try {

            request.getSession().setAttribute("guides",
                    ((GuideDao) dao).select());
            request.getRequestDispatcher(
                    "C:\\Users\\Maurice\\Desktop\\трит\\Smoker-s-paradise\\code\\web\\WEB-INF\\jsp\\guides.jsp")
                    .forward(request, response);

        } catch (ApplicationException e) {

            logger.log(Level.ERROR, e.getMessage());
            e.printStackTrace();

        }

    }

}