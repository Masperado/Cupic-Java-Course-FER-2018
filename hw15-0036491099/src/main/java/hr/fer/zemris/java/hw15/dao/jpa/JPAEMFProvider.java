package hr.fer.zemris.java.hw15.dao.jpa;

import javax.persistence.EntityManagerFactory;

/**
 * This class represents {@link EntityManagerFactory} provider bridge for this web application.
 */
public class JPAEMFProvider {

    /**
     * {@link EntityManagerFactory} instance.
     */
    public static EntityManagerFactory emf;

    /**
     * Getter for {@link EntityManagerFactory} instance
     *
     * @return {@link EntityManagerFactory} instance
     */
    public static EntityManagerFactory getEmf() {
        return emf;
    }

    /**
     * Setter for {@link EntityManagerFactory} instance
     *
     * @param emf {@link EntityManagerFactory} instance
     */
    public static void setEmf(EntityManagerFactory emf) {
        JPAEMFProvider.emf = emf;
    }
}