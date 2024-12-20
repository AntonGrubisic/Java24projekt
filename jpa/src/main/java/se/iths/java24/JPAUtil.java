package se.iths.java24;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.function.Consumer;

public class JPAUtil {
    private static final EntityManagerFactory emf;

    static {
        emf = Persistence.createEntityManagerFactory("jpa-hibernate-mysql");
        Runtime.getRuntime().addShutdownHook(new Thread(emf::close));
    }

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public static void inTransaction(Consumer<EntityManager> work) {
        EntityManager em = JPAUtil.getEntityManager();
        try (EntityManager entityManager = JPAUtil.getEntityManager()) {
            EntityTransaction transaction = entityManager.getTransaction();
            try {
                transaction.begin();
                work.accept(entityManager);
                transaction.commit();
            } catch (Exception e) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                throw e;
            } finally {
                em.close(); // Stäng EntityManager
            }
        }
    }
    public static void setForeignKeyChecks(EntityManager em, boolean enable) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        try {
            if (enable) {
                em.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1;").executeUpdate();
            } else {
                em.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0;").executeUpdate();
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }
}
