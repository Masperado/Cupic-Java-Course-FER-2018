package hr.fer.zemris.java.dao;

import hr.fer.zemris.java.dao.sql.SQLDAO;

/**
 * This class represents singleton object which knows who he need to return as a provider for database.
 */
public class DAOProvider {

    /**
     * Instance of dao provider.
     */
    private static DAO dao = new SQLDAO();

    /**
     * This method is used for returning instance of dao provider.
     *
     * @return Instance
     */
    public static DAO getDao() {
        return dao;
    }

}