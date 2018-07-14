package hr.fer.zemris.java.hw15.dao.jpa;

import hr.fer.zemris.java.hw15.dao.DAO;
import hr.fer.zemris.java.hw15.dao.DAOException;
import hr.fer.zemris.java.hw15.model.BlogComment;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * This class represents implementation of {@link DAO} for this web application.
 */
public class JPADAOImpl implements DAO {

    @Override
    public BlogEntry getBlogEntry(Long id) throws DAOException {
        return JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
    }

    @Override
    public List<BlogEntry> getBlogEntryForUser(BlogUser user) throws DAOException {

        @SuppressWarnings("unchecked")
        List<BlogEntry> blogEntries = JPAEMProvider.getEntityManager().createQuery("SELECT b FROM BlogEntry as b where b.creator=:n1").setParameter("n1", user).getResultList();

        return blogEntries;
    }

    @Override
    public BlogUser getUser(String nick) throws DAOException {
        @SuppressWarnings("unchecked")
        List<BlogUser> blogUser = JPAEMProvider.getEntityManager().createQuery("SELECT b from BlogUser as " +
                "b where b" +
                ".nick=:n1").setParameter("n1", nick).getResultList();

        if (blogUser.isEmpty()) {
            return null;
        } else {
            return blogUser.get(0);
        }
    }

    @Override
    public List<BlogUser> getUsers() throws DAOException {
        @SuppressWarnings("unchecked")
        List<BlogUser> blogUsers = JPAEMProvider.getEntityManager().createQuery("SELECT b FROM BlogUser as b ")
                .getResultList();

        return blogUsers;
    }


    @Override
    public void persistUser(BlogUser user) throws DAOException {
        EntityManager em = JPAEMProvider.getEntityManager();

        if (user.getId() != null) {
            em.merge(user);
        } else {
            em.persist(user);
        }

    }

    @Override
    public void persistEntry(BlogEntry entry) throws DAOException {
        EntityManager em = JPAEMProvider.getEntityManager();

        if (entry.getId() != null) {
            em.merge(entry);
        } else {
            em.persist(entry);
        }


    }

    @Override
    public void persistComment(BlogComment comment) throws DAOException {
        EntityManager em = JPAEMProvider.getEntityManager();

        if (comment.getId() != null) {
            em.merge(comment);
        } else {
            em.persist(comment);
        }


    }
}