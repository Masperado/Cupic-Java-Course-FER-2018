package hr.fer.zemris.java.hw15.dao;

import hr.fer.zemris.java.hw15.model.BlogComment;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;

import java.util.List;

/**
 * This interface is used for communicating with database.
 */
public interface DAO {

    /**
     * This method is used for getting blog entry for given id.
     *
     * @param id Id of entry
     * @return {@link BlogEntry}
     * @throws DAOException DAO exception
     */
    BlogEntry getBlogEntry(Long id) throws DAOException;

    /**
     * This method is used for getting list of all blog entries for given user.
     *
     * @param user {@link BlogUser}
     * @return List of {@link BlogEntry}
     * @throws DAOException DAO exception
     */
    List<BlogEntry> getBlogEntryForUser(BlogUser user) throws DAOException;

    /**
     * This method is used for getting user for given nick.
     *
     * @param nick Nickname
     * @return {@link BlogUser}
     * @throws DAOException DAO exception
     */
    BlogUser getUser(String nick) throws DAOException;

    /**
     * This method is used for getting list of all users.
     *
     * @return List of {@link BlogUser}
     * @throws DAOException DAO exception
     */
    List<BlogUser> getUsers() throws DAOException;

    /**
     * This method is used for saving user to database.
     *
     * @param user {@link BlogUser}
     * @throws DAOException DAO exception
     */
    void persistUser(BlogUser user) throws DAOException;

    /**
     * This method is used for saving entry to database.
     *
     * @param entry {@link BlogEntry}
     * @throws DAOException DAO exception
     */
    void persistEntry(BlogEntry entry) throws DAOException;

    /**
     * This method is used for saving comment to database.
     *
     * @param comment {@link BlogComment}
     * @throws DAOException DAO exception
     */
    void persistComment(BlogComment comment) throws DAOException;


}