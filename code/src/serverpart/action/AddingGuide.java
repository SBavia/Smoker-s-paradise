package serverpart.action;

import database.GuideDao;
import database.SubstanceDao;
import database.dao.AbstractDao;
import entity.Guide;
import exception.ApplicationException;
import factory.GuideFactory;
import factory.EntityFactory;
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
 * This class implements interface "Action" for adding guide.
 */
public class AddingGuide implements Action {

    /**
     * Logger for debug and error.
     */
    private static Logger logger;

    /**
     * Public default constructor.
     */
    public AddingGuide() {

        logger = LogManager.getLogger(AddingGuide.class);

    }

    @Override
    public void execute(final HttpServletRequest request,
                        final HttpServletResponse response)
            throws IOException, ServletException {

        final EntityFactory factory = new GuideFactory();
        final Guide guide = ((GuideFactory) factory).createEntity(request);

        AbstractDao dao;
        List<Guide> guides = new ArrayList<>();

        dao = new SubstanceDao();

        try {

            if (!((SubstanceDao) dao).isExistSubstance(
                    guide.getSubstance().getName())) {
                ((SubstanceDao) dao).insert(guide.getSubstance());
            }

            dao = new GuideDao();
            ((GuideDao) dao).insert(guide);
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