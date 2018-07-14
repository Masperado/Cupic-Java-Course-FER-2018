package hr.fer.zemris.java.hw15.dao.jpa;

import hr.fer.zemris.java.hw15.dao.DAOException;

import javax.persistence.EntityManager;

/**
 * This class represents {@link EntityManager} provider.
 */
public class JPAEMProvider {

    /**
     * Thread safe collection of {@link EntityManager}.
     */
    private static ThreadLocal<EntityManager> locals = new ThreadLocal<>();

    /**
     * This method is used for getting entity manager.
     *
     * @return {@link EntityManager}
     */
    public static EntityManager getEntityManager() {
        EntityManager em = locals.get();
        if (em == null) {
            em = JPAEMFProvider.getEmf().createEntityManager();
            em.getTransaction().begin();
            locals.set(em);
        }
        return em;
    }

    /**
     * This method is used for closing {@link EntityManager}.
     *
     * @throws DAOException DAO exception
     */
    public static void close() throws DAOException {
        EntityManager em = locals.get();
        if (em == null) {
            return;
        }
        DAOException dex = null;
        try {
            em.getTransaction().commit();
        } catch (Exception ex) {
            dex = new DAOException("Unable to commit transaction.", ex);
        }
        try {
            em.close();
        } catch (Exception ex) {
            if (dex != null) {
                dex = new DAOException("Unable to close entity manager.", ex);
            }
        }
        locals.remove();
        if (dex != null) throw dex;
    }

}