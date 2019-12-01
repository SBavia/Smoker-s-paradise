package database.dao;

import entity.Guide;
import exception.ApplicationException;

import java.util.List;

/**
 * This abstract class stores methods for work with table 'guide'.
 */
public abstract class AbstractGuideDao extends AbstractDao<Guide> {

    /**
     * Protected default constructor.
     */
    protected AbstractGuideDao() {

        super();

    }

    /**
     * Method declaration for delete guide from database.
     *
     * @param name value of the guide name
     * @return boolean value
     * @throws ApplicationException throw SQLException
     */
    protected abstract boolean delete(String name)
            throws ApplicationException;

    /**
     * Method declaration for select guide by name from database.
     *
     * @param name value of the guide name
     * @return list of guides
     * @throws ApplicationException throw SQLException
     */
    protected abstract List<Guide> select(String name)
            throws ApplicationException;

}