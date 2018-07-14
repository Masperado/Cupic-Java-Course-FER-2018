package hr.fer.zemris.java.hw15.dao;

import hr.fer.zemris.java.hw15.dao.jpa.JPADAOImpl;

/**
 * This class represents singleton object which knows who he need to return as a provider for database.
 */
public class DAOProvider {

    /**
     * Instance of dao provider.
     */
    private static DAO dao = new JPADAOImpl();

    /**
     * This method is used for returning instance of dao provider.
     *
     * @return Instance
     */
    public static DAO getDAO() {
        return dao;
    }

}