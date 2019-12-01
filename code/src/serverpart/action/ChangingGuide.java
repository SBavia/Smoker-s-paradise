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

/**
 * This class implements "Action" for changing guide.
 */
public class ChangingGuide implements Action {

    /**
     * Logger for debug and error.
     */
    private static Logger logger;

    /**
     * Public default constructor.
     */
    public ChangingGuide() {

        logger = LogManager.getLogger(ChangingGuide.class);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(final HttpServletRequest request,
                        final HttpServletResponse response)
            throws IOException, ServletException {

        final String description = request.getParameter("description");
        final String guideName = request.getParameter("guide_name");
        final Guide guide = new Guide();
        final AbstractDao dao = new GuideDao();

        guide.setDescription(description);
        guide.setName(guideName);

        try {
            ((GuideDao) dao).update(guide);
        } catch (ApplicationException e) {

            logger.log(Level.ERROR, e.getMessage());
            e.printStackTrace();

        }

        request.getRequestDispatcher("web/admin.jsp")
                .forward(request, response);

    }

}