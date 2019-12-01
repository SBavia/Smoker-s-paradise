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
 * This class implements interface "Action" for deleting guide.
 */
public class DeletingGuide implements Action {

    /**
     * Logger for debug and error.
     */
    private static Logger logger;

    /**
     * Public default constructor.
     */
    public DeletingGuide() {

        logger = LogManager.getLogger(DeletingGuide.class);

    }

    public void execute(final HttpServletRequest request,
                        final HttpServletResponse response)
            throws IOException, ServletException {

        final String guideName = request.getParameter("guide_name");
        final AbstractDao dao = new GuideDao();

        List<Guide> guides = new ArrayList<>();

        try {

            ((GuideDao) dao).delete(guideName);
            guides = ((GuideDao) dao).select();

        } catch (ApplicationException e) {

            logger.log(Level.ERROR, e.getMessage());
            e.printStackTrace();

        }

        request.getSession().removeAttribute("guides");
        request.getSession().setAttribute("guides", guides);
        request.getRequestDispatcher("C:\\Users\\Maurice\\Desktop\\трит\\Smoker-s-paradise\\code\\web\\WEB-INF\\jsp\\admin.jsp")
                .forward(request, response);

    }

}