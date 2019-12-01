package factory;

import entity.Guide;
import entity.Substance;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class implements methods createEntity for guide.
 */
public class GuideFactory implements EntityFactory<Guide> {

    /**
     * Logger for debug.
     */
    private static Logger logger;

    /**
     * Public default constructor.
     */
    public GuideFactory() {

        logger = LogManager.getLogger(GuideFactory.class);

    }

    public Guide createEntity() {

        final Guide guide = new Guide();
        final String debugString = " Object Guide " + guide + " created.";

        logger.log(Level.DEBUG, debugString);

        return guide;

    }

    @Override
    public Guide createEntity(final HttpServletRequest request) {

        final Guide guide = new Guide();
        final Substance substance = new Substance();
        final String debugString = " Object Guide " + guide + " created.";

        substance.setName(request.getParameter("substance_name"));

        guide.setName(request.getParameter("guide_name"));
        guide.setDescription(request.getParameter("description"));
        guide.setSubstance(substance);

        logger.log(Level.DEBUG, debugString);

        return guide;

    }

    @Override
    public Guide createEntity(final ResultSet resultSet)
            throws SQLException {

        final Guide guide = new Guide();
        final String debugString = " Object Guide " + guide + " created.";

        guide.setId(resultSet.getInt("id"));
        guide.setName(resultSet.getString("name"));
        guide.setDescription(resultSet.getString("description"));
        guide.setSubstance(new Substance() {{
            this.setName(resultSet.getString("substance_name"));
        }});

        logger.log(Level.DEBUG, debugString);

        return guide;

    }

}