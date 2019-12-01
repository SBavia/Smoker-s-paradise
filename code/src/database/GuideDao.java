package database;

import database.dao.AbstractGuideDao;
import database.pool.ConnectionPool;
import entity.Guide;
import exception.ApplicationException;
import factory.GuideFactory;
import factory.EntityFactory;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements methods for work with table 'guide'.
 */
public class GuideDao extends AbstractGuideDao {

    /**
     * Logger for debug and error.
     */
    private static Logger logger;

    /**
     * Value of the sql query for select all guides from database.
     */
    private static final String SELECT_ALL_GuideS;

    /**
     * Value of the sql query for select guides by name from database.
     */
    private static final String SELECT_BY_NAME;

    /**
     * Value of the sql query that inserts guide.
     */
    private static final String INSERT_Guide;

    /**
     * Value of the sql query that deletes guide from database.
     */
    private static final String DELETE_Guide;

    /**
     * Value of the sql query that updates guide in database.
     */
    private static final String UPDATE_Guide;

    static {

        SELECT_ALL_GuideS = "SELECT d.`id`, d.`name`, d.`description`, s.`name`"
                + " `substance_name` FROM `guide` d INNER JOIN `substance` s "
                + "ON s.`id` = d.`substance_id`";
        SELECT_BY_NAME = "SELECT d.`id`, d.`name`, d.`description`, s.`name`"
                + " `substance_name` FROM `guide` d INNER JOIN `substance` s "
                + "ON s.`id` = d.`substance_id` WHERE d.`name` = ?";
        INSERT_Guide = "INSERT INTO `guide` (`substance_id`, `name`, "
                + "`description`) VALUES((SELECT `id` "
                + "FROM `substance` s WHERE s.`name` = ?), ?, ?)";
        DELETE_Guide = "DELETE FROM `guide` WHERE name = ?";
        UPDATE_Guide = "UPDATE `guide` SET `description` = ? WHERE `name` = ?";

    }

    /**
     * Public default constructor.
     */
    public GuideDao() {

        logger = LogManager.getLogger();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Guide> select() throws ApplicationException {

        final List<Guide> guides = new ArrayList<>();
        final EntityFactory factory = new GuideFactory();
        final ResultSet resultSet;
        final String debugString = " All guides selected from table `guide`.";

        Connection connection = null;
        Statement statement = null;

        try {

            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SELECT_ALL_GuideS);

            while (resultSet.next()) {
                guides.add((Guide) factory.createEntity(resultSet));
            }

        } catch (SQLException e) {
            throw new ApplicationException(e.getMessage());
        } finally {

            super.close(statement);
            super.close(connection);

        }

        logger.log(Level.DEBUG, debugString);

        return guides;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Guide> select(final String name)
            throws ApplicationException {

        final List<Guide> guides = new ArrayList<>();
        final EntityFactory factory = new GuideFactory();
        final ResultSet resultSet;
        final String debugString = " All guides selected from table `guide`.";

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SELECT_BY_NAME);

            statement.setString(1, name);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                guides.add((Guide) factory.createEntity(resultSet));
            }

        } catch (SQLException e) {
            throw new ApplicationException(e.getMessage());
        } finally {

            super.close(statement);
            super.close(connection);

        }

        logger.log(Level.DEBUG, debugString);

        return guides;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insert(final Guide guide) throws ApplicationException {

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(INSERT_Guide);

            statement.setString(1, guide.getSubstance().getName());
            statement.setString(2, guide.getName());
            statement.setString(3, guide.getDescription());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new ApplicationException(e.getMessage());
        } finally {

            super.close(statement);
            super.close(connection);

        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final Guide guide) throws ApplicationException {

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(UPDATE_Guide);

            statement.setString(1, guide.getDescription());
            statement.setString(2, guide.getName());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new ApplicationException(e);
        } finally {

            super.close(statement);
            super.close(connection);

        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(final String name) throws ApplicationException {

        final int countRows;

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(DELETE_Guide);

            statement.setString(1, name);

            countRows = statement.executeUpdate();

        } catch (SQLException e) {
            throw new ApplicationException(e.getMessage());
        } finally {

            super.close(statement);
            super.close(connection);

        }

        return countRows != 0;

    }

}