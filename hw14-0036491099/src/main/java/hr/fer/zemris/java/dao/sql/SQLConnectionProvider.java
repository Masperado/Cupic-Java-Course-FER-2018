package hr.fer.zemris.java.dao.sql;

import java.sql.Connection;

/**
 * This class is used for storing connections to database for current running thread.
 */
public class SQLConnectionProvider {

    /**
     * Thread local object.
     */
    private static ThreadLocal<Connection> connections = new ThreadLocal<>();

    /**
     * Setter for connection.
     *
     * @param con Connection
     */
    public static void setConnection(Connection con) {
        if (con == null) {
            connections.remove();
        } else {
            connections.set(con);
        }
    }

    /**
     * Getter for connection.
     *
     * @return Connection
     */
    public static Connection getConnection() {
        return connections.get();
    }

}